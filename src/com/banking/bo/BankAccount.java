package com.banking.bo;

public class BankAccount {
	private int accountNumber;
	private String holder;
	private double balance;
	
	public BankAccount() {
		// TODO Auto-generated constructor stub
	}

	public BankAccount(int accountNumber, String holder, double balance) {
		super();
		this.accountNumber = accountNumber;
		this.holder = holder;
		this.balance = balance;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getHolder() {
		return holder;
	}

	public void setHolder(String holder) {
		this.holder = holder;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "accountNumber:" + accountNumber + "| holder:" + holder + "| balance:$" + balance;
	}
	
	
	
	
}
