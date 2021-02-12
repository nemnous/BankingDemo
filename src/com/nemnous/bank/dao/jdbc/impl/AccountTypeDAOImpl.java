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

import com.nemnous.bank.dao.AccountTypeDAO;
import com.nemnous.bank.dao.factory.MYSQLDbDAOFactory;
import com.nemnous.bank.models.AccountType;

/**
 * Account type -> savings or fixed
 * Deals with the account type table
 * @author nemnous
 *
 */
public class AccountTypeDAOImpl implements AccountTypeDAO{
	
	private static Connection connection = MYSQLDbDAOFactory.getConnection();
	private Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private static final String INVALID_MSG = "Wrong SQL Query";

	/**
	 * adds type to the account_type table
	 * @param accountType
	 * @return
	 */
	@Override
	public short addType(AccountType accountType) {

		String query 
	        = "insert into account_type(type)"
	        		+ " values(?);";
		short generatedKey = 0;
		try(PreparedStatement preparedStatement = 
				connection.prepareStatement(query,
	                    Statement.RETURN_GENERATED_KEYS);) {
	
		    preparedStatement.setString(1, accountType.getType());
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
	 * returns id given the type.
	 * @param type
	 * @return
	 */
	@Override
	public short getIdbyType(String type) {
		String query = "select * from account_type where type = ?";
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
	 * returns type given id
	 * @param id
	 * @return
	 */
	@Override
	public String getTypebyId(short id) {
		String query = "select * from account_type where id = ?";
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
	 * returns all types as a collection.
	 * @return
	 */
	@Override
	public Collection<AccountType> getAllAccountTypes() {
		Collection<AccountType> accountTypes = new ArrayList<>();
		String query = "select * from account_type";
		try(PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			ResultSet result = preparedStatement.executeQuery();
			while(result.next()) {
				AccountType accountType = new AccountType(result.getString("type"));
				accountTypes.add(accountType);
			}
		} catch (SQLException e) {
			logger.log(Level.WARNING, INVALID_MSG);
		}
		return accountTypes;
	}
	

}
