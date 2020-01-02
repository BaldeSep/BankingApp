package com.banking.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.banking.bo.BankingSystem;
import com.banking.bo.MoneyTransfer;
import com.banking.exception.DatabaseException;
import com.banking.exception.LibraryException;

public class ViewMoneyTransferTest {
	private String validUserName = "colin";
	
	@Test
	public void viewMoneyTransferTest() {
		BankingSystem system = BankingSystem.getInstance();
		List<MoneyTransfer> transfers = new ArrayList<>();;
		try {
			transfers = system.viewMoneyTransfers(validUserName);
		} catch (DatabaseException | LibraryException e) {
			System.out.println(e.getMessage());
		}
		for(MoneyTransfer transfer : transfers) {
			System.out.println(transfer);
		}
		assertEquals(true, transfers.size() > 0);
	}
	
	
}
