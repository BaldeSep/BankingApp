package com.banking.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import com.banking.bo.BankingSystem;
import com.banking.exception.BankingSystemException;

public class Logging {
	@Test
	public void logDeposit() {
		BankingSystem system = BankingSystem.getInstance();
		int accountNumber = 21;
		double amount = 50.00;
		boolean depositSuccess = false;
		try {
			depositSuccess = system.makeDeposit(accountNumber, amount);
		} catch (BankingSystemException e) {
			System.out.println(e);
		}
		assertEquals(true, depositSuccess);
	}
}
