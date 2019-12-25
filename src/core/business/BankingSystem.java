package core.business;

public class BankingSystem {
	private static final BankingSystem bankingSystem = new BankingSystem();
	
	public BankingSystem() {}
	
	public static BankingSystem getInstance() {
		return bankingSystem;
	}
	
	public boolean registerUser(String userName, String password, UserType type) {
		return true;
	}
}
