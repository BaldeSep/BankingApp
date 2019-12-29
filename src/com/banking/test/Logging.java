package com.banking.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import com.banking.bo.BankingSystem;
import com.banking.exception.BankingSystemException;
import com.banking.exception.DatabaseException;
import com.banking.exception.LibraryException;

public class Logging {
	@Test
	public void logDeposit() {
		BankingSystem system = BankingSystem.getInstance();
		int accountNumber = 300;
		double amount = -50.00;
		boolean depositSuccess = false;
		try {
			depositSuccess = system.makeDeposit(accountNumber, amount);
		} catch (BankingSystemException | LibraryException | DatabaseException e) {
			System.out.println(e.getMessage());
		}
		assertEquals(true, depositSuccess);
	}
}
