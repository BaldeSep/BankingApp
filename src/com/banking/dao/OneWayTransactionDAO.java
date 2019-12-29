package com.banking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.banking.bo.types.TransactionType;
import com.banking.exception.DatabaseException;
import com.banking.exception.LibraryException;
import com.banking.util.OracleDBConnection;

public class OneWayTransactionDAO {
	private static final Logger log = Logger.getLogger(OneWayTransactionDAO.class);
	public boolean logTransaction(TransactionType type, int accountNumber, double amount) throws LibraryException, DatabaseException {
		boolean logSuccess = false;
		try(Connection connection = OracleDBConnection.getConnection()){
			String sqlInsertNewLog = "Insert Into OneWayTransactionLog (transaction_type, account_number, amount, date_of_transaction) Values(?, ?, ?, ?)";
			PreparedStatement statementInsertNewLog = connection.prepareStatement(sqlInsertNewLog);
			statementInsertNewLog.setInt(1, type.ordinal());
			statementInsertNewLog.setInt(2, accountNumber);
			statementInsertNewLog.setDouble(3, amount);
			statementInsertNewLog.setDate(4, new java.sql.Date(new java.util.Date().getTime()));
			int countUpdates = statementInsertNewLog.executeUpdate();
			if(countUpdates == 1) {
				logSuccess = true;
			}else {
				log.error(new DatabaseException("An Improper Number of Entries Were Updated. Number Updated: " + countUpdates));
			}
		}catch(ClassNotFoundException e) {
			log.error(e);
			throw new LibraryException();
		}catch(SQLException e) {
			log.error(e);
			throw new DatabaseException();
		}
		
		return logSuccess;
	}
}
