package com.banking.bo;

import com.banking.bo.types.TransactionType;

public class OneWayTransaction {
	private TransactionType type;
	private int accountNumber;
	private double amount;
	
	public OneWayTransaction() {
		// TODO Auto-generated constructor stub
	}

	public OneWayTransaction(TransactionType type, int accountNumber, double amount) {
		super();
		this.type = type;
		this.accountNumber = accountNumber;
		this.amount = amount;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Type Of Transaction: [" + type + "] Account Number: [" + accountNumber + "] Amount: [$" + amount + "]";
	}
	
	
}
