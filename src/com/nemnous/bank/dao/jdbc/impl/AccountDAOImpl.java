package com.nemnous.bank.dao.jdbc.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.nemnous.bank.dao.AccountDAO;
import com.nemnous.bank.dao.factory.MYSQLDbDAOFactory;
import com.nemnous.bank.exceptions.InsufficientBalanceException;
import com.nemnous.bank.exceptions.InvalidDetailsException;
import com.nemnous.bank.models.Account;


//https://stackoverflow.com/questions/9981277/dao-package-structure
//https://stackoverflow.com/questions/2173535/spring-hibernate-dao-naming-convention


/**
 * DAO for acccount table
 * @author nemnous
 *
 */
public class AccountDAOImpl implements AccountDAO {

	private static Connection connection = MYSQLDbDAOFactory.getConnection();
	private Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/**
	 * adds account to the table
	 * @param account
	 * @return
	 */
	@Override
	public long addAccount(Account account) {
		String query 
	        = "insert into account(account_number, customer_id, account_type_id, balance)"
	        		+ " values(?, ?, ?, ?);";
		long generatedKey = 0;
		try(PreparedStatement preparedStatement = 
				connection.prepareStatement(query,
	                    Statement.RETURN_GENERATED_KEYS);) {
		    preparedStatement.setString(1, account.getAccountNumber()); 
		    preparedStatement.setLong(2, account.getCustomerId()); 
		    preparedStatement.setShort(3, account.getAccountTypeId());
		    //https://stackoverflow.com/questions/40376198/how-to-insert-big-integer-in-prepared-statement-java
		    preparedStatement.setBigDecimal(4, account.getBalance());
		    preparedStatement.executeUpdate();
	
		    ResultSet result = preparedStatement.getGeneratedKeys();
		    
		    if (result.next()) {
		        generatedKey = result.getLong(1);
		    }

		} catch (SQLException e) {
			logger.log(Level.WARNING, "Wrong SQL Query");
		}

		return generatedKey;
		
	}

	/**
	 * returns all the accounts from the account table
	 * @return
	 */
	@Override
	public Collection<Account> getAllAccounts() {
		Collection<Account> accounts = new ArrayList<>();
		String query = "select * from account";
		try(PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			ResultSet result = preparedStatement.executeQuery();
			while(result.next()) {
				Account account = new Account(result.getString("account_number"),
						result.getBigDecimal("balance"), result.getLong("customer_id"),
						result.getShort("account_type_id"));
				accounts.add(account);
			}
		} catch (SQLException e) {
			logger.log(Level.WARNING, "Wrong SQL Query");
		}
		return accounts;
	}


	/**
	 * returns an account given an account number.
	 * @param accountNumber
	 * @return
	 */
	@Override
	public Account getAccountByAccNumber(String accountNumber) {
		String query = "select * from account where account_number = ?";
		Account account = null;
		try(PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			preparedStatement.setString(1, accountNumber);
			ResultSet result = preparedStatement.executeQuery();
	        while (result.next()) {
	            account = new Account(result.getString("account_number"),
						result.getBigDecimal("balance"), result.getLong("customer_id"),
						result.getShort("account_type_id"));
	        } 
            
		} catch (SQLException e) {
			logger.log(Level.WARNING, "Wrong SQL Query");
		}
		return account;
	}

	/**
	 * returns the balance given an account number
	 * @param accountNumber
	 * @return
	 */
	@Override
	public BigDecimal getBalancebyAccNumber(String accountNumber) {
		Account account = getAccountByAccNumber(accountNumber);
		return account.getBalance();
	}

	private BigDecimal updateBalance(String accountNumber, BigDecimal amount) {
		
		String query = "update account set balance = ? where account_number = ?";
		BigDecimal updatedBalance = new BigDecimal(-1);
		try {
			BigDecimal currentBalance = getAccountByAccNumber(accountNumber).getBalance();
			updatedBalance = currentBalance.add(amount);
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setBigDecimal(1, updatedBalance);
			preparedStatement.setString(2, accountNumber);
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			logger.log(Level.WARNING, "Wrong SQL Query");
		} catch (NullPointerException e) {
			logger.log(Level.WARNING, "Account Not found");
		}
		return updatedBalance;
	}
	
	/**
	 * Deposits money to the account
	 * @param accountNumber
	 * @param amount
	 * @return
	 */
	@Override
	public BigDecimal depositToAccount(String accountNumber, BigDecimal amount) {
		if (amount.compareTo(BigDecimal.ZERO) < 0) {
			throw new InvalidDetailsException("Amount cannot be zero or negative");
		}
		return updateBalance(accountNumber, amount);
	}

	/**
	 * withdraws money from the account
	 * @param accountNumber
	 * @param amount
	 * @return
	 */
	@Override
	public BigDecimal withdrawToAccount(String accountNumber, BigDecimal amount) {
		if (amount.compareTo(BigDecimal.ZERO) < 0) {
			throw new InvalidDetailsException("Amount cannot be zero or negative");
		}
		try {
			BigDecimal currentBalance = getAccountByAccNumber(accountNumber).getBalance();
			if(currentBalance.compareTo(amount) < 0) {
				throw new InsufficientBalanceException("Insufficient balance");
			}
		} catch (NullPointerException e) {
			logger.log(Level.WARNING, "Account Not found");

			return new BigDecimal(-1);
		}
		return updateBalance(accountNumber, amount.negate());
	}
	

}
