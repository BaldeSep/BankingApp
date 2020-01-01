package com.banking.menus;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.Test;

import com.banking.bo.RequestTicket;
import com.banking.dao.RequestTicketDAO;
import com.banking.exception.DatabaseException;
import com.banking.exception.LibraryException;

public class GetAllApplicationsTest {
	private String validUserName = "balde";
	private String invalidUserName = "dsclknd";
	@Test
	public void viewApplications() {
		RequestTicketDAO ticketDAO = new RequestTicketDAO();
		List<RequestTicket> tickets = null;
		List<RequestTicket> tickets2 = null;
		try {
			tickets = ticketDAO.getTickets(validUserName);
			tickets2 = ticketDAO.getTickets(invalidUserName);
		} catch (LibraryException | DatabaseException e) {
			System.out.println(e.getMessage());
		}
		
		for(RequestTicket ticket: tickets) {
			System.out.println(ticket);
		}
		
		assertEquals(true, tickets != null && tickets2 == null);
	}
}
