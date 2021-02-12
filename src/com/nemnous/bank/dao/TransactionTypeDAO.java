package com.nemnous.bank.dao;

import java.util.Collection;

import com.nemnous.bank.models.TransactionType;

/**
 * Manages the transaction_type table
 * transaction type -> withdraw and deposit
 * @author nemnous
 *
 */
public interface TransactionTypeDAO {
	/**
	 * adds a type to the transaction_type table
	 * @param transactionType
	 * @return
	 */
	public short addType(TransactionType transactionType);
	
	/**
	 * returns an id given an type
	 * @param type
	 * @return
	 */
	public short getIdbyType(String type);
	/**
	 * returns a type given an type id
	 * @param id
	 * @return
	 */
	public String getTypebyId(short id);
	
	/**
	 * returns all types of available transaction types
	 * @return
	 */
	public Collection<TransactionType> getAllAccountTypes();
}
