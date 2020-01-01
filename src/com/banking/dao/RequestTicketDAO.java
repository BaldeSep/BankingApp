package com.banking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.banking.bo.RequestTicket;
import com.banking.exception.DatabaseException;
import com.banking.exception.LibraryException;
import com.banking.util.OracleDBConnection;


public class RequestTicketDAO {
	private static final Logger log = Logger.getLogger(RequestTicketDAO.class);
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
			log.error(e);
			throw new LibraryException();
		} catch (SQLException e) {
			log.error(e);
			throw new DatabaseException();
		}
		return applications;
	}
}
