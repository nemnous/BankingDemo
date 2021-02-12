package com.nemnous.bank.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.nemnous.bank.dao.AccountDAO;
import com.nemnous.bank.dao.AccountTypeDAO;
import com.nemnous.bank.dao.CustomerDAO;
import com.nemnous.bank.dao.factory.DAOFactory;
import com.nemnous.bank.exceptions.InsufficientBalanceException;
import com.nemnous.bank.exceptions.InvalidDetailsException;
import com.nemnous.bank.interfaces.InputReader;
import com.nemnous.bank.models.Account;
import com.nemnous.bank.models.AccountType;
import com.nemnous.bank.models.Customer;
import com.nemnous.bank.validations.InputValidation;


public class FileReader implements InputReader{
	String path = "resources/input.txt";
	
	private static final String INVALID_MSG = "Invalid Input";
	private File file;
	
	private final Logger logger;

	private DAOFactory daoFactory;
	
	private CustomerDAO customerDao;
	
	private AccountDAO accountDao;

	private AccountTypeDAO accountTypeDAO;
	
	public FileReader() {

		this.logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		
		this.daoFactory = DAOFactory.getDAOFactory(DAOFactory.INMEMORY);

		this.customerDao = daoFactory.getCustomerDAO();
		
		this.accountDao = daoFactory.getAccountDAO();

		
		this.accountTypeDAO = daoFactory.getAccountTypeDAO();
		
		 this.file = new File(path);
		
	}
	
	/**

	 * this parses the value and get details to create new account.
	 * @param input
	 */
	public void parseAddAccount(String[] input) {
//		input[1] -> name, input[2] -> phone, input[3] -> address,
//		input[4] -> acount_number, input[5] -> acoount_type
		long branchId = 1; // lets suppose we're working on a single branch
		try {
			InputValidation.validateName(input[1]);
			InputValidation.validatePhone(input[2]);
			Customer customer = new Customer(input[2], input[3], input[1], branchId);
			long customerId = customerDao.addCustomer(customer);
			short accountTypeId = accountTypeDAO.getIdbyType(input[5]);
			Account account = new Account(input[4], BigDecimal.ZERO, customerId, accountTypeId);
			accountDao.addAccount(account);
		} catch (InvalidDetailsException e) {
			logger.log(Level.WARNING, e.getMessage());
		} catch (ArrayIndexOutOfBoundsException e) {
			logger.log(Level.WARNING, INVALID_MSG);
		}
	}


	private void parseWithdraw(String[] input) {
		try {
			String accountNumber = input[1];
			BigDecimal amount = new BigDecimal(input[2]);
			accountDao.withdrawToAccount(accountNumber, amount);
		} catch (NumberFormatException e) {
			logger.log(Level.INFO, "Not a valid amount");
		} catch (InvalidDetailsException | InsufficientBalanceException e) {
			logger.log(Level.INFO, e.getMessage());
		} catch (ArrayIndexOutOfBoundsException e) {
			logger.log(Level.INFO, INVALID_MSG);
		}
		
	}


	private void parseDeposit(String[] input) {
		try {
			String accountNumber = input[1];
			BigDecimal amount = new BigDecimal(input[2]);
			accountDao.depositToAccount(accountNumber, amount);
		} catch (NumberFormatException e) {
			logger.log(Level.INFO, "Not a valid amount");
		} catch (InvalidDetailsException e) {
			logger.log(Level.INFO, e.getMessage());
		} catch (ArrayIndexOutOfBoundsException e) {
			logger.log(Level.INFO, INVALID_MSG);
		}
	}


	private void searchByAccount(String accountNumber) {
		try {

			Account account = accountDao.getAccountByAccNumber(accountNumber);
			Customer customer = customerDao.getCustomerById(account.getCustomerId());
			String accountType = accountTypeDAO.getTypebyId(account.getAccountTypeId());
			String message = account.getAccountNumber() + " " + customer.getName() + " " +
			customer.getPhone() + " " + accountType + " " + account.getBalance();
			logger.log(Level.INFO, message);
			
		} catch (NullPointerException e) {
			logger.log(Level.WARNING, "Account not found");
		}
	}


	private void displayAccounts() {
		
		for (Account account : accountDao.getAllAccounts()) {
			Customer customer = customerDao.getCustomerById(account.getCustomerId());
			String accountType = accountTypeDAO.getTypebyId(account.getAccountTypeId());
			String message = account.getAccountNumber() + " " + customer.getName() + " " +
			customer.getPhone() + " " + accountType + " " + account.getBalance();
			logger.log(Level.INFO, message);
		}
	}


	/**
	 * This method is used to read input from the file.
	 * It gives the user the options available.
	 * @throws FileNotFoundException 
	 */
	public void read() {

		accountTypeDAO.addType(new AccountType("savings"));
		accountTypeDAO.addType(new AccountType("fixed"));


		logger.log(Level.INFO,"------------------------------"
				+ "-----------------------------------------");

		try(Scanner scan = new Scanner(file);) {
			while (scan.hasNextLine()) {
			      String line = scan.nextLine();
			      logger.log(Level.INFO, "\n\n {0}", line);
			      String[] input = line.split(" ");
			      switch (input[0]) {
					case "add":
						parseAddAccount(input);
						continue;
					case "displayAll":
						displayAccounts();
						continue;
					case "search":
						searchByAccount(input[1]);
						continue;
					case "deposit":
						parseDeposit(input);
						continue;
					case "withdraw":
						parseWithdraw(input);
						continue;
					default:
						continue;
					}
			}
		} catch (FileNotFoundException e) {
			logger.log(Level.SEVERE, "File Not Found");
		}
			
	}


}
