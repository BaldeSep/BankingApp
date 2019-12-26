package core.business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class BankingSystem {
	private static final BankingSystem bankingSystem = new BankingSystem();
	private static final Logger log = Logger.getLogger(BankingSystem.class);
	
	public BankingSystem() {}
	
	public static BankingSystem getInstance() {
		return bankingSystem;
	}
	
	public boolean registerUser(String userName, String password, UserType type) {
		boolean userInserted = false;
		try(Connection connection = OracleDBConnection.getInstance()) {
			// Run SQL Command
			String insertUserSqlCommand = "Insert Into Users (user_name, password, user_type) Values (?, ?, ?)";
			PreparedStatement insertUserStatement = connection.prepareStatement(insertUserSqlCommand);
			// Get Result of SQL Command
			insertUserStatement.setString(1, userName);
			insertUserStatement.setString(2, password);
			insertUserStatement.setInt(3, type.ordinal());
			int count = insertUserStatement.executeUpdate();
			// If There was a successful insert return true 
			// Else return false
			if(count > 0) {
				userInserted = true;
			}
		} catch (ClassNotFoundException | SQLException e) {
			log.error(e);
		} 
		return userInserted;
	}
}
