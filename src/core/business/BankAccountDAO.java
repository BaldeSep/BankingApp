package core.business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class BankAccountDAO {
	private static final Logger log = Logger.getLogger(BankAccountDAO.class);
	
	public boolean createBankAccount(final String userName) {
		boolean createdAccount = false;
		
		try(Connection connection = OracleDBConnection.getInstance()){
			// Create SQL Statement
			String insertBankAccount = "INSERT INTO BANKACCOUNTS (holder) VALUES (?)";
			PreparedStatement insertBankAccountStatement = connection.prepareStatement(insertBankAccount);
			insertBankAccountStatement.setString(1, userName);
			// Execute Statement
			int count = insertBankAccountStatement.executeUpdate();
			if(count > 0) {
				createdAccount = true;
			}
		}catch(ClassNotFoundException e) {
			log.error(e);
		}catch(SQLException e) {
			log.error(e);
		}
		
		return createdAccount;
	}
	
	public boolean createBankAccount(final String userName, final double initialBalance) {
		boolean createdAccount = false;
		
		try(Connection connection = OracleDBConnection.getInstance()){
			// Create SQL Statement
			String insertBankAccount = "INSERT INTO BANKACCOUNTS (holder, balance) VALUES (?, ?)";
			PreparedStatement insertBankAccountStatement = connection.prepareStatement(insertBankAccount);
			insertBankAccountStatement.setString(1, userName);
			if(initialBalance >= 0.0) {
				insertBankAccountStatement.setDouble(2, initialBalance);
			}else {
				insertBankAccountStatement.setDouble(2, 0.00);
			}
			// Execute Statement
			int count = insertBankAccountStatement.executeUpdate();
			if(count > 0) {
				createdAccount = true;
			}
		}catch(ClassNotFoundException e) {
			log.error(e);
		}catch(SQLException e) {
			log.error(e);
		}
		
		return createdAccount;
	}
}
