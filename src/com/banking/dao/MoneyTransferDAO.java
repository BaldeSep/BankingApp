package com.banking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.banking.bo.MoneyTransfer;
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

	public List<MoneyTransfer> viewMoneyTransfers(String destinationUserName) {
		List<MoneyTransfer> moneyTransfers = new ArrayList<>();
		try(Connection connection = OracleDBConnection.getInstance()){
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
	
}
