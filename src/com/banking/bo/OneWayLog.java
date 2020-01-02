package com.banking.bo;

import java.util.Date;

public class OneWayLog {
	private OneWayTransaction transaction;
	private String holder;
	private Date dateOfTransaction;
	private int logId;
	
	public OneWayLog() {
		// TODO Auto-generated constructor stub
	}

	public OneWayLog(OneWayTransaction transaction, String holder, Date dateOfTransaction, int logId) {
		super();
		this.transaction = transaction;
		this.holder = holder;
		this.dateOfTransaction = dateOfTransaction;
		this.logId = logId;
	}

	public OneWayTransaction getTransaction() {
		return transaction;
	}

	public void setTransaction(OneWayTransaction transaction) {
		this.transaction = transaction;
	}

	public String getHolder() {
		return holder;
	}

	public void setHolder(String holder) {
		this.holder = holder;
	}

	public Date getDateOfTransaction() {
		return dateOfTransaction;
	}

	public void setDateOfTransaction(Date dateOfTransaction) {
		this.dateOfTransaction = dateOfTransaction;
	}

	public int getLogId() {
		return logId;
	}

	public void setLogId(int logId) {
		this.logId = logId;
	}

	@Override
	public String toString() {
		return "Transaction: [" + transaction + "] Account Holder: [" + holder + "] Date Of Transaction: ["
				+ dateOfTransaction + "] Log ID: [" + logId + "]";
	}
	
	
}
