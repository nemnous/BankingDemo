package com.nemnous.bank.dao.inmemory.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

import com.nemnous.bank.dao.AccountTypeDAO;
import com.nemnous.bank.models.AccountType;


/**
 * Account type -> savings or fixed
 * Deals with the account type table
 * @author nemnous
 *
 */
public class InMemoryAccountTypeDAOImpl implements AccountTypeDAO{
	Collection<AccountType> accountTypes = new ArrayList<>();
	private static final AtomicInteger count = new AtomicInteger(0);
	
	/**
	 * returns all types as a collection.
	 * @return
	 */
	@Override
	public Collection<AccountType> getAllAccountTypes() {
		return accountTypes;
	}

	/**
	 * adds type to the account_type table
	 * @param accountType
	 * @return
	 */
	@Override
	public short addType(AccountType accountType) {
		short id = (short) count.incrementAndGet();
		accountType.setId(id);
		accountTypes.add(accountType);
		return id;
	}

	/**
	 * returns id given the type.
	 * @param type
	 * @return
	 */
	@Override
	public short getIdbyType(String type) {
		for (AccountType accountType : accountTypes) {
			if(accountType.getType().equals(type)) {
				return accountType.getId();
			}
		}
		return -1;
	}

	/**
	 * returns type given id
	 * @param id
	 * @return
	 */
	@Override
	public String getTypebyId(short id) {
		for (AccountType accountType : accountTypes) {
			if(accountType.getId() == id) {
				return accountType.getType();
			}
		}
		return null;
	}
	
}
