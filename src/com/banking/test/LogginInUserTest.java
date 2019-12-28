package com.banking.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import com.banking.dao.UserDAO;

public class LogginInUserTest {
	@Test
	public void loginUserTest() {
		UserDAO userDAO = new UserDAO();
		String userName = "killian";
		String password = "abc123";
		boolean userExists = userDAO.verifyUserCredentials(userName, password);
		assertEquals(true, userExists);	
	}
}