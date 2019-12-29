package com.banking.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import com.banking.bo.BankingSystem;
import com.banking.bo.types.UserType;
import com.banking.dao.UserDAO;
import com.banking.exception.DatabaseException;
import com.banking.exception.LibraryException;

public class RegisterUserTest {
	@Test
	public void registerUser() {
		BankingSystem system = BankingSystem.getInstance();
		String userName = "Gimbo";
		String password = "lookhere";
		UserType type = UserType.Customer;
		boolean result = false;
		try {
			result = system.registerUser(userName, password, type);
		} catch (DatabaseException | LibraryException e) {
			System.out.println(e);
		}
		assertEquals(true, result);
	}
}
