package com.banking.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import com.banking.bo.BankingSystem;
import com.banking.exception.BankingSystemException;

public class MakingDepositTest {
	@Test
	public void makeDeposit(){
		BankingSystem system = BankingSystem.getInstance();
		int accountNumber = 4;
		double amount = 2345.23;
		boolean successful = false;
		try {
			successful = system.makeDeposit(accountNumber, amount);			
		}catch(BankingSystemException e) {
			System.out.println(e);
		}
		assertEquals(true, successful);
	}
}
