package com.nemnous.bank.models;

public class AccountType {
	private short id;
	private String type;

	
	public AccountType(String type) {
		super();
		this.type = type;
	}

	public AccountType() {
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public short getId() {
		return id;
	}

	public void setId(short id) {
		this.id = id;
	}
	
}
