package com.banking.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import com.banking.bo.BankingSystem;
import com.banking.dao.UserDAO;
import com.banking.exception.BankingSystemException;
import com.banking.exception.DatabaseException;
import com.banking.exception.LibraryException;

public class LogginInUserTest {
	@Test
	public void loginUserTest() {
		BankingSystem system = BankingSystem.getInstance();
		String userName = "colin";
		String password = "abc123";
		boolean userExists = false;
		try {
			userExists = system.verifyUserCredentials(userName, password);
		} catch (DatabaseException | LibraryException e) {
		}
		assertEquals(true, userExists);	
	}
}
