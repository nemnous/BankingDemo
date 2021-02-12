package com.nemnous.bank.models;

import java.math.BigDecimal;

public class Transaction {
	private long id;
	private long accountId;
	private short transactionTypeId;
	private BigDecimal amount;
	
	public Transaction(long accountId, short transactionTypeId, BigDecimal amount) {
		super();
		this.accountId = accountId;
		this.transactionTypeId = transactionTypeId;
		this.amount = amount;
	}
	public long getAccountId() {
		return accountId;
	}
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	public short getTransactionTypeId() {
		return transactionTypeId;
	}
	public void setTransactionTypeId(short transactionTypeId) {
		this.transactionTypeId = transactionTypeId;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
}
