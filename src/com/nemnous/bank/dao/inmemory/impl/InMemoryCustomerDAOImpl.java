package com.nemnous.bank.dao.inmemory.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

import com.nemnous.bank.dao.CustomerDAO;
import com.nemnous.bank.models.Customer;


/**manages the customer table operations.
 * 
 * @author nemnous
 *
 */
public class InMemoryCustomerDAOImpl implements CustomerDAO{
	Collection<Customer> customers = new ArrayList<>();
	private static final AtomicInteger count = new AtomicInteger(0);

	/**
	 * adds cutomer to the table.
	 * @param customer
	 * @return
	 */
	@Override
	public long addCustomer(Customer customer) {
		long id = count.incrementAndGet();
		customer.setId(id);
		customers.add(customer);
		return id;
	}

	/**
	 * returns all the customers in the table
	 * @return
	 */
	@Override
	public Collection<Customer> getAllCustomers() {
		return customers;
	}

	/**
	 * returns a customer given an id.
	 * @param id
	 * @return
	 */
	@Override
	public Customer getCustomerById(long id) {
		for (Customer customer : customers) {
			if(customer.getId() == id) {
				return customer;
			}
		}
		return null;
	}

}
