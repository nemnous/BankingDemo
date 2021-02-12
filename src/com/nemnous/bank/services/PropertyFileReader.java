package com.nemnous.bank.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
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

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;

public class PropertyFileReader implements InputReader{
	
	String path = "resources/config.properties";
	
	private static final String INVALID_MSG = "Invalid Input";
	
	private final Logger logger;

	private DAOFactory daoFactory;
	
	private CustomerDAO customerDao;
	
	private AccountDAO accountDao;

	private AccountTypeDAO accountTypeDAO;
	
	public PropertyFileReader() {

		this.logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		
		this.daoFactory = DAOFactory.getDAOFactory(DAOFactory.INMEMORY);

		this.customerDao = daoFactory.getCustomerDAO();
		
		this.accountDao = daoFactory.getAccountDAO();

		this.accountTypeDAO = daoFactory.getAccountTypeDAO();
		
	}

	/**
	 * implementing the read method. This reads the file from the path
	 * and loads the properties.
	 */
	public void read() {

		accountTypeDAO.addType(new AccountType("savings"));
		accountTypeDAO.addType(new AccountType("fixed"));

		Properties property = null;
		try(FileReader reader = new FileReader(path);) {
			 property = new Properties();
		     property.load(reader);
		} catch (IOException e) {
		     logger.log(Level.WARNING, "File Not Found");
		     return;
		}

		//Getting all keys in a property file and
		//sorting because of orderly insertion for testing purpose.
		List<String> keys = new ArrayList<>(property.stringPropertyNames()); //contains all keys in properties file. 
		Collections.sort(keys);
		String input;
		String[] details;
		//iterating through all the keys for performing desired operation.
		for(String key : keys) {
			logger.log(Level.INFO, "\n{0}\n",property.getProperty(key));
			if (key.contains("addAccount")) {
				input = property.getProperty(key);
				details = input.split(" ");
				parseAddAccount(details);
			} else if (key.contains("search")) {
				searchByAccount(property.getProperty(key));
			} else if (key.contains("deposit")) {
				input = property.getProperty(key);
				details = input.split(" ");
				parseDeposit(details);
			} else if (key.contains("withdraw")) {
				input = property.getProperty(key);
				details = input.split(" ");
				parseWithdraw(details);
			}
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

	/**
	 * this method is called when the key has withdraw
	 * this parses the value and get details to make transaction.
	 */
	private void parseWithdraw(String[] input) {
		try {
			String accountNumber = input[0];
			BigDecimal amount = new BigDecimal(input[1]);
			accountDao.withdrawToAccount(accountNumber, amount);
		} catch (NumberFormatException e) {
			logger.log(Level.INFO, "Not a valid amount");
		} catch (InvalidDetailsException | InsufficientBalanceException e) {
			logger.log(Level.INFO, e.getMessage());
		} catch (ArrayIndexOutOfBoundsException e) {
			logger.log(Level.INFO, INVALID_MSG);
		}
		
	}
	
	
	/**
	 * this method is called when the key has add account
	 * this parses the value and get details to create new account.
	 */
	public void parseAddAccount(String[] input) {
		long branchId = 1; // lets suppose we're working on a single branch
		try {
			InputValidation.validateName(input[0]);
			InputValidation.validatePhone(input[1]);
			Customer customer = new Customer(input[1], input[2], input[0], branchId);
			long customerId = customerDao.addCustomer(customer);
			short accountTypeId = accountTypeDAO.getIdbyType(input[4]);
			Account account = new Account(input[3], BigDecimal.ZERO, customerId, accountTypeId);
			accountDao.addAccount(account);
		} catch (InvalidDetailsException e) {
			logger.log(Level.WARNING, e.getMessage());
		} catch (ArrayIndexOutOfBoundsException e) {
			logger.log(Level.WARNING, INVALID_MSG);
		}
	}
	
	/**
	 * this method is called when the key has withdraw
	 * this parses the value and get details to make transaction.
	 */
	public void parseDeposit(String[] input) {
		try {
			String accountNumber = input[0];
			BigDecimal amount = new BigDecimal(input[1]);
			accountDao.depositToAccount(accountNumber, amount);
		} catch (NumberFormatException e) {
			logger.log(Level.INFO, "Not a valid amount");
		} catch (InvalidDetailsException e) {
			logger.log(Level.INFO, e.getMessage());
		} catch (ArrayIndexOutOfBoundsException e) {
			logger.log(Level.INFO, INVALID_MSG);
		}
	}
}
