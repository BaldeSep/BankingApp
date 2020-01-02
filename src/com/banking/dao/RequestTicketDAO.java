package com.banking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.banking.bo.RequestTicket;
import com.banking.exception.BankingSystemException;
import com.banking.exception.DatabaseException;
import com.banking.exception.LibraryException;
import com.banking.util.OracleDBConnection;


public class RequestTicketDAO {
	// private static final Logger log = Logger.getLogger(RequestTicketDAO.class);
	public List<RequestTicket> getTickets(String userName) throws LibraryException, DatabaseException{
		List<RequestTicket> applications = new ArrayList<>(); 
		try(Connection connection = OracleDBConnection.getConnection()){
			String sqlGetApplications = 
					"Select ticket_id, user_name, default_balance " + 
					"From requestticket " + 
					"Where user_name = ? ";
			PreparedStatement statementGetApplications = connection.prepareStatement(sqlGetApplications);
			statementGetApplications.setString(1, userName);
			ResultSet applicationsSet = statementGetApplications.executeQuery();
			boolean applicationsExist = false;
			RequestTicket tempTicket;
			while(applicationsSet.next()) {
				applicationsExist = true;
				tempTicket = new RequestTicket();
				tempTicket.setId(applicationsSet.getInt("ticket_id"));
				tempTicket.setUserName(applicationsSet.getString("user_name"));
				tempTicket.setInitialBalance(applicationsSet.getDouble("default_balance"));
				applications.add(tempTicket);
			}
			if(!applicationsExist) {
				throw new DatabaseException("No Applications Found For User: ["   + userName + "]");
			}
		} catch (ClassNotFoundException e) {
			throw new LibraryException();
		} catch (SQLException e) {
			throw new DatabaseException();
		}
		return applications;
	}
	
	
	public boolean approveApplication(int ticketId) throws DatabaseException, LibraryException, BankingSystemException {
		boolean accountCreated = false;
		try(Connection connection = OracleDBConnection.getConnection()) {
			String sqlGetTicket = 
					"Select user_name, default_balance From requestticket Where ticket_id = ?";
			PreparedStatement statementGetTicketInfo = connection.prepareStatement(sqlGetTicket);
			statementGetTicketInfo.setInt(1, ticketId);
			ResultSet setTicket = statementGetTicketInfo.executeQuery();
			String userName = "";
			double balance = 0.00;
			if(setTicket.next()) {
				userName = setTicket.getString("user_name");
				balance = setTicket.getDouble("default_balance");
			}else {
				throw new DatabaseException("That Ticket: [" +  ticketId +"] Could Not Be Found...");
			}
			
			if(balance < 0) {
				throw new BankingSystemException("Invalid Default Balance Of: [" +  balance + "]");
			}
			
			String sqlDeleteTicket = "Delete From requestticket Where ticket_id = ?";
			PreparedStatement statementDeleteTicket = connection.prepareStatement(sqlDeleteTicket);
			statementDeleteTicket.setInt(1, ticketId);
			int countDeletions= statementDeleteTicket.executeUpdate();
			
			String sqlAddNewAccount = "Insert Into bankaccounts(holder, balance) Values(?,?)";
			PreparedStatement statementAddNewAccount = connection.prepareStatement(sqlAddNewAccount);
			statementAddNewAccount.setString(1, userName);
			statementAddNewAccount.setDouble(2, balance);
			int countAdditions = statementAddNewAccount.executeUpdate();
			if(countAdditions == 1) {
				accountCreated = true;
			}
			
		} catch (ClassNotFoundException e) {
			throw new LibraryException();
		} catch (SQLException e) {
			throw new DatabaseException();
		}
		return accountCreated;
	}
}
