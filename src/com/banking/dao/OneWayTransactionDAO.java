package com.banking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.banking.bo.OneWayLog;
import com.banking.bo.OneWayTransaction;
import com.banking.bo.types.TransactionType;
import com.banking.exception.DatabaseException;
import com.banking.exception.LibraryException;
import com.banking.util.OracleDBConnection;

public class OneWayTransactionDAO {
	// private static final Logger log = Logger.getLogger(OneWayTransactionDAO.class);
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
				throw new DatabaseException("An Improper Number of Entries Were Updated. Number Updated: " + countUpdates);
			}
		}catch(ClassNotFoundException e) {
			throw new LibraryException();
		}catch(SQLException e) {
			throw new DatabaseException();
		}
		
		return logSuccess;
	}
	public List<OneWayLog> getLogs() throws DatabaseException, LibraryException {
		List<OneWayLog> logs = new ArrayList<>();
		try(Connection connection = OracleDBConnection.getConnection()) {
			String sqlGetLogs = 
					"Select o.log_id, o.account_number, o.amount, b.holder, o.date_of_transaction, o.transaction_type "
					+ "From onewaytransactionlog o "
					+ "Join bankaccounts b "
					+ "On o.account_number = b.account_number "
					+ "Order By o.date_of_transaction Desc";
			PreparedStatement statementGetLogs = connection.prepareStatement(sqlGetLogs);
			ResultSet setLogs = statementGetLogs.executeQuery();
			boolean emptyLogs = true;
			OneWayTransaction tempTransaction;
			OneWayLog tempLog;
			while(setLogs.next()) {
				emptyLogs = false;
				tempTransaction = new OneWayTransaction();
				tempTransaction.setAccountNumber(setLogs.getInt("account_number"));
				tempTransaction.setAmount(setLogs.getDouble("amount"));
				tempTransaction.setType(TransactionType.fromInt(setLogs.getInt("transaction_type")));
				tempLog = new OneWayLog();
				tempLog.setTransaction(tempTransaction);
				tempLog.setLogId(setLogs.getInt("log_id"));
				tempLog.setHolder(setLogs.getString("holder"));
				tempLog.setDateOfTransaction(setLogs.getDate("date_of_transaction"));
				logs.add(tempLog);
			}
			if(emptyLogs) {
				throw new DatabaseException("Could Not Find Any Logs.");
			}
		}catch(SQLException e) {
			throw new DatabaseException();
		} catch (ClassNotFoundException e) {
			throw new LibraryException();
		}
		return logs;
	}
	
	
}
