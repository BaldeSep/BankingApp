package com.banking.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import com.banking.bo.BankingSystem;
import com.banking.exception.BankingSystemException;
import com.banking.exception.DatabaseException;
import com.banking.exception.LibraryException;

public class CreateBankAccountTest {
	public void createBankAccount() {
		BankingSystem system = BankingSystem.getInstance();
		String userName = "balde";
		boolean accountCreated = false;
		try {
			accountCreated = system.createBankAccount(userName);
		} catch (LibraryException | DatabaseException e) {
			System.out.println(e);
		}
		assertEquals(true, accountCreated);
	}
	@Test
	public void createBankAccountWithInitialBalance() {
		BankingSystem system = BankingSystem.getInstance();
		String userName = "kopo";
		double initialBalance = 200.45;
		boolean accountCreated = false;
		try {
			accountCreated = system.createBankAccount(userName, initialBalance);
		} catch (BankingSystemException | LibraryException | DatabaseException e) {
			System.out.println(e);
		}
		assertEquals(true, accountCreated);
	}
		
}
