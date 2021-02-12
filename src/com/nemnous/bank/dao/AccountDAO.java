package com.nemnous.bank.dao;

import java.math.BigDecimal;
import java.util.Collection;

import com.nemnous.bank.models.Account;

/**
 * DAO for acccount table
 * @author nemnous
 *
 */
public interface AccountDAO {
	
	/**
	 * adds account to the table
	 * @param account
	 * @return
	 */
	public long addAccount(Account account);
	
	/**
	 * returns all the accounts from the account table
	 * @return
	 */
	public Collection<Account> getAllAccounts();
	
	/**
	 * returns an account given an account number.
	 * @param accountNumber
	 * @return
	 */
	public Account getAccountByAccNumber(String accountNumber);
	
//	https://www.ibm.com/support/knowledgecenter/en/SSEPEK_12.0.0/java/src/tpc/imjcc_rjvjdata.html
	/**
	 * returns the balance given an account number
	 * @param accountNumber
	 * @return
	 */
	public BigDecimal getBalancebyAccNumber(String accountNumber);
	
	/**
	 * Deposits money to the account
	 * @param accountNumber
	 * @param amount
	 * @return
	 */
	public BigDecimal depositToAccount(String accountNumber, BigDecimal amount);

	/**
	 * withdraws money from the account
	 * @param accountNumber
	 * @param amount
	 * @return
	 */
	public BigDecimal withdrawToAccount(String accountNumber, BigDecimal amount);

}
