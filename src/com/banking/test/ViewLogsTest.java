package com.banking.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.banking.bo.BankingSystem;
import com.banking.bo.MoneyTransferLog;
import com.banking.bo.OneWayLog;
import com.banking.bo.OneWayTransaction;
import com.banking.dao.MoneyTransferTransactionDAO;
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
	@Test
	public void testMoneyTransferLogs() {
		MoneyTransferTransactionDAO transfer = new MoneyTransferTransactionDAO();
		List<MoneyTransferLog> logs = new ArrayList<>();
		try {
			logs =  transfer.getLogs();
		} catch (DatabaseException | LibraryException e) {
			System.out.println(e);
		}
		
		for(MoneyTransferLog log: logs) {
			System.out.println(log);
		}
		
		assertEquals(true, logs.size() > 0);
		
	}
	
}
