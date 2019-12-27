package com.banking.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import com.banking.bo.BankingSystem;
import com.banking.bo.types.UserType;
import com.banking.dao.UserDAO;

public class RegisterUserTest {
	@Test
	public void registerUser() {
		BankingSystem system = BankingSystem.getInstance();
		String userName = "michealb";
		String password = "lookhere";
		UserType type = UserType.Employee;
		boolean result = system.registerUser(userName, password, type);
		assertEquals(true, result);
	}
}
