package com.banking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.banking.bo.BankAccount;
import com.banking.bo.BankingSystem;
import com.banking.bo.RequestTicket;
import com.banking.bo.types.TransactionType;
import com.banking.exception.BankingSystemException;
import com.banking.util.OracleDBConnection;

public class BankAccountDAO {
	private static final Logger log = Logger.getLogger(BankAccountDAO.class);
	
	public boolean createBankAccount(final String userName) {
		boolean createdAccount = false;
		
		try(Connection connection = OracleDBConnection.getConnection()){
			// Create SQL Statement
			String insertBankAccount = "INSERT INTO BANKACCOUNTS (holder) VALUES (?)";
			PreparedStatement insertBankAccountStatement = connection.prepareStatement(insertBankAccount);
			insertBankAccountStatement.setString(1, userName);
			// Execute Statement
			int count = insertBankAccountStatement.executeUpdate();
			if(count > 0) {
				createdAccount = true;
			}
		}catch(ClassNotFoundException e) {
			log.error(e);
		}catch(SQLException e) {
			log.error(e);
		}
		
		return createdAccount;
	}
	
	public boolean createBankAccount(final String userName, final double initialBalance) {
		boolean createdAccount = false;
		
		try(Connection connection = OracleDBConnection.getConnection()){
			// Create SQL Statement
			String insertBankAccount = "INSERT INTO BANKACCOUNTS (holder, balance) VALUES (?, ?)";
			PreparedStatement insertBankAccountStatement = connection.prepareStatement(insertBankAccount);
			insertBankAccountStatement.setString(1, userName);
			if(initialBalance >= 0.0) {
				insertBankAccountStatement.setDouble(2, initialBalance);
			}else {
				insertBankAccountStatement.setDouble(2, 0.00);
			}
			// Execute Statement
			int count = insertBankAccountStatement.executeUpdate();
			if(count > 0) {
				createdAccount = true;
			}
		}catch(ClassNotFoundException e) {
			log.error(e);
		}catch(SQLException e) {
			log.error(e);
		}
		
		return createdAccount;
	}
	
	public boolean applyForBankAccount(RequestTicket ticket) {
		boolean applicationSent = false;
		
		try(Connection connection = OracleDBConnection.getConnection()){
			String userName = ticket.getUserName();
			double defaultBalance =  ticket.getInitialBalance();
			String sql = "Insert Into RequestTicket(user_name, default_balance) Values(?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, userName);
			if(defaultBalance < 0) {
				statement.setDouble(2, 0.00);				
			}else {
				statement.setDouble(2, defaultBalance);				
			}
			int count = statement.executeUpdate();
			if(count > 0) {
				applicationSent = true;
			}
		}catch(ClassNotFoundException e) {
			log.error(e);
		}catch(SQLException e) {
			log.error(e);
		}
		
		return applicationSent;
	}

	public List<BankAccount> getAccounts(String userName) {
		List<BankAccount> accounts = new ArrayList<>();
		try(Connection connection = OracleDBConnection.getConnection()){
			String sql = "Select account_number, holder, balance From BankAccounts Where holder = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, userName);
			ResultSet resultAccounts = statement.executeQuery();
			while(resultAccounts.next()) {
				BankAccount account = new BankAccount();
				account.setAccountNumber(resultAccounts.getInt("account_number"));
				account.setHolder(resultAccounts.getString("holder"));
				account.setBalance(resultAccounts.getDouble("balance"));
				accounts.add(account);
			}
		}catch(ClassNotFoundException e) {
			log.error(e);
		}catch(SQLException e) {
			log.error(e);
		}
		return accounts;
	}

	public BankAccount getAccount(int accountNumber) {
		List<BankAccount> accounts = new ArrayList<>();
		BankAccount account = null;
		try(Connection connection = OracleDBConnection.getConnection()){
			String sql = "Select account_number, holder, balance From BankAccounts Where account_number = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, accountNumber);
			ResultSet resultAccounts =  statement.executeQuery();
			
			while(resultAccounts.next()) {
				BankAccount newAccount= new BankAccount();
				newAccount.setAccountNumber(resultAccounts.getInt("account_number"));
				newAccount.setBalance(resultAccounts.getDouble("balance"));
				newAccount.setHolder(resultAccounts.getString("holder"));
				accounts.add(newAccount);
			}
			if(accounts.size() == 1) {
				account = accounts.get(0);
			}
		}catch(ClassNotFoundException e) {
			log.error(e);
		}catch(SQLException e) {
			log.error(e);
		}
		return account;
	}

	public boolean makeDeposit(int accountNumber, double amount){
		boolean successfulDeposit = false;
		try(Connection connection = OracleDBConnection.getConnection()){
			String selectAccount = "Select balance From BankAccounts Where account_number = ?";
			PreparedStatement getBalanceStatement = connection.prepareStatement(selectAccount);
			getBalanceStatement.setInt(1, accountNumber);
			ResultSet accountBalanceSet = getBalanceStatement.executeQuery();
			double oldBalance = 0.00;
			if(accountBalanceSet.next()) {
				oldBalance = accountBalanceSet.getDouble("balance");
			}
			double newBalance = oldBalance + amount;
			String addNewBalance = "Update BankAccounts Set balance = ? Where account_number = ? ";
			PreparedStatement updateBalanceStatement = connection.prepareStatement(addNewBalance);
			updateBalanceStatement.setDouble(1, newBalance);
			updateBalanceStatement.setInt(2, accountNumber);
			int updatedRows = updateBalanceStatement.executeUpdate();
			if(updatedRows == 1) {
				successfulDeposit = true;
			}
		}catch(ClassNotFoundException e) {
			log.error(e);
		}catch(SQLException e) {
			log.error(e);
		}
		return successfulDeposit;
	}

	public double makeWithdrawal(int accountNumber, double amountToWithdrawal) throws BankingSystemException {
		double moneyWithdrawn = 0.00;
		try(Connection connection = OracleDBConnection.getConnection()){
			String queryAccount = "Select balance From BankAccounts Where account_number = ?";
			PreparedStatement getBalanceStatement = connection.prepareStatement(queryAccount);
			getBalanceStatement.setInt(1, accountNumber);
			ResultSet accountSet = getBalanceStatement.executeQuery();
			double oldBalance = 0.00;
			if(accountSet.next()) {
				oldBalance = accountSet.getDouble("balance");
			}else {
				throw new BankingSystemException("Bank Account Could Not Be Found With Given Account Number: " + accountNumber );
			}
			double newBalance = oldBalance - amountToWithdrawal;
			if(newBalance < 0) {
				throw new BankingSystemException("Amount Withdrawn Exceeds Available Funds");
			}
			String updateBalance = "Update BankAccounts Set balance = ? Where account_number = ?";
			PreparedStatement updateBalanceStatement = connection.prepareStatement(updateBalance);
			updateBalanceStatement.setDouble(1, newBalance);
			updateBalanceStatement.setInt(2, accountNumber);
			int updatedCount = updateBalanceStatement.executeUpdate();
			if(updatedCount == 1) {
				moneyWithdrawn = amountToWithdrawal;
			}else {
				throw new BankingSystemException("Error Removing Funds With Account");
			}
		}catch(ClassNotFoundException e) {
			log.error(e);
		}catch(SQLException e) {
			log.error(e);
		}
		return moneyWithdrawn;
	}
	
	
}
