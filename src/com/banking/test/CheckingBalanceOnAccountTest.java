package com.banking.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.Test;

import com.banking.bo.BankAccount;
import com.banking.bo.BankingSystem;

public class CheckingBalanceOnAccountTest {
	@Test
	public void viewBalanceOfAccount() {
		BankingSystem system = BankingSystem.getInstance();
		String userName = "colin";
		List<BankAccount> accounts = system.getAccounts(userName);
		for(BankAccount account: accounts) {
			System.out.println(account.getBalance());
		}
		assertEquals(false, accounts.size() == 0);
	}
}
