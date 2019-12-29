package com.banking.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import com.banking.bo.BankingSystem;
import com.banking.exception.BankingSystemException;
import com.banking.exception.DatabaseException;
import com.banking.exception.LibraryException;

public class MakeWithdrawalTest {
	@Test
	public void makeWithdrawal(){
		BankingSystem system = BankingSystem.getInstance();
		int accountNumber = 100;
		double amountToWithdrawal = 150.00;
		double money = 0.00;
		try {
			money = system.makeWithdrawal(accountNumber, amountToWithdrawal);
		} catch (BankingSystemException | DatabaseException | LibraryException e) {
			System.out.println(e.getMessage());
		}
		System.out.println(money);
		assertEquals(false, money == 0.00);
	}
}
