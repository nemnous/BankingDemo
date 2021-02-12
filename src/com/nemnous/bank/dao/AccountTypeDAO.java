package com.nemnous.bank.dao;

import java.util.Collection;

import com.nemnous.bank.models.AccountType;

/**
 * Account type -> savings or fixed
 * Deals with the account type table
 * @author nemnous
 *
 */
public interface AccountTypeDAO {
	/**
	 * adds type to the account_type table
	 * @param accountType
	 * @return
	 */
	public short addType(AccountType accountType);
	/**
	 * returns id given the type.
	 * @param type
	 * @return
	 */
	public short getIdbyType(String type);
	/**
	 * returns type given id
	 * @param id
	 * @return
	 */
	public String getTypebyId(short id);
	/**
	 * returns all types as a collection.
	 * @return
	 */
	public Collection<AccountType> getAllAccountTypes();
}
