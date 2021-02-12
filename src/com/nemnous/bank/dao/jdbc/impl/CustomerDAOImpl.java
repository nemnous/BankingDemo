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

import com.nemnous.bank.dao.CustomerDAO;
import com.nemnous.bank.dao.factory.MYSQLDbDAOFactory;
import com.nemnous.bank.models.Customer;


/**manages the customer table operations.
 * 
 * @author nemnous
 *
 */
public class CustomerDAOImpl implements CustomerDAO{
	
	private static Connection connection = MYSQLDbDAOFactory.getConnection();
	private Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static final String MESSAGE = "Wrong SQL Query";
	
	/**
	 * adds cutomer to the table.
	 * @param customer
	 * @return
	 */
	@Override
	public long addCustomer(Customer customer) {

		String query 
	        = "insert into customer(name, address, phone, branch_id)"
	        		+ " values(?, ?, ?, ?);";

		try(PreparedStatement preparedStatement = 
				connection.prepareStatement(query,
	                    Statement.RETURN_GENERATED_KEYS);) {
		    preparedStatement.setString(1, customer.getName()); 
		    preparedStatement.setString(2, customer.getAddress()); 
		    preparedStatement.setString(3, customer.getPhone());
		    //https://stackoverflow.com/questions/40376198/how-to-insert-big-integer-in-prepared-statement-java
		    preparedStatement.setLong(4, customer.getBranchId());
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
	 * returns all the customers in the table
	 * @return
	 */
	@Override
	public Collection<Customer> getAllCustomers() {
		Collection<Customer> customers = new ArrayList<>();
		String query = "select * from customer";
		try(PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			ResultSet result = preparedStatement.executeQuery();
			while(result.next()) {
				Customer customer = new Customer(result.getString("phone"),
						result.getString("address"),result.getString("name"),
						result.getLong("branch_id"));
				customers.add(customer);
			}
		} catch (SQLException e) {
			logger.log(Level.WARNING, MESSAGE);
		}
		return customers;
	}
	
	/**
	 * returns a customer given an id.
	 * @param id
	 * @return
	 */
	@Override
	public Customer getCustomerById(long id) {
		String query = "select * from customer where id = ?";
		Customer customer = null;
		try(PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			preparedStatement.setLong(1, id);
			ResultSet result = preparedStatement.executeQuery();
	        while (result.next()) {
	            customer = new Customer(result.getString("phone"),
						result.getString("address"),result.getString("name"),
						result.getLong("branch_id"));
	        } 
            
		} catch (SQLException e) {
			logger.log(Level.WARNING, MESSAGE);
		}
		return customer; 
	}

}
