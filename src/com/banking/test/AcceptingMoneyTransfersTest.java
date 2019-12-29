package com.banking.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import com.banking.bo.BankingSystem;
import com.banking.exception.BankingSystemException;
import com.banking.exception.DatabaseException;
import com.banking.exception.LibraryException;

public class AcceptingMoneyTransfersTest {
	@Test
	public void acceptMoneyTransfer() {
		BankingSystem system = BankingSystem.getInstance();
		int transferId = 27;
		
		boolean success = false;
		try {
			success = system.acceptMoneyTransfer(transferId);
		} catch (BankingSystemException | LibraryException | DatabaseException e) {
			System.out.println(e.getMessage());
		}
		
		
		assertEquals(true, success);
	}
}
