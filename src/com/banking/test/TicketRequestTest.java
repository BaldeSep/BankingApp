package com.banking.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import com.banking.bo.BankingSystem;
import com.banking.bo.RequestTicket;

public class TicketRequestTest {
	public void generateRequestTicket() {
		BankingSystem system = BankingSystem.getInstance();
		String userName = "balde";
		double balance = 100.00;
		RequestTicket resultTicket = system.generateRequestTicket(userName, balance);
		assertEquals(true, resultTicket.getInitialBalance() >= 0);
	}
	@Test
	public void applyForBankAccount() {
		BankingSystem system = BankingSystem.getInstance();
		String userName = "balde";
		double balance = 50.30;
		RequestTicket ticket = system.generateRequestTicket(userName, balance);
		boolean applicationWasSent = system.applyForBankAccount(ticket);
		assertEquals(true, applicationWasSent);
	}
}
