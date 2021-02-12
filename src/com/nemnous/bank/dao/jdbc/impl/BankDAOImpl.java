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

import com.nemnous.bank.dao.BankDAO;
import com.nemnous.bank.dao.factory.MYSQLDbDAOFactory;
import com.nemnous.bank.models.Bank;

/**
 * this manages the bank table.
 * @author nemnous
 *
 */
public class BankDAOImpl implements BankDAO{
	
	private static Connection connection = MYSQLDbDAOFactory.getConnection();
	private Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static final String INVALID_MSG = "Wrong SQL Query";


	/**
	 * adds branch to the table.
	 * @param bank
	 * @return
	 */
	@Override
	public long addBank(Bank bank) {

		String query 
	        = "insert into bank(name, ifsc_code, city)"
	        		+ " values(?, ?, ?);"; 
	
		try(PreparedStatement preparedStatement = 
				connection.prepareStatement(query,
	                    Statement.RETURN_GENERATED_KEYS);) {
	
		    preparedStatement.setString(1, bank.getName()); 
		    preparedStatement.setString(2, bank.getIfscCode()); 
		    preparedStatement.setString(3, bank.getCity());
		    preparedStatement.executeUpdate();
	
		    ResultSet result = preparedStatement.getGeneratedKeys();
		    long generatedKey = 0;
		    if (result.next()) {
		        generatedKey = result.getLong(1);
		        return generatedKey;
		    }
		    
		} catch (SQLException e) {
			logger.log(Level.WARNING, INVALID_MSG);
		}
	
		return 0;
	}

	
	/**
	 * returns all branches from the table bank.
	 * @return
	 */
	@Override
	public Collection<Bank> getAllBranches() {
		Collection<Bank> branches = new ArrayList<>();
		String query = "select * from bank";
		try(PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			ResultSet result = preparedStatement.executeQuery();
			while(result.next()) {
				Bank bank = new Bank(result.getString("name"),
						result.getString("ifsc_code"), result.getString("city"));
				branches.add(bank);
			}
		} catch (SQLException e) {
			logger.log(Level.WARNING, INVALID_MSG);
		}
		return branches;
	}

	
	/**
	 * returns the bank given the id of the bank.
	 * @param id
	 * @return
	 */
	@Override
	public long getIdByIfsc(String ifsc) {
		String query = "select * from bank where ifsc_code = ?";
		long id = 0;
		try(PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			preparedStatement.setString(1, ifsc);
			ResultSet result = preparedStatement.executeQuery();
	        while (result.next()) {
	            id = result.getLong("id");
	        } 
            
		} catch (SQLException e) {
			logger.log(Level.WARNING, INVALID_MSG);
		}
		return id;
	}
	
	/**
	 * returns the id of bank given the IFSC code
	 * @param ifsc
	 * @return
	 */
	@Override
	public Bank getBankById(long id) {
		String query = "select * from bank where id = ?";
		Bank branch = null;
		try(PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			preparedStatement.setLong(1, id);
			ResultSet result = preparedStatement.executeQuery();
	        while (result.next()) {
	            branch = new Bank(result.getString("name"),
						result.getString("ifsc_code"), result.getString("city"));
	        } 
            
		} catch (SQLException e) {
			logger.log(Level.WARNING, INVALID_MSG);
		}
		return branch; 
	}
	
	

}
