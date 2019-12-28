package com.banking.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.banking.bo.types.TransactionType;
import com.banking.util.OracleDBConnection;

public class TransactionDAO {

	private static final Logger log = Logger.getLogger(TransactionDAO.class);
	
	public boolean logTransaction(TransactionType type, int sourceAccount, int destAccount, double amount) {
		boolean logSuccess = false;
		try(Connection connection = OracleDBConnection.getConnection()){
			String sqlInsertLog = "Insert Into TransactionLogs (transaction_type, source_account, destination_account, amount, date_of_transaction) Values (?, ?, ?, ?, ?)";
			PreparedStatement statementInsertLog = connection.prepareStatement(sqlInsertLog);
			statementInsertLog.setInt(1, type.ordinal());
			statementInsertLog.setInt(2, sourceAccount);
			statementInsertLog.setInt(3, destAccount);
			statementInsertLog.setDouble(4, amount);
			statementInsertLog.setDate(5, new java.sql.Date(new java.util.Date().getTime()));
			int countUpdate = statementInsertLog.executeUpdate();
			if(countUpdate == 1) {
				logSuccess = true;
			}
		}catch(ClassNotFoundException e) {
			log.error(e);
		}catch(SQLException e) {
			log.error(e);
		}
		return logSuccess;
	}
}
