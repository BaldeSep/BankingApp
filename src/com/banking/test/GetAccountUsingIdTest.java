package com.banking.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import com.banking.bo.BankAccount;
import com.banking.bo.BankingSystem;

public class GetAccountUsingIdTest {
	@Test
	public void getAccountById() {
		BankingSystem system = BankingSystem.getInstance();
		int accountNumber = 17;
		BankAccount account = system.getAccount(accountNumber);
		System.out.println(account);
		assertEquals(false, account == null);
	}
}
