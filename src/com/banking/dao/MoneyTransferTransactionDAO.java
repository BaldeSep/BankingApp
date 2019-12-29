package com.banking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.banking.exception.DatabaseException;
import com.banking.exception.LibraryException;
import com.banking.util.OracleDBConnection;

public class MoneyTransferTransactionDAO {
	private static final Logger log = Logger.getLogger(MoneyTransferDAO.class); 
	public boolean logTransaction(int sourceAccount, int destinationAccount, int transferId, double amount) throws LibraryException, DatabaseException {
		boolean logSuccessful = false;
		try(Connection connection = OracleDBConnection.getConnection()){
			String sqlInsertNewLog = "Insert Into MoneyTransferTransactionLog (source_account, destination_account, transfer_id, amount, date_of_transaction) Values(?, ?, ?, ?, ?)";
			PreparedStatement statementInsertNewLog = connection.prepareStatement(sqlInsertNewLog);
			statementInsertNewLog.setInt(1, sourceAccount);
			statementInsertNewLog.setInt(2, destinationAccount);
			statementInsertNewLog.setInt(3, transferId);
			statementInsertNewLog.setDouble(4, amount);
			statementInsertNewLog.setDate(5, new java.sql.Date(new java.util.Date().getTime()));
			int countUpdates = statementInsertNewLog.executeUpdate();
			if(countUpdates == 1) {
				logSuccessful = true;
			}else {
				log.error(new DatabaseException("Improper Number of Updates Performed. Number of Updates: " + countUpdates));
			}
		}catch(ClassNotFoundException e) {
			log.error(e);
			throw new LibraryException();
		}catch(SQLException e) {
			log.error(e);
			throw new DatabaseException();
		}
		return logSuccessful;
	}
}
