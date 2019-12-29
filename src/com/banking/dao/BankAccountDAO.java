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
import com.banking.exception.DatabaseException;
import com.banking.exception.LibraryException;
import com.banking.util.OracleDBConnection;

public class BankAccountDAO {
	private static final Logger log = Logger.getLogger(BankAccountDAO.class);
	
	// Create A Bank Account With An Initial Balance Of $0.00
	public boolean createBankAccount(final String userName) throws LibraryException, DatabaseException {
		boolean createdAccount = false;
		
		try(Connection connection = OracleDBConnection.getConnection()){
			// Create SQL Statement
			String insertBankAccount = "INSERT INTO BANKACCOUNTS (holder) VALUES (?)";
			PreparedStatement insertBankAccountStatement = connection.prepareStatement(insertBankAccount);
			insertBankAccountStatement.setString(1, userName);
			// Execute Statement
			int count = insertBankAccountStatement.executeUpdate();
			if(count == 1) {
				createdAccount = true;
			}
		}catch(ClassNotFoundException e) {
			log.error(e);
			throw new LibraryException();
		}catch(SQLException e) {
			log.error(e);
			throw new DatabaseException("Sorry There Was An Error When Creating Your Account, Try Again Later and Please Report To Customer Support");
		}
		
		return createdAccount;
	}
	
	// Create A Bank Account With A Starting Balance
	public boolean createBankAccount(final String userName, final double initialBalance) throws LibraryException, DatabaseException {
		boolean createdAccount = false;
		
		try(Connection connection = OracleDBConnection.getConnection()){
			// Create SQL Statement
			String insertBankAccount = "INSERT INTO BANKACCOUNTS (holder, balance) VALUES (?, ?)";
			PreparedStatement insertBankAccountStatement = connection.prepareStatement(insertBankAccount);
			insertBankAccountStatement.setString(1, userName);
			insertBankAccountStatement.setDouble(2, initialBalance);
			// Execute Statement
			int count = insertBankAccountStatement.executeUpdate();
			if(count == 1) {
				createdAccount = true;
			}
		}catch(ClassNotFoundException e) {
			log.error(e);
			throw new LibraryException();
		}catch(SQLException e) {
			log.error(e);
			throw new DatabaseException("Sorry There Was An Issue Creating Your Bank Account, Contact Support.");
		}
		
		return createdAccount;
	}
	
	// Give this method a Request Ticket for opening an account
	public boolean applyForBankAccount(RequestTicket ticket) throws LibraryException, DatabaseException {
		boolean applicationSent = false;
		
		try(Connection connection = OracleDBConnection.getConnection()){
			String userName = ticket.getUserName();
			double defaultBalance =  ticket.getInitialBalance();
			String sql = "Insert Into RequestTicket(user_name, default_balance) Values(?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, userName);			
			statement.setDouble(2, defaultBalance);				
			int count = statement.executeUpdate();
			if(count == 1) {
				applicationSent = true;
			}
		}catch(ClassNotFoundException e) {
			log.error(e);
			throw new LibraryException();
		}catch(SQLException e) {
			log.error(e);
			throw new DatabaseException();
		}
		
		return applicationSent;
	}

	// Get All Accounts For User given their user name
	public List<BankAccount> getAccounts(String userName) throws LibraryException, DatabaseException {
		List<BankAccount> accounts = new ArrayList<>();
		try(Connection connection = OracleDBConnection.getConnection()){
			String sql = "Select account_number, holder, balance From BankAccounts Where holder = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, userName);
			ResultSet resultAccounts = statement.executeQuery();
			if(!resultAccounts.next()) {
				throw new DatabaseException("Sorry We Couldn't Find Any Accounts For User: " + userName + ". Try Applying For One Today!");
			}
			while(resultAccounts.next()) {
				BankAccount account = new BankAccount();
				account.setAccountNumber(resultAccounts.getInt("account_number"));
				account.setHolder(resultAccounts.getString("holder"));
				account.setBalance(resultAccounts.getDouble("balance"));
				accounts.add(account);
			}
		}catch(ClassNotFoundException e) {
			log.error(e);
			throw new LibraryException();
		}catch(SQLException e) {
			log.error(e);
			throw new DatabaseException("Sorry There Was An Issue Finding Your Accounts Contact Support.");
		}
		return accounts;
	}
	
	// Given an account number this returns a BankAccount object representing that account
	public BankAccount getAccount(int accountNumber) throws LibraryException, DatabaseException {
		BankAccount account = null;
		try(Connection connection = OracleDBConnection.getConnection()){
			String sql = "Select account_number, holder, balance From BankAccounts Where account_number = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, accountNumber);
			ResultSet resultAccounts =  statement.executeQuery();
			
			// If there are no results throw an exception
			if(resultAccounts.next()) {
				account = new BankAccount(resultAccounts.getInt("account_number"), resultAccounts.getString("holder"), resultAccounts.getDouble("balance"));
			}
			else {
				throw new DatabaseException("Sorry We Couldn't Find That Account Number. Try Another One");
			}
		}catch(ClassNotFoundException e) {
			log.error(e);
			throw new LibraryException();
		}catch(SQLException e) {
			log.error(e);
			throw new DatabaseException();
		}
		return account;
	}

	// Given a BankAccount number and an amount to deposit this will deposit money into the account
	// within the database
	public boolean makeDeposit(int accountNumber, double amount) throws LibraryException, DatabaseException{
		boolean successfulDeposit = false;
		try(Connection connection = OracleDBConnection.getConnection()){
			String selectAccount = "Select balance From BankAccounts Where account_number = ?";
			PreparedStatement getBalanceStatement = connection.prepareStatement(selectAccount);
			getBalanceStatement.setInt(1, accountNumber);
			ResultSet accountBalanceSet = getBalanceStatement.executeQuery();
			double oldBalance = 0.00;
			if(accountBalanceSet.next()) {
				oldBalance = accountBalanceSet.getDouble("balance");
			}else {
				throw new DatabaseException("Sorry We Could Not Find The Account: " + accountNumber + ". Try another account.");
			}
			double newBalance = oldBalance + amount;
			String addNewBalance = "Update BankAccounts Set balance = ? Where account_number = ? ";
			PreparedStatement updateBalanceStatement = connection.prepareStatement(addNewBalance);
			updateBalanceStatement.setDouble(1, newBalance);
			updateBalanceStatement.setInt(2, accountNumber);
			int updatedRows = updateBalanceStatement.executeUpdate();
			if(updatedRows == 1) {
				successfulDeposit = true;
			}else {
				throw new DatabaseException("Sorry There Was An Error Making This Deposit. Try Again With A Differend Account");
			}
		}catch(ClassNotFoundException e) {
			log.error(e);
			throw new LibraryException();
		}catch(SQLException e) {
			log.error(e);
			throw new DatabaseException();
		}
		return successfulDeposit;
	}
	
	// Given an account number and an amount to withdrawal this removes money from account
	public double makeWithdrawal(int accountNumber, double amountToWithdrawal) throws DatabaseException, BankingSystemException, LibraryException  {
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
				throw new DatabaseException("Bank Account Could Not Be Found With Given Account Number: " + accountNumber );
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
				throw new DatabaseException("Error Removing Funds With Account");
			}
		}catch(ClassNotFoundException e) {
			log.error(e);
			throw new LibraryException();
		}catch(SQLException e) {
			log.error(e);
			throw new DatabaseException();
		}
		return moneyWithdrawn;
	}
	
	
}
