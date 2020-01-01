package com.banking.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.banking.bo.MoneyTransfer;
import com.banking.bo.types.MoneyTransferState;
import com.banking.exception.BankingSystemException;
import com.banking.exception.DatabaseException;
import com.banking.exception.LibraryException;
import com.banking.util.OracleDBConnection;

public class MoneyTransferDAO {
	private static final Logger log = Logger.getLogger(MoneyTransferDAO.class);
	
	public int postMoneyTransfer(int sourceAccountNumber, int destinationAccountNumber, double amount) throws DatabaseException, LibraryException {
		int moneyTransferId = -1;
		try(Connection connection = OracleDBConnection.getConnection()){
			String sqlInsertNewMoneyTransfer = "{call POSTMONEYTRANSFERRETURNID(?,?,?,?,?)}";
			CallableStatement statementInsertNewMoneyTransfer = connection.prepareCall(sqlInsertNewMoneyTransfer);
			statementInsertNewMoneyTransfer.setInt(1, sourceAccountNumber);
			statementInsertNewMoneyTransfer.setInt(2, destinationAccountNumber);
			statementInsertNewMoneyTransfer.setDouble(3, amount);
			statementInsertNewMoneyTransfer.setInt(4, MoneyTransferState.Denied.ordinal());
			statementInsertNewMoneyTransfer.registerOutParameter(5, java.sql.Types.INTEGER);
			int countUpdated = statementInsertNewMoneyTransfer.executeUpdate();
			if(countUpdated == 1) {
				moneyTransferId = statementInsertNewMoneyTransfer.getInt(5);
			}else {
				throw new DatabaseException("Sorry There Was An Error Posting Your Money Transfer Try Contacting Support Or Trying A Different Set Of Accounts");
			}
		}catch(ClassNotFoundException e) {
			log.error(e);
			throw new LibraryException();
		}catch(SQLException e) {
			log.error(e);
			throw new DatabaseException();
		}
		return moneyTransferId;
	}

	public List<MoneyTransfer> viewMoneyTransfers(String userName) throws DatabaseException, LibraryException {
		List<MoneyTransfer> moneyTransfers = new ArrayList<>();
		try(Connection connection = OracleDBConnection.getConnection()){
			String sqlGetAccountsOfUsers = "Select account_number From BankAccounts Where holder = ?";
			PreparedStatement statementGetAccountsOfUsers = connection.prepareStatement(sqlGetAccountsOfUsers);
			statementGetAccountsOfUsers.setString(1, userName);
			ResultSet accountNumbersResults = statementGetAccountsOfUsers.executeQuery();
			List<Integer> accountNumbers = new ArrayList<>();
			
			boolean isEmpty = true;
			while(accountNumbersResults.next()) {
				isEmpty = false;
				accountNumbers.add(accountNumbersResults.getInt("account_number"));
			}
			if(isEmpty) {
				throw new DatabaseException("Sorry We Could Not Find Account For Holder: " + userName);
			}
			String sqlGetAllTransfersForAccount = "Select transfer_id, source_account, destination_account, amount, state From MoneyTransfer Where source_account = ? Or destination_account = ?";
			PreparedStatement statementGetAllTransfersForAccount = connection.prepareStatement(sqlGetAllTransfersForAccount);
			ResultSet moneyTransferResults;
			MoneyTransfer temp;
			for(int accountNumber: accountNumbers) {
				statementGetAllTransfersForAccount.setInt(1, accountNumber);
				statementGetAllTransfersForAccount.setInt(2, accountNumber);
				moneyTransferResults = statementGetAllTransfersForAccount.executeQuery();
				
				while(moneyTransferResults.next()) {
					temp = new MoneyTransfer();
					temp.setSourceAccountNumber(moneyTransferResults.getInt("source_account"));
					temp.setDestinationAccountNumber(moneyTransferResults.getInt("destination_account"));
					temp.setAmount(moneyTransferResults.getDouble("amount"));
					temp.setId(moneyTransferResults.getInt("transfer_id"));
					temp.setState(MoneyTransferState.fromInt(moneyTransferResults.getInt("state")));
					moneyTransfers.add(temp);
				}
			}
		}catch(ClassNotFoundException e) {
			log.error(e);
			throw new LibraryException();
		}catch(SQLException e) {
			log.error(e);
			throw new DatabaseException();
		}
		return moneyTransfers;
	}
	
	// Given a transfer id this will do the transfer from one account to another
	public MoneyTransfer acceptMoneyTransfer(int transferId) throws BankingSystemException, DatabaseException, LibraryException{
		MoneyTransfer transfer = null;
		try(Connection connection = OracleDBConnection.getConnection()){
			String sqlGetTransfer = "Select transfer_id, source_account, destination_account, amount, state From MoneyTransfer Where transfer_id = ?";
			PreparedStatement statementGetTransfer = connection.prepareStatement(sqlGetTransfer);
			statementGetTransfer.setInt(1, transferId);
			ResultSet setTransfer = statementGetTransfer.executeQuery();
			int sourceAccountNumber = -1;
			int destinationAccountNumber = -1;
			double amount = 0.00;
			int state; 
			int updatedCount = 0;
			int id = -1;
			if(setTransfer.next()) {
				sourceAccountNumber = setTransfer.getInt("source_account");
				destinationAccountNumber = setTransfer.getInt("destination_account");
				amount = setTransfer.getDouble("amount");
				state = setTransfer.getInt("state");
				id = setTransfer.getInt("transfer_id");
				if(state != MoneyTransferState.Accepted.ordinal()) {
					transfer = new MoneyTransfer(id, sourceAccountNumber, destinationAccountNumber, amount, MoneyTransferState.Denied);
					String sqlAcceptedTransfer = "Update MoneyTransfer Set state = ? Where transfer_id = ?";
					PreparedStatement statementDeleteTransfer = connection.prepareStatement(sqlAcceptedTransfer);
					statementDeleteTransfer.setInt(1, MoneyTransferState.Accepted.ordinal());
					statementDeleteTransfer.setInt(2, transferId);
					updatedCount = statementDeleteTransfer.executeUpdate();					
				}else {
					throw new BankingSystemException("Transfer Already Accepted. Cannot Accept Transfer More Than Once");
				}
			}else {
				throw new DatabaseException("Sorry We Could Not Find the Transfer Id: " + transferId + ". Try a differend ID");
			}
			if(updatedCount != 1) {
				throw new DatabaseException("There Was An Error Accepting Your Money Transfer Try Contacting Support.");
			}
			
		}catch(ClassNotFoundException e) {
			log.error(e);
			throw new LibraryException();
		}catch(SQLException e) {
			log.error(e);
			throw new DatabaseException();
		}
		return transfer;
	}
	
}
