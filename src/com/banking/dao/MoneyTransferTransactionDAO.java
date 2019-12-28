package com.banking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.banking.util.OracleDBConnection;

public class MoneyTransferTransactionDAO {
	private static final Logger log = Logger.getLogger(MoneyTransferDAO.class); 
	public boolean logTransaction(int sourceAccount, int destinationAccount, int transferId, double amount) {
		boolean logSuccessful = false;
		try(Connection connection = OracleDBConnection.getConnection()){
			String sqlInsertNewLog = "Insert Into MoneyTransferTransactionLog (source_account, destination_account, transfer_id, amount) Values(?, ?, ?, ?)";
			PreparedStatement statementInsertNewLog = connection.prepareStatement(sqlInsertNewLog);
			statementInsertNewLog.setInt(1, sourceAccount);
			statementInsertNewLog.setInt(2, destinationAccount);
			statementInsertNewLog.setInt(3, transferId);
			statementInsertNewLog.setDouble(4, amount);
			int countUpdates = statementInsertNewLog.executeUpdate();
			if(countUpdates == 1) {
				logSuccessful = true;
			}
		}catch(ClassNotFoundException e) {
			log.error(e);
		}catch(SQLException e) {
			log.error(e);
		}
		return logSuccessful;
	}
}
