package com.nemnous.bank.services;

import java.math.BigDecimal;
import java.util.Random;
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

public class ConsoleReader implements InputReader {
	private final Logger logger;
	
	private Scanner scan;
	
	private Random random = new Random();

	private DAOFactory daoFactory;
	
	private CustomerDAO customerDao;
	
	private AccountDAO accountDao;


	private AccountTypeDAO accountTypeDAO;
	
	public ConsoleReader() {

		this.logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		
		this.daoFactory = DAOFactory.getDAOFactory(DAOFactory.INMEMORY);

		this.customerDao = daoFactory.getCustomerDAO();
		
		this.accountDao = daoFactory.getAccountDAO();
		
		this.accountTypeDAO = daoFactory.getAccountTypeDAO();
		
		this.scan = new Scanner(System.in);
		
	}
	
	/**
	 * @return a string of account number
	 */
	public String generateAccountNumber() {
		final int length = 12;
	    char[] digits = new char[length];
	    digits[0] = (char) (random.nextInt(9) + '1');
	    for (int i = 1; i < length; i++) {
	        digits[i] = (char) (random.nextInt(10) + '0');
	    }
	    return new String(digits);
	}


	

	/**
	 * This method is used to read input from the console.
	 * It gives the user the options available.
	 */
	@Override
	public void read() {
		
		accountTypeDAO.addType(new AccountType("savings"));
		accountTypeDAO.addType(new AccountType("fixed"));

		logger.log(Level.INFO,"------------------------------"
				+ "-----------------------------------------");
		int key = 0;

		do {
			logger.log(Level.INFO,"Select operation to perform \n"
					+ "1. Add New Account \n"
					+ "2. Display All Accounts \n"
					+ "3. Search By Account \n"
					+ "4. Deposit Money in Account\n"
					+ "5. Withdraw Money From Account\n"
					+ "6. Exit\n");

			String choice = scan.nextLine();
			if(!choice.equals("") && choice.matches("\\d+") && choice.length() == 1) {
				key = Integer.parseInt(choice);
			} else {
				logger.log(Level.INFO, "Enter a valid number");
				continue;
			}

			switch (key) {
			case 1:
				readAddAccount();
				break;
			case 2:
				displayAccounts();
				break;
			case 3:
				logger.log(Level.INFO, "Enter account Number");
				searchByAccount(scan.nextLine());
				break;
			case 4:
				readDeposit();
				break;
			case 5:
				readWithdraw();
				break;
			default:
				continue;
			}
		} while (key != 6);
	}
	
	
	public void readWithdraw() {
		logger.log(Level.INFO, "Enter Account Number");
		String accountNumber = scan.nextLine();
		logger.log(Level.INFO, "Enter Amount");
		String deposit = scan.nextLine();
		try {
			BigDecimal amount = new BigDecimal(deposit);
			accountDao.withdrawToAccount(accountNumber, amount);
		} catch(NumberFormatException e) {
			logger.log(Level.WARNING, "Enter float value only");
		} catch (InvalidDetailsException | InsufficientBalanceException e) {
			logger.log(Level.WARNING, e.getMessage());
		}
	}


	/**
	 * This function is called when user requests
	 * to deposit amount to a specific bank account.
	 */
	public void readDeposit() {
		logger.log(Level.INFO, "Enter Account Number");
		String accountNumber = scan.nextLine();
		logger.log(Level.INFO, "Enter Amount");
		String deposit = scan.nextLine();
		try {
			BigDecimal amount = new BigDecimal(deposit);
			accountDao.depositToAccount(accountNumber, amount);
		} catch(NumberFormatException e) {
			logger.log(Level.WARNING, "Enter float value only");
		} catch (InvalidDetailsException e) {
			logger.log(Level.WARNING, e.getMessage());
		}
	}

	private void searchByAccount(String accountNumber) {
		for (Account account : accountDao.getAllAccounts()) {
			if (account.getAccountNumber().equals(accountNumber)) {

				Customer customer = customerDao.getCustomerById(account.getCustomerId());
				logger.log(Level.INFO, account.getAccountNumber() + " " + customer.getName()
			 + " " + customer.getPhone() + " " +  accountTypeDAO.getTypebyId(account.getAccountTypeId())
			 + " " + account.getBalance());
				
			return;
			}
		}
		
		logger.log(Level.INFO, "Account Not Found");
	}

	/**
	 * this function is called when a user
	 * selects to add account to bank.
	 * This reads required account details from user.
	 */
	public void readAddAccount() {
		try {
			Customer customer = new Customer();
			logger.log(Level.INFO, "Enter Name");
			String name = scan.nextLine();
			InputValidation.validateName(name);
			customer.setName(name);
			logger.log(Level.INFO, "Enter Phone Number");
			String phone =  scan.nextLine();
			InputValidation.validatePhone(phone);
			customer.setPhone(phone);
			logger.log(Level.INFO, "Enter Address");
			customer.setAddress(scan.nextLine());

			logger.log(Level.INFO, "Type of Account \n");
			for (AccountType type : accountTypeDAO.getAllAccountTypes()) {
				logger.log(Level.INFO, type.getId() + ". " + type.getType());
			}
			String choice = scan.nextLine();
			int accountTypeId = 0;
			accountTypeId = Integer.parseInt(choice);

			if (accountTypeDAO.getTypebyId((short)accountTypeId) == null) {
				accountTypeId = 1;
			}
			
			long customerId = customerDao.addCustomer(customer);
			
			Account account = new Account(generateAccountNumber(), BigDecimal.ZERO,
					customerId, (short) accountTypeId);
			accountDao.addAccount(account);
		} catch (InvalidDetailsException e) {
			logger.log(Level.WARNING, e.getMessage());
		} catch(NumberFormatException e) {
			logger.log(Level.WARNING, "Invalid Input Default value set to"
					+ accountTypeDAO.getTypebyId((short) 1));
		}
		
	}
	
	private void displayAccounts() {
		for (Account account : accountDao.getAllAccounts()) {
			Customer customer = customerDao.getCustomerById(account.getCustomerId());
			logger.log(Level.INFO, account.getAccountNumber() + " " + customer.getName()
		 + " " + customer.getPhone() + " " +  accountTypeDAO.getTypebyId(account.getAccountTypeId())
		 + " " + account.getBalance());
		}
	}
}
