package com.banking.bo;

import com.banking.bo.types.TransactionType;

public class Transaction {
	private TransactionType type;
	private int sourceAccountNumber;
	private int destinationAccountNumber;
	private double amount;
	
	public Transaction() {
		// TODO Auto-generated constructor stub
	}

	public Transaction(TransactionType type, int sourceAccountNumber, int destinationAccountNumber, double amount) {
		super();
		this.type = type;
		this.sourceAccountNumber = sourceAccountNumber;
		this.destinationAccountNumber = destinationAccountNumber;
		this.amount = amount;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public int getSourceAccountNumber() {
		return sourceAccountNumber;
	}

	public void setSourceAccountNumber(int sourceAccountNumber) {
		this.sourceAccountNumber = sourceAccountNumber;
	}

	public int getDestinationAccountNumber() {
		return destinationAccountNumber;
	}

	public void setDestinationAccountNumber(int destinationAccountNumber) {
		this.destinationAccountNumber = destinationAccountNumber;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Transaction [sourceAccountNumber=" + sourceAccountNumber + ", destinationAccountNumber="
				+ destinationAccountNumber + ", amount=" + amount + "]";
	}
	
}
