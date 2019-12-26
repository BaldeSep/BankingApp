package core.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import core.business.BankingSystem;
import core.business.UserDAO;
import core.business.UserType;

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
