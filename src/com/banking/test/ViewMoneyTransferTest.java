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
	@Test
	public void viewMoneyTransferTest() {
		BankingSystem system = BankingSystem.getInstance();
		String destinationUserName = "scdsk";
		List<MoneyTransfer> transfers = new ArrayList<>();;
		try {
			transfers = system.viewMoneyTransfers(destinationUserName);
		} catch (DatabaseException | LibraryException e) {
			System.out.println(e.getMessage());
		}
		for(MoneyTransfer transfer : transfers) {
			System.out.println(transfer);
		}
		assertEquals(true, transfers.size() > 0);
	}
}
