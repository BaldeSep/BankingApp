package com.banking.bo;

import java.util.Date;

public class MoneyTransferLog {
	private MoneyTransferTransaction transfer;
	private String sourceAccountUserName, destinationAccountUserName;
	private Date dateOfTransaction;
	
	public MoneyTransferLog(MoneyTransferTransaction transfer, String sourceAccountUserName,
			String destinationAccountUserName, Date dateOfTransaction) {
		super();
		this.transfer = transfer;
		this.sourceAccountUserName = sourceAccountUserName;
		this.destinationAccountUserName = destinationAccountUserName;
		this.dateOfTransaction = dateOfTransaction;
	}

	public MoneyTransferLog() {
		// TODO Auto-generated constructor stub
	}

	public Date getDateOfTransaction() {
		return dateOfTransaction;
	}

	public void setDateOfTransaction(Date dateOfTransaction) {
		this.dateOfTransaction = dateOfTransaction;
	}

	public MoneyTransferTransaction getTransfer() {
		return transfer;
	}

	public void setTransfer(MoneyTransferTransaction transfer) {
		this.transfer = transfer;
	}

	public String getSourceAccountUserName() {
		return sourceAccountUserName;
	}

	public void setSourceAccountUserName(String sourceAccountUserName) {
		this.sourceAccountUserName = sourceAccountUserName;
	}

	public String getDestinationAccountUserName() {
		return destinationAccountUserName;
	}

	public void setDestinationAccountUserName(String destinationAccountUserName) {
		this.destinationAccountUserName = destinationAccountUserName;
	}

	@Override
	public String toString() {
		return "MoneyTransferLog [transfer=" + transfer + ", sourceAccountUserName=" + sourceAccountUserName
				+ ", destinationAccountUserName=" + destinationAccountUserName + ", dateOfTransaction="
				+ dateOfTransaction + "]";
	}


	
}
