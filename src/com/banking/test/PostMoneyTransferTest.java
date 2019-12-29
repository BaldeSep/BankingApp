package com.banking.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import com.banking.bo.BankingSystem;
import com.banking.exception.BankingSystemException;
import com.banking.exception.DatabaseException;
import com.banking.exception.LibraryException;

public class PostMoneyTransferTest {
	@Test
	public void postMoneyTransfer() {
		BankingSystem system = BankingSystem.getInstance();
		int sourceAccountNumber = 21;
		int destinationAccountNumber = 12; 
		double amountToTransfer = 5555.00;
		boolean posted = false;
		try {
			posted = system.postMoneyTransfer(sourceAccountNumber, destinationAccountNumber, amountToTransfer);
		} catch (BankingSystemException | DatabaseException | LibraryException e) {
			System.out.println(e.getMessage());
		}
		assertEquals(true, posted);
		
	}
}
