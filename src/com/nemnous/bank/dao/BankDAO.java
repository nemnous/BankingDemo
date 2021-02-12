package com.nemnous.bank.dao;

import java.util.Collection;

import com.nemnous.bank.models.Bank;

/**
 * this manages the bank table.
 * @author nemnous
 *
 */
public interface BankDAO {
	/**
	 * adds branch to the table.
	 * @param bank
	 * @return
	 */
	public long addBank(Bank bank);
	
	/**
	 * returns all branches from the table bank.
	 * @return
	 */
	public Collection<Bank> getAllBranches();
	/**
	 * returns the bank given the id of the bank.
	 * @param id
	 * @return
	 */
	public Bank getBankById(long id);
	/**
	 * returns the id of bank given the IFSC code
	 * @param ifsc
	 * @return
	 */
	public long getIdByIfsc(String ifsc);

}
