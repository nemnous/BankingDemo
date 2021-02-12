package com.nemnous.bank.models;

import java.math.BigDecimal;

public class Account {
	private long id;
	private String accountNumber;
	private BigDecimal balance;
	long customerId;
	short accountTypeId;
	
	public Account() {
		
	}

	public Account(String accountNumber, BigDecimal balance, long customerId, short accountTypeId) {
		super();
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.customerId = customerId;
		this.accountTypeId = accountTypeId;
	}



	public String getAccountNumber() {
		return accountNumber;
	}



	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}



	public BigDecimal getBalance() {
		return balance;
	}



	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}



	public long getCustomerId() {
		return customerId;
	}



	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}



	public short getAccountTypeId() {
		return accountTypeId;
	}



	public void setAccountTypeId(short accountTypeId) {
		this.accountTypeId = accountTypeId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	
	
	
}
