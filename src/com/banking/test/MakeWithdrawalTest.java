package com.banking.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import com.banking.bo.BankingSystem;
import com.banking.exception.BankingSystemException;

public class MakeWithdrawalTest {
	@Test
	public void makeWithdrawal(){
		BankingSystem system = BankingSystem.getInstance();
		int accountNumber = 4;
		double amountToWithdrawal = 150.00;
		double money = 0.00;
		try{
			money = system.makeWithdrawal(accountNumber, amountToWithdrawal);
		}catch(BankingSystemException e) {
			System.out.println(e);
		}
		System.out.println(money);
		assertEquals(false, money == 0.00);
	}
}
