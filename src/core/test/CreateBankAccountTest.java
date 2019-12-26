package core.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import core.business.BankingSystem;

public class CreateBankAccountTest {
	public void createBankAccount() {
		BankingSystem system = BankingSystem.getInstance();
		String userName = "balde";
		boolean accountCreated =  system.createBankAccount(userName);
		assertEquals(true, accountCreated);
	}
	
	@Test
	public void createBankAccountWithInitialBalance() {
		BankingSystem system = BankingSystem.getInstance();
		String userName = "killian";
		double initialBalance = -50.00;
		boolean accountCreated =  system.createBankAccount(userName, initialBalance);
		assertEquals(true, accountCreated);
	}
		
}
