package com.nemnous.bank.models;

public class Bank {
	private long id;
	private String name;
	private String ifscCode;
	private String city;
	
	public Bank(String name, String ifscCode, String city) {
		super();
		this.name = name;
		this.ifscCode = ifscCode;
		this.city = city;
	}
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getIfscCode() {
		return ifscCode;
	}
	
	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}

	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}
	
}
