package com.banking.bo;

import com.banking.bo.types.MoneyTransferState;

public class MoneyTransfer {
	private int id;
	private int sourceAccountNumber;
	private int destinationAccountNumber;
	private double amount;
	private MoneyTransferState state;
	
	public MoneyTransfer() {
		// TODO Auto-generated constructor stub
	}

	public MoneyTransfer(int id, int sourceAccountNumber, int destinationAccountNumber, double amount,
			MoneyTransferState state) {
		super();
		this.id = id;
		this.sourceAccountNumber = sourceAccountNumber;
		this.destinationAccountNumber = destinationAccountNumber;
		this.amount = amount;
		this.state = state;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public MoneyTransferState getState() {
		return state;
	}

	public void setState(MoneyTransferState state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "MoneyTransfer [id=" + id + ", sourceAccountNumber=" + sourceAccountNumber
				+ ", destinationAccountNumber=" + destinationAccountNumber + ", amount=" + amount + ", state=" + state
				+ "]";
	}
	
	
	
	
}
