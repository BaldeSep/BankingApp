package com.banking.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import com.banking.dao.RequestTicketDAO;
import com.banking.exception.BankingSystemException;
import com.banking.exception.DatabaseException;
import com.banking.exception.LibraryException;

public class ApproveAccountsTest {
	private int invalidId = 18;
	
	@Test
	public void approveAccount() {
		RequestTicketDAO ticket = new RequestTicketDAO();
		boolean accountCreated = false;
		try {
			accountCreated = ticket.approveApplication(invalidId);
		} catch (DatabaseException | LibraryException | BankingSystemException e) {
			System.out.println(e.getMessage());
		}
		
		assertEquals(true, accountCreated);
	}
}
