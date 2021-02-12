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

import com.nemnous.bank.dao.TransactionTypeDAO;
import com.nemnous.bank.dao.factory.MYSQLDbDAOFactory;
import com.nemnous.bank.models.TransactionType;

/**
 * Manages the transaction_type table
 * transaction type -> withdraw and deposit
 * @author nemnous
 *
 */
public class TransactionTypeDAOImpl implements TransactionTypeDAO{
	
	private static Connection connection = MYSQLDbDAOFactory.getConnection();
	private Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private static final String INVALID_MSG = "Wrong SQL Query";

	/**
	 * adds a type to the transaction_type table
	 * @param transactionType
	 * @return
	 */
	@Override
	public short addType(TransactionType transactionType) {

		String query 
	        = "insert into transaction_type(type)"
	        		+ " values(?);";
		short generatedKey = 0;
		try(PreparedStatement preparedStatement = 
				connection.prepareStatement(query,
	                    Statement.RETURN_GENERATED_KEYS);) {
	
		    preparedStatement.setString(1, transactionType.getType());
		    preparedStatement.executeUpdate();
	
		    ResultSet result = preparedStatement.getGeneratedKeys();
		    
		    if (result.next()) {
		        generatedKey = result.getShort(1);
		    }
		    
		} catch (SQLException e) {
			logger.log(Level.WARNING, INVALID_MSG);
		}
	
		return generatedKey;
	}

	/**
	 * returns an id given an type
	 * @param type
	 * @return
	 */
	@Override
	public short getIdbyType(String type) {
		String query = "select * from transaction_type where type = ?";
		short id = 0;
		try(PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			preparedStatement.setString(1, type);
			ResultSet result = preparedStatement.executeQuery();
	        while (result.next()) {
	            id = result.getShort("id");
	        } 
            
		} catch (SQLException e) {
			logger.log(Level.WARNING, INVALID_MSG);
		}
		return id;
	}

	/**
	 * returns a type given an type id
	 * @param id
	 * @return
	 */
	@Override
	public String getTypebyId(short id) {
		String query = "select * from transaction_type where id = ?";
		String type = "";
		try(PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			preparedStatement.setShort(1, id);
			ResultSet result = preparedStatement.executeQuery();
	        while (result.next()) {
	            type = result.getString("type");
	        } 

		} catch (SQLException e) {
			logger.log(Level.WARNING, INVALID_MSG);
		}
		return type;
	}

	/**
	 * returns all types of available transaction types
	 * @return
	 */
	@Override
	public Collection<TransactionType> getAllAccountTypes() {
		Collection<TransactionType> transactionTypes = new ArrayList<>();
		String query = "select * from transaction_type";
		try(PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			ResultSet result = preparedStatement.executeQuery();
			while(result.next()) {
				TransactionType transactionType = new TransactionType(result.getString("type"));
				transactionTypes.add(transactionType);
			}
		} catch (SQLException e) {
			logger.log(Level.WARNING, INVALID_MSG);
		}
		return transactionTypes;
	}
	

}
