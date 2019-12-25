package core.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import core.business.BankingSystem;
import core.business.UserType;

public class RegisterUserTest {
	@Test
	public void registerUser() {
		BankingSystem system = BankingSystem.getInstance();
		String userName = "balde";
		String password = "123";
		UserType type = UserType.Customer;
		boolean result = system.registerUser(userName, password, type);
		assertEquals(true, result);
	}
}
