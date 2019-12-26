package core.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import core.business.BankingSystem;

public class CreateBankAccountTest {
	@Test
	public void createBankAccount() {
		BankingSystem system = BankingSystem.getInstance();
		String userName = "loki";
		boolean accountCreated =  system.createBankAccount(userName);
		assertEquals(true, accountCreated);
	}
		
}
