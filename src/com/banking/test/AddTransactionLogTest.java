package com.banking.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import com.banking.bo.BankingSystem;
import com.banking.bo.types.TransactionType;
import com.banking.dao.MoneyTransferTransactionDAO;
import com.banking.dao.OneWayTransactionDAO;
import com.banking.exception.BankingSystemException;
import com.banking.exception.DatabaseException;
import com.banking.exception.LibraryException;

public class AddTransactionLogTest {
	public void addOneWayTransaction() {
		OneWayTransactionDAO transaction = new OneWayTransactionDAO(); 
		TransactionType type = TransactionType.Deposit;
		int accountNumber = 5;
		double amount = 30.00;
		boolean successfulLog = transaction.logTransaction(type, accountNumber, amount);	
		assertEquals(true,  successfulLog);
	}
	
	public void addTransferTrasnaction() {
		MoneyTransferTransactionDAO transaction = new MoneyTransferTransactionDAO();
		int srcAccount = 4;
		int destAccount = 11;
		double amount = 30.00;
		int transferId = 5;
		boolean logSuccess = transaction.logTransaction(srcAccount, destAccount, transferId, amount);
		assertEquals(true, logSuccess);
	}

	public void logDeposit() {
		BankingSystem system = BankingSystem.getInstance();
		int accountNumber = 21;
		double amount = 20000;
		boolean depositSuccess = false;
		try {
			depositSuccess = system.makeDeposit(accountNumber, amount);
		} catch (BankingSystemException | LibraryException | DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(true, depositSuccess);
	}

	public void logWithdrawal() {
		BankingSystem system = BankingSystem.getInstance();
		int accountNumber = 4;
		double amount = -300;
		double money = 0.00;
		try {
			money = system.makeWithdrawal(accountNumber, amount);
		} catch (BankingSystemException | DatabaseException | LibraryException e) {
			System.out.println(e.getMessage());
		}

		assertEquals(true, money > 0);
	}
	
	@Test
	public void logTransfer() {
		BankingSystem system = BankingSystem.getInstance();
		int srcAccount = 5;
		int destAccount = 11;
		double amount = 100;
		boolean success = false;
		try {
			success = system.postMoneyTransfer(srcAccount, destAccount, amount);
		} catch (BankingSystemException | DatabaseException | LibraryException e) {
			System.out.println(e.getMessage());
		}
		
		assertEquals(true, success);
	}
}
