package com.banking.bo;

public class MoneyTransferTransaction {
	private int sourceAccount;
	private int destinationAccount;
	private double amount;
	
	public MoneyTransferTransaction() {
		// TODO Auto-generated constructor stub
	}

	public MoneyTransferTransaction(int sourceAccount, int destinationAccount, double amount) {
		super();
		this.sourceAccount = sourceAccount;
		this.destinationAccount = destinationAccount;
		this.amount = amount;
	}

	public int getSourceAccount() {
		return sourceAccount;
	}

	public void setSourceAccount(int sourceAccount) {
		this.sourceAccount = sourceAccount;
	}

	public int getDestinationAccount() {
		return destinationAccount;
	}

	public void setDestinationAccount(int destinationAccount) {
		this.destinationAccount = destinationAccount;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "MoneyTransferTransaction [sourceAccount=" + sourceAccount + ", destinationAccount=" + destinationAccount
				+ ", amount=" + amount + "]";
	}
	
}
