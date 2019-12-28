package com.banking.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import com.banking.bo.BankingSystem;
import com.banking.exception.BankingSystemException;

public class AcceptingMoneyTransfersTest {
	@Test
	public void acceptMoneyTransfer() {
		BankingSystem system = BankingSystem.getInstance();
		int transferId = 5;
		boolean success = false;
		try {
			success = system.acceptMoneyTransfer(transferId);
		}catch(BankingSystemException e) {
			System.out.println(e);
		}
		assertEquals(true, success);
	}
}
