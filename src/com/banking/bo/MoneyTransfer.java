package com.banking.bo;

public class MoneyTransfer {
	private int sourceAccountNumber;
	private int destinationAccountNumber;
	private double amount;
	
	public MoneyTransfer() {
	}

	public MoneyTransfer(int sourceAccountNumber, int destinationAccountNumber, double amount) {
		super();
		this.sourceAccountNumber = sourceAccountNumber;
		this.destinationAccountNumber = destinationAccountNumber;
		this.amount = amount;
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
		return "MoneyTransfer [sourceAccountNumber=" + sourceAccountNumber + ", destinationAccountNumber="
				+ destinationAccountNumber + ", amount=" + amount + "]";
	}
	
	
}
