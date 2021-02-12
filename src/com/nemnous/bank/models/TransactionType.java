package com.nemnous.bank.models;

public class TransactionType {
	private short id;
	private String type;
	
	public TransactionType(String type) {
		super();
		this.setType(type);
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
