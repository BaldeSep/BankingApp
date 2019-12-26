package core.business;

public class BankingSystem {
	private static final BankingSystem bankingSystem = new BankingSystem();
	private UserDAO userDAO;
	private BankAccountDAO bankAccountDAO;
	public BankingSystem() {
		userDAO = new UserDAO();
		bankAccountDAO = new BankAccountDAO();
	}
	
	public static BankingSystem getInstance() {
		return bankingSystem;
	}
	
	public boolean registerUser(final String userName, final String password, final UserType type) {
		return userDAO.registerUser(userName, password, type);
	}
	
	public boolean verifyUserCredentials(final String userName, final String password) {
		return userDAO.verifyUserCredentials(userName, password);
	}

	public boolean createBankAccount(final String userName) {
		return bankAccountDAO.createBankAccount(userName);
	}
	
	public boolean createBankAccount(final String userName,final double initialBalance ) {
		return bankAccountDAO.createBankAccount(userName, initialBalance);
	}
}
