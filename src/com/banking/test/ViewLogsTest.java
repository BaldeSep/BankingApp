package com.banking.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.banking.bo.BankingSystem;
import com.banking.bo.OneWayLog;
import com.banking.bo.OneWayTransaction;
import com.banking.exception.DatabaseException;
import com.banking.exception.LibraryException;


public class ViewLogsTest {
	@Test
	public void testDepositAndWithdrawalLogs() {
		BankingSystem system = BankingSystem.getInstance();
		List<OneWayLog> transactions = Collections.<OneWayLog>emptyList();
		try {
			transactions = system.viewOneWayTransactions();
		} catch (DatabaseException | LibraryException e) {
			System.out.println(e.getMessage());
		}
		for(OneWayLog log: transactions) {
			System.out.println(log);
		}
		
		assertEquals(true, transactions.size() > 0);
	}
}
