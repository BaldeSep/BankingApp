package com.banking.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import com.banking.bo.BankingSystem;
import com.banking.bo.types.TransactionType;
import com.banking.dao.MoneyTransferDAO;
import com.banking.dao.MoneyTransferTransactionDAO;
import com.banking.dao.OneWayTransactionDAO;
import com.banking.exception.BankingSystemException;

public class AddTransactionLogTest {
	@Test
	public void addOneWayTransaction() {
		OneWayTransactionDAO transaction = new OneWayTransactionDAO(); 
		TransactionType type = TransactionType.Deposit;
		int accountNumber = 200;
		double amount = 30.00;
		boolean successfulLog = transaction.logTransaction(type, accountNumber, amount);	
		assertEquals(true,  successfulLog);
	}
	
	@Test
	public void addTransferTrasnaction() {
		MoneyTransferTransactionDAO transaction = new MoneyTransferTransactionDAO();
		int srcAccount = 4;
		int destAccount = 15;
		double amount = 30.00;
		int transferId = 6;
		boolean logSuccess = transaction.logTransaction(srcAccount, destAccount, transferId, amount);
		assertEquals(true, logSuccess);
	}
}
