package com.banking.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.Test;

import com.banking.bo.BankingSystem;
import com.banking.bo.MoneyTransfer;

public class ViewMoneyTransferTest {
	@Test
	public void viewMoneyTransferTest() {
		BankingSystem system = BankingSystem.getInstance();
		String destinationUserName = "killian";
		List<MoneyTransfer> transfers = system.viewMoneyTransfers(destinationUserName);
		for(MoneyTransfer transfer : transfers) {
			System.out.println(transfer);
		}
		assertEquals(true, transfers.size() > 0);
	}
}
