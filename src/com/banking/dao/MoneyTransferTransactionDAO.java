package com.banking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.banking.bo.MoneyTransferLog;
import com.banking.bo.MoneyTransferTransaction;
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
	
	public List<MoneyTransferLog> getLogs() throws DatabaseException, LibraryException{
		List<MoneyTransferLog> logs = new ArrayList<>();
		try(Connection connection = OracleDBConnection.getConnection()) {
			String sqlGetLogs = 
					"Select m.id, m.source_account, b.holder as \"source_holder\", m.destination_account, ba.holder as \"destination_holder\", m.amount, m.transfer_id, m.date_of_transaction " + 
					"From moneytransfertransactionlog m " + 
					"Join bankaccounts b " + 
					"On m.source_account = b.account_number " + 
					"Join bankaccounts ba " + 
					"On m.destination_account = ba.account_number " +
					"Order By m.date_of_transaction Desc";
			PreparedStatement statementGetLogs = connection.prepareStatement(sqlGetLogs);
			ResultSet setLogs = statementGetLogs.executeQuery();
			
			boolean emptyLogs = true;
			MoneyTransferTransaction transactionTemp;
			MoneyTransferLog transactionLogTemp;
			while(setLogs.next()) {
				emptyLogs = false;
				transactionTemp = new MoneyTransferTransaction();
				transactionTemp.setAmount(setLogs.getDouble("amount"));
				transactionTemp.setSourceAccount(setLogs.getInt("source_account"));
				transactionTemp.setDestinationAccount(setLogs.getInt("destination_account"));
				transactionLogTemp = new MoneyTransferLog();
				transactionLogTemp.setTransfer(transactionTemp);
				transactionLogTemp.setSourceAccountUserName(setLogs.getString("source_holder"));
				transactionLogTemp.setDestinationAccountUserName(setLogs.getString("destination_holder"));
				transactionLogTemp.setDateOfTransaction(setLogs.getDate("date_of_transaction"));
				logs.add(transactionLogTemp);
			}
			
			if(emptyLogs) {
				throw new DatabaseException("No Logs Found");
			}
			
			
		} catch (ClassNotFoundException e) {
			log.error(e);
			throw new LibraryException();
		} catch (SQLException e) {
			log.error(e);
			throw new DatabaseException();
		}
		
		return logs;
	}
}
