package com.banking.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import com.banking.bo.BankingSystem;
import com.banking.bo.types.TransactionType;

public class AddTransactionLogTest {
	@Test
	public void addTransactionLog() {
		BankingSystem system = BankingSystem.getInstance();
		TransactionType type = TransactionType.Deposit;
		int sourceAccount = 4;
		int destAccount = 5;
		double amount = 30.00;
		boolean transactionLogged = system.logTransaction(type, sourceAccount, destAccount, amount);
		assertEquals(true, transactionLogged);
	}
}
