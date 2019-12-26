package core.business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class UserDAO {
	private static final Logger log = Logger.getLogger(UserDAO.class);
	
	public UserDAO() {}
	
	// Register User and add them into the Database
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
		} catch (ClassNotFoundException e) {
			log.error(e);
		} catch(SQLException e) {
			log.error(e);
		}
		return userInserted;
	}
	
	// Verify If The User Exists Within the database
	public boolean verifyUserCredentials(String userName, String password) {
		boolean userExists = false;
		
		try(Connection connection = OracleDBConnection.getInstance()){
			// Create SQL Command
			String queryUser = "Select user_name From Users Where user_name = ? And password = ?";
			PreparedStatement queryUserStatement = connection.prepareStatement(queryUser);
			// Add Dynamic Data
			queryUserStatement.setString(1, userName);
			queryUserStatement.setString(2, password);
			// Get Results of Query
			ResultSet results = queryUserStatement.executeQuery();
			// If there is a result then the user exists
			if(results.next()) {
				userExists = true;
			}
		}catch(ClassNotFoundException e) {
			log.error(e);
		}catch(SQLException e) {
			log.error(e);
		}
		
		return userExists;
	}
	
	
	
}
