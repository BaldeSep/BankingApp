package com.banking.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import com.banking.bo.BankingSystem;
import com.banking.exception.BankingSystemException;

public class MakeMoneyTranserTest {
	@Test
	public void postMoneyTransfer() {
		BankingSystem system = BankingSystem.getInstance();
		int sourceAccountNumber = 201;
		int destinationAccountNumber = 40; 
		double amountToTransfer = -300.00;
		boolean posted = false;
		try {
			posted = system.postMoneyTransfer(sourceAccountNumber, destinationAccountNumber, amountToTransfer);
		}catch(BankingSystemException e) {
			System.out.println(e.getMessage());
		}
		assertEquals(true, posted);
		
	}
}
