package com.nemnous.bank.dao.inmemory.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

import com.nemnous.bank.dao.BankDAO;
import com.nemnous.bank.models.Bank;



/**
 * this manages the bank table.
 * @author nemnous
 *
 */
public class InMemoryBankDAOImpl implements BankDAO {
	
	Collection<Bank> branches = new ArrayList<>();
	private static final AtomicInteger count = new AtomicInteger(0);


	/**
	 * adds branch to the table.
	 * @param bank
	 * @return
	 */
	@Override
	public long addBank(Bank bank) {
		long id = count.incrementAndGet();
		bank.setId(id);
		branches.add(bank);
		return id;
	}

	/**
	 * returns all branches from the table bank.
	 * @return
	 */
	@Override
	public Collection<Bank> getAllBranches() {
		return branches;
	}

	/**
	 * returns the bank given the id of the bank.
	 * @param id
	 * @return
	 */
	@Override
	public Bank getBankById(long id) {
		for (Bank bank : branches) {
			if (bank.getId() == id) {
				return bank;
			}
		}
		return null;
	}

	/**
	 * returns the id of bank given the IFSC code
	 * @param ifsc
	 * @return
	 */
	@Override
	public long getIdByIfsc(String ifsc) {
		for (Bank bank : branches) {
			if(bank.getIfscCode().equals(ifsc)) {
				return bank.getId();
			}
 		}
		return -1;
	}

}
