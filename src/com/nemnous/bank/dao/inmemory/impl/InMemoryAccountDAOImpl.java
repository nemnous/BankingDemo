package com.nemnous.bank.dao.inmemory.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.nemnous.bank.dao.AccountDAO;
import com.nemnous.bank.exceptions.InsufficientBalanceException;
import com.nemnous.bank.exceptions.InvalidDetailsException;
import com.nemnous.bank.models.Account;

/**
 * DAO for acccount table inMemory
 * @author nemnous
 *
 */
public class InMemoryAccountDAOImpl implements AccountDAO {
	Collection<Account> accounts = new ArrayList<>();
	private static final AtomicInteger count = new AtomicInteger(0);
	private Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/**
	 * adds account to the table
	 * @param account
	 * @return
	 */
	@Override
	public long addAccount(Account account) {
		long id = count.incrementAndGet();
		account.setId(id);
		accounts.add(account);
		return id;
	}

	/**
	 * returns all the accounts from the account table
	 * @return
	 */
	@Override
	public Collection<Account> getAllAccounts() {
		return accounts;
	}
	
	/**
	 * returns an account given an account number.
	 * @param accountNumber
	 * @return
	 */
	@Override
	public Account getAccountByAccNumber(String accountNumber) {
		for (Account account : accounts) {
			if(account.getAccountNumber().equals(accountNumber)) {
				return account;
			}
		}
		return null;
	}

	/**
	 * returns the balance given an account number
	 * @param accountNumber
	 * @return
	 */
	@Override
	public BigDecimal getBalancebyAccNumber(String accountNumber) {
		Account account = getAccountByAccNumber(accountNumber);
		return account.getBalance();
	}
	
	/**
	 * support function which adds amount to the account
	 * @param accountNumber
	 * @param amount
	 * @return
	 */
	private BigDecimal updateBalance(String accountNumber, BigDecimal amount) {
		try {
			Account account = getAccountByAccNumber(accountNumber);
			BigDecimal currentBalance = account.getBalance();
			BigDecimal updatedBalance = currentBalance.add(amount);
			account.setBalance(updatedBalance);
			return updatedBalance;
		} catch (NullPointerException e) {
			logger.log(Level.WARNING, "Account Not found");
		}
		return new BigDecimal(-1);
	}


	/**
	 * Deposits money to the account
	 * @param accountNumber
	 * @param amount
	 * @return
	 */
	@Override
	public BigDecimal depositToAccount(String accountNumber, BigDecimal amount) {
		if (amount.compareTo(BigDecimal.ZERO)< 0) {
			throw new InvalidDetailsException("Amount cannot be negative");
		}
		return updateBalance(accountNumber, amount);
	}
	
	/**
	 * withdraws money from the account
	 * @param accountNumber
	 * @param amount
	 * @return
	 */
	@Override
	public BigDecimal withdrawToAccount(String accountNumber, BigDecimal amount) {
		if (amount.compareTo(BigDecimal.ZERO) < 0) {
			throw new InvalidDetailsException("Amount cannot be zero or negative");
		}
		try {
			BigDecimal currentBalance = getAccountByAccNumber(accountNumber).getBalance();
			if(currentBalance.compareTo(amount) < 0) {
				throw new InsufficientBalanceException("Insufficient balance");
			}
		} catch (NullPointerException e) {
			logger.log(Level.WARNING, "Account Not found");
			return new BigDecimal(-1);
		}
		return updateBalance(accountNumber, amount.negate());
	}

}
