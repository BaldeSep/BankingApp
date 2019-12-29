package com.banking.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import com.banking.bo.BankAccount;
import com.banking.bo.BankingSystem;
import com.banking.exception.DatabaseException;
import com.banking.exception.LibraryException;

public class GetAccountUsingIdTest {
	@Test
	public void getAccountById() {
		BankingSystem system = BankingSystem.getInstance();
		int accountNumber = 44;
		BankAccount account = null;
		try {
			account = system.getAccount(accountNumber);
		} catch (LibraryException | DatabaseException e) {
			System.out.println(e.getMessage());
		}
		System.out.println(account);
		assertEquals(false, account == null);
	}
}
