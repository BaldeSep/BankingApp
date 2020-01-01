package com.banking.bo;


public class RequestTicket {
	private int id;
	private String userName;
	private double initialBalance;	
	public RequestTicket() {
		// TODO Auto-generated constructor stub
	}
	public RequestTicket(int id, String userName, double initialBalance) {
		super();
		this.id = id;
		this.userName = userName;
		this.initialBalance = initialBalance;
	}

	public RequestTicket(String userName, double initialBalance) {
		super();
		this.userName = userName;
		this.initialBalance = initialBalance;
	}
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public double getInitialBalance() {
		return initialBalance;
	}

	public void setInitialBalance(double initialBalance) {
		this.initialBalance = initialBalance;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "RequestTicket [id=" + id + ", userName=" + userName + ", initialBalance=" + initialBalance + "]";
	}
	
	
	
	
}
