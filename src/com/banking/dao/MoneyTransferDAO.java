package com.banking.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.banking.bo.BankingSystem;
import com.banking.bo.MoneyTransfer;
import com.banking.exception.BankingSystemException;
import com.banking.util.OracleDBConnection;

public class MoneyTransferDAO {
	private static final Logger log = Logger.getLogger(MoneyTransferDAO.class);
	
	public int postMoneyTransfer(int sourceAccountNumber, int destinationAccountNumber, double amount) {
		int moneyTransferId = -1;
		try(Connection connection = OracleDBConnection.getConnection()){
			String sqlInsertNewMoneyTransfer = "Begin Insert Into MoneyTransfer (source_account, destination_account, amount) Values (?, ?, ?) returning Transfer_Id into ?; end;";
			CallableStatement statementInsertNewMoneyTransfer = connection.prepareCall(sqlInsertNewMoneyTransfer);
			statementInsertNewMoneyTransfer.setInt(1, sourceAccountNumber);
			statementInsertNewMoneyTransfer.setInt(2, destinationAccountNumber);
			statementInsertNewMoneyTransfer.setDouble(3, amount);
			statementInsertNewMoneyTransfer.registerOutParameter(4, java.sql.Types.INTEGER);
			int countUpdated = statementInsertNewMoneyTransfer.executeUpdate();
			if(countUpdated == 1) {
				moneyTransferId = statementInsertNewMoneyTransfer.getInt(4);
			}
		}catch(ClassNotFoundException e) {
			log.error(e);
		}catch(SQLException e) {
			log.error(e);
		}
		return moneyTransferId;
	}

	public List<MoneyTransfer> viewMoneyTransfers(String destinationUserName) {
		List<MoneyTransfer> moneyTransfers = new ArrayList<>();
		try(Connection connection = OracleDBConnection.getConnection()){
			String sqlGetAccountsOfUsers = "Select account_number From BankAccounts Where holder = ?";
			PreparedStatement statementGetAccountsOfUsers = connection.prepareStatement(sqlGetAccountsOfUsers);
			statementGetAccountsOfUsers.setString(1, destinationUserName);
			ResultSet accountNumbersResults = statementGetAccountsOfUsers.executeQuery();
			List<Integer> accountNumbers = new ArrayList<>();
			while(accountNumbersResults.next()) {
				accountNumbers.add(accountNumbersResults.getInt("account_number"));
			}
			String sqlGetAllTransfersForAccount = "Select transfer_id, source_account, destination_account, amount From MoneyTransfer Where destination_account = ?";
			PreparedStatement statementGetAllTransfersForAccount = connection.prepareStatement(sqlGetAllTransfersForAccount);
			ResultSet moneyTransferResults;
			MoneyTransfer temp;
			for(int accountNumber: accountNumbers) {
				statementGetAllTransfersForAccount.setInt(1, accountNumber);
				moneyTransferResults = statementGetAllTransfersForAccount.executeQuery();
				while(moneyTransferResults.next()) {
					temp = new MoneyTransfer();
					temp.setSourceAccountNumber(moneyTransferResults.getInt("source_account"));
					temp.setDestinationAccountNumber(moneyTransferResults.getInt("destination_account"));
					temp.setAmount(moneyTransferResults.getDouble("amount"));
					moneyTransfers.add(temp);
				}
			}
		}catch(ClassNotFoundException e) {
			log.error(e);
		}catch(SQLException e) {
			log.error(e);
		}
		return moneyTransfers;
	}

	public MoneyTransfer acceptMoneyTransfer(int transferId) throws BankingSystemException{
		MoneyTransfer transfer = null;
		BankingSystem system = BankingSystem.getInstance();
		try(Connection connection = OracleDBConnection.getConnection()){
			String sqlGetTransfer = "Select source_account, destination_account, amount From MoneyTransfer Where transfer_id = ?";
			PreparedStatement statementGetTransfer = connection.prepareStatement(sqlGetTransfer);
			statementGetTransfer.setInt(1, transferId);
			ResultSet setTransfer = statementGetTransfer.executeQuery();
			int sourceAccountNumber = -1;
			int destinationAccountNumber = -1;
			double amount = -0.00;
			if(setTransfer.next()) {
				sourceAccountNumber = setTransfer.getInt("source_account");
				destinationAccountNumber = setTransfer.getInt("destination_account");
				amount = setTransfer.getDouble("amount");
				transfer = new MoneyTransfer(sourceAccountNumber, destinationAccountNumber, amount);
				String sqlDeleteTransfer = "Delete From MoneyTransfer Where transfer_id = ?";
				PreparedStatement statementDeleteTransfer = connection.prepareStatement(sqlDeleteTransfer);
				statementDeleteTransfer.setInt(1, transferId);
				statementDeleteTransfer.executeUpdate();
			}
			
			
		}catch(ClassNotFoundException e) {
			log.error(e);
		}catch(SQLException e) {
			log.error(e);
		}
		return transfer;
	}
	
}
