package core.business;

public class BankingSystem {
	private static final BankingSystem bankingSystem = new BankingSystem();
	private UserDAO userDAO;
	public BankingSystem() {
		userDAO = new UserDAO();
	}
	
	public static BankingSystem getInstance() {
		return bankingSystem;
	}
	
	public boolean registerUser(String userName, String password, UserType type) {
		return userDAO.registerUser(userName, password, type);
	}
	
	public boolean verifyUserCredentials(String userName, String password) {
		return userDAO.verifyUserCredentials(userName, password);
	}

	public boolean createBankAccount(String userName) {
		return userDAO.createBankAccount(userName);
	}
}
