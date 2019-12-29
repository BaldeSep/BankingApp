package com.banking.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.banking.bo.BankAccount;
import com.banking.bo.BankingSystem;
import com.banking.exception.DatabaseException;
import com.banking.exception.LibraryException;

public class CheckingBalanceOnAccountTest {
	@Test
	public void viewBalanceOfAccount() {
		BankingSystem system = BankingSystem.getInstance();
		String userName = "Gimbo";
		List<BankAccount> accounts = new ArrayList<>();
		try {
			accounts = system.getAccounts(userName);
		} catch (LibraryException | DatabaseException e) {
			System.out.println(e.getMessage());
		}
		for(BankAccount account: accounts) {
			System.out.println(account);	
		}
		assertEquals(false, accounts.size() == 0);
	}
}
