package com.banking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.banking.util.OracleDBConnection;

public class MoneyTransferDAO {
	private static final Logger log = Logger.getLogger(MoneyTransferDAO.class);
	
	public boolean postMoneyTransfer(int sourceAccountNumber, int destinationAccountNumber, double amount) {
		boolean transferSuccess = false;
		try(Connection connection = OracleDBConnection.getInstance()){
			String sqlInsertNewMoneyTransfer = "Insert Into MoneyTransfer (source_account, destination_account, amount) Values (?, ?, ?)";
			PreparedStatement statementInsertNewMoneyTransfer = connection.prepareStatement(sqlInsertNewMoneyTransfer);
			statementInsertNewMoneyTransfer.setInt(1, sourceAccountNumber);
			statementInsertNewMoneyTransfer.setInt(2, destinationAccountNumber);
			statementInsertNewMoneyTransfer.setDouble(3, amount);
			int countUpdated = statementInsertNewMoneyTransfer.executeUpdate();
			if(countUpdated == 1) {
				transferSuccess = true;
			}
		}catch(ClassNotFoundException e) {
			log.error(e);
		}catch(SQLException e) {
			log.error(e);
		}
		return transferSuccess;
	}
	
}
