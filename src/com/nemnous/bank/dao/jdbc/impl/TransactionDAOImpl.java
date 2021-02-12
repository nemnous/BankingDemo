package com.nemnous.bank.dao.jdbc.impl;

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
import com.nemnous.bank.dao.TransactionDAO;
import com.nemnous.bank.dao.factory.MYSQLDbDAOFactory;
import com.nemnous.bank.models.Account;
import com.nemnous.bank.models.Transaction;

/**
 * Manages all the transaction table operations.
 * an entry is done when there is an successful 
 * withdraw or deposit operation.
 * @author nemnous
 *
 */
public class TransactionDAOImpl implements TransactionDAO{


	private static Connection connection = MYSQLDbDAOFactory.getConnection();
	private Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static final String MESSAGE = "Wrong SQL Query";
	
	/**
	 * adds transaction to the table.
	 * @param transaction
	 * @return
	 */
	@Override
	public long addTransaction(Transaction transaction) {

		String query 
	        = "insert into customer(account_id, transaction_type_id, amount)"
	        		+ " values(?, ?, ?);";

		try(PreparedStatement preparedStatement = 
				connection.prepareStatement(query,
	                    Statement.RETURN_GENERATED_KEYS);) {
		    preparedStatement.setLong(1, transaction.getAccountId()); 
		    preparedStatement.setShort(2, transaction.getTransactionTypeId()); 
		    preparedStatement.setBigDecimal(3, transaction.getAmount());
		    //https://stackoverflow.com/questions/40376198/how-to-insert-big-integer-in-prepared-statement-java
		    preparedStatement.executeUpdate();
		    
		    ResultSet result = preparedStatement.getGeneratedKeys();
		    long generatedKey = 0;
		    result.first();
		    generatedKey = result.getLong(1);
	        return generatedKey;

		} catch (SQLException e) {
			logger.log(Level.WARNING, MESSAGE);
		} 
	
		return 0;
	}

	/**
	 * returns all the transactions in the table.
	 * @return
	 */
	@Override
	public Collection<Transaction> getAllTransactions() {
		Collection<Transaction> transactions = new ArrayList<>();
		String query = "select * from transaction";
		try(PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			ResultSet result = preparedStatement.executeQuery();
			while(result.next()) {
				Transaction transaction = new Transaction(result.getLong("account_id"), result.getShort("transactionType_id"),
						result.getBigDecimal("amount"));
				transactions.add(transaction);
			}
		} catch (SQLException e) {
			logger.log(Level.WARNING, MESSAGE);
		}
		return transactions;
	}
	

	/**
	 * returns a transaction given an id.
	 * @param id
	 * @return
	 */
	@Override
	public Transaction getTransactionById(long id) {
		String query = "select * from transaction where id = ?";
		Transaction transaction = null;
		try(PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			preparedStatement.setLong(1, id);
			ResultSet result = preparedStatement.executeQuery();
	        while (result.next()) {
	        	transaction = new Transaction(result.getLong("account_id"),result.getShort("transactionType_id"),
						result.getBigDecimal("amount"));
	        } 
            
		} catch (SQLException e) {
			logger.log(Level.WARNING, MESSAGE);
		}
		return transaction; 
	}

	
	/**
	 * returns all the transactions given an account number
	 * @param accountNumber
	 * @return
	 */
	@Override
	public Collection<Transaction> getTransactionsByAccountNumber(String accountNumber) {
		Collection<Transaction> transactions = new ArrayList<>();
		AccountDAO accountDAOImpl = new AccountDAOImpl();
		Account account = accountDAOImpl.getAccountByAccNumber(accountNumber);

		String query = "select * from transaction where account_id = ?";
		try(PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			preparedStatement.setLong(1, account.getId());
			ResultSet result = preparedStatement.executeQuery();
			while(result.next()) {
				Transaction transaction = new Transaction(result.getLong("account_id"),result.getShort("transactionType_id"),
						result.getBigDecimal("amount"));
				transactions.add(transaction);
			}
		} catch (SQLException e) {
			logger.log(Level.WARNING, MESSAGE);
		}
		return transactions;
	}

}
