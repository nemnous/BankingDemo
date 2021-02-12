package com.nemnous.bank.models;


public class Customer {
	private long id;
	private String phone;
	private String address;
	private String name;
	private long branchId;
	
	
	public Customer() {
		
	}

	public Customer(String phone, String address, String name, long branchId) {
		super();
		this.phone = phone;
		this.address = address;
		this.name = name;
		this.branchId = branchId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getBranchId() {
		return branchId;
	}

	public void setBranchId(long branchId) {
		this.branchId = branchId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	
	
//	private Account account;
	//https://www.developer.com/java/data/how-to-implement
	//-database-relationship-in-hibernate.html
//	private Collection<Account>
	
	
	//List of accounts
}
