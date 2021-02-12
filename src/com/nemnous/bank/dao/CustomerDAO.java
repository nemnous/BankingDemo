package com.nemnous.bank.dao;

import java.util.Collection;

import com.nemnous.bank.models.Customer;

/**manages the customer table operations.
 * 
 * @author nemnous
 *
 */
public interface CustomerDAO {
	
	/**
	 * adds cutomer to the table.
	 * @param customer
	 * @return
	 */
	public long addCustomer(Customer customer);
	
	/**
	 * returns all the customers in the table
	 * @return
	 */
	public Collection<Customer> getAllCustomers();
	
	/**
	 * returns a customer given an id.
	 * @param id
	 * @return
	 */
	public Customer getCustomerById(long id);
	
}
