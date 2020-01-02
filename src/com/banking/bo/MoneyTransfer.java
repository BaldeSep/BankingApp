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
		return "Money Transfer ID: [" + id + "] Source Account Number: [" + sourceAccountNumber
				+ "] Destination Account Number : [" + destinationAccountNumber + "] Amount: [$" + amount + "] State: [" + state + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + destinationAccountNumber;
		result = prime * result + id;
		result = prime * result + sourceAccountNumber;
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MoneyTransfer other = (MoneyTransfer) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (destinationAccountNumber != other.destinationAccountNumber)
			return false;
		if (id != other.id)
			return false;
		if (sourceAccountNumber != other.sourceAccountNumber)
			return false;
		if (state != other.state)
			return false;
		return true;
	}
	
	
	
	
	
	
}
