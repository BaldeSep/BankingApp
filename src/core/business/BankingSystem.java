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
}
