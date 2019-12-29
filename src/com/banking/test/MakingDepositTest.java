package com.banking.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import com.banking.bo.BankingSystem;
import com.banking.exception.BankingSystemException;
import com.banking.exception.DatabaseException;
import com.banking.exception.LibraryException;

public class MakingDepositTest {
	@Test
	public void makeDeposit(){
		BankingSystem system = BankingSystem.getInstance();
		int accountNumber = 200;
		double amount = 2345.23;
		boolean successful = false;
		try {
			successful = system.makeDeposit(accountNumber, amount);
		} catch (BankingSystemException | LibraryException | DatabaseException e) {
			System.out.println(e.getMessage());
		}
		assertEquals(true, successful);
	}
}
