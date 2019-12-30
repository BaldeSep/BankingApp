package com.banking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.banking.bo.User;
import com.banking.bo.types.UserType;
import com.banking.exception.DatabaseException;
import com.banking.exception.LibraryException;
import com.banking.util.OracleDBConnection;

public class UserDAO {
	private static final Logger log = Logger.getLogger(UserDAO.class);
	
	public UserDAO() {}
	
	// Register User and add them into the Database
	public boolean registerUser(String userName, String password, UserType type) throws DatabaseException, LibraryException {
		boolean userInserted = false;
		try(Connection connection = OracleDBConnection.getConnection()) {
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
			if(count == 1) {
				userInserted = true;
			}
		} catch (ClassNotFoundException e) {
			log.error(e); 
			throw new LibraryException("Sorry There Was An Unforseen Error That We Can't Recover From Try Again Later");
		} catch(SQLException e) {
			log.error(e);
			throw new DatabaseException("Sorry There Was An Issue With The Registration Process, Try Registering With A Different User Name.");
		}
		return userInserted;
	}
	
	// Verify If The User Exists Within the database
	public User verifyUserCredentials(String userName, String password) throws DatabaseException, LibraryException {
		User verifiedUser = null;
		try(Connection connection = OracleDBConnection.getConnection()){
			// Create SQL Command
			String queryUser = "Select user_type From Users Where user_name = ? And password = ?";
			PreparedStatement queryUserStatement = connection.prepareStatement(queryUser);
			// Add Dynamic Data
			queryUserStatement.setString(1, userName);
			queryUserStatement.setString(2, password);
			// Get Results of Query
			ResultSet results = queryUserStatement.executeQuery();
			// If there is a result then the user exists
			if(results.next()) {
				int userType = results.getInt("user_type");
				if(userType == UserType.Customer.ordinal()) {
					verifiedUser = new User(userName, password, UserType.Customer);
				}else {
					verifiedUser = new User(userName, password, UserType.Employee);
				}
			}else {
				DatabaseException databaseException = new DatabaseException("Sorry We Could Not Verify That User: " + userName + " Exists, Try Again With Different Credentials");
				log.error(databaseException);
				throw databaseException;
			}
		}catch(ClassNotFoundException e) {
			log.error(e);
			throw new LibraryException("Sorry There Was An Unforseen Error That We Can't Recover From Try Again Later");
		}catch(SQLException e) {
			log.error(e);
			throw new DatabaseException("Sorry There Was An Issue Trying To Verify Your Credientials. Try Again Later.");
		}
		
		return verifiedUser;
	}
	
	
}
