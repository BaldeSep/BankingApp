package com.banking.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import com.banking.bo.BankingSystem;
import com.banking.bo.RequestTicket;
import com.banking.exception.BankingSystemException;
import com.banking.exception.DatabaseException;
import com.banking.exception.LibraryException;

public class TicketRequestTest {
	public void generateRequestTicket() {
		BankingSystem system = BankingSystem.getInstance();
		String userName = "balde";
		double balance = -30000.00;
		RequestTicket resultTicket = new RequestTicket(userName, balance);
		try {
			resultTicket = system.generateRequestTicket(userName, balance);
		} catch (BankingSystemException e) {
			System.out.println(e);		}
		assertEquals(true, resultTicket.getInitialBalance() > 0);
	}
	
	@Test
	public void applyForBankAccount() {
		BankingSystem system = BankingSystem.getInstance();
		String userName = "balde";
		double balance = 100;
		RequestTicket ticket = new RequestTicket(userName, balance);
		try {
			ticket = system.generateRequestTicket(userName, balance);
		} catch (BankingSystemException e) {
			System.out.println(e.getMessage());
		}
		boolean applicationWasSent = false;
		try {
			applicationWasSent = system.applyForBankAccount(ticket);
		} catch (BankingSystemException | LibraryException | DatabaseException e) {
			System.out.println(e.getMessage());
		}
		assertEquals(true, applicationWasSent);
	}
}
