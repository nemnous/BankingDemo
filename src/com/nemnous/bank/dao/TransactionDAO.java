package com.nemnous.bank.dao;

import java.util.Collection;

import com.nemnous.bank.models.Transaction;

/**
 * Manages all the transaction table operations.
 * an entry is done when there is an successful 
 * withdraw or deposit operation.
 * @author nemnous
 *
 */
public interface TransactionDAO {
	
	/**
	 * adds transaction to the table.
	 * @param transaction
	 * @return
	 */
	public long addTransaction(Transaction transaction);
	/**
	 * returns all the transactions in the table.
	 * @return
	 */
	public Collection<Transaction> getAllTransactions();
	/**
	 * returns a transaction given an id.
	 * @param id
	 * @return
	 */
	public Transaction getTransactionById(long id);
	
	/**
	 * returns all the transactions given an account number
	 * @param accountNumber
	 * @return
	 */
	public Collection<Transaction> getTransactionsByAccountNumber(String accountNumber);

}
