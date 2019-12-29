package com.banking.bo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.log4j.Logger;

import com.banking.bo.types.TransactionType;
import com.banking.bo.types.UserType;
import com.banking.dao.BankAccountDAO;
import com.banking.dao.MoneyTransferDAO;
import com.banking.dao.MoneyTransferTransactionDAO;
import com.banking.dao.OneWayTransactionDAO;
import com.banking.dao.UserDAO;
import com.banking.exception.BankingSystemException;
import com.banking.exception.DatabaseException;
import com.banking.exception.LibraryException;

public class BankingSystem {
	private static final BankingSystem bankingSystem = new BankingSystem();
	private String currentUser;
	private UserDAO userDAO;
	private BankAccountDAO bankAccountDAO;
	private MoneyTransferDAO moneyTransferDAO;
	private OneWayTransactionDAO oneWayTransactionDAO;
	private MoneyTransferTransactionDAO moneyTransferTransactionDAO;
	private BankingSystem() {
		currentUser = null;
		userDAO = new UserDAO();
		bankAccountDAO = new BankAccountDAO();
		moneyTransferDAO = new MoneyTransferDAO();
		oneWayTransactionDAO = new OneWayTransactionDAO();
		moneyTransferTransactionDAO = new MoneyTransferTransactionDAO();
	}
	
	public static BankingSystem getInstance() {
		return bankingSystem;
	}
	
	// Add user's user name and password into the database
	public boolean registerUser(final String userName, final String password, final UserType type) throws DatabaseException, LibraryException {
		return userDAO.registerUser(userName, password, type);
	}
	
	// Used to Log User Into The System
	public boolean verifyUserCredentials(final String userName, final String password) throws DatabaseException, LibraryException {
		// If the user entered Valid Credentials save their userName locally.
		// This can be used as a sort of session;
		if(userDAO.verifyUserCredentials(userName, password)) {
			currentUser = userName;
			return true;
		}
		return false;
	}
	
	// If the User decides to log out of the system this resets the session
	public void endUserSession() {
		if(currentUser != null) {
			currentUser = null;
		}
	}
	
	// Given a user's user name they can have an account created for them
	public boolean createBankAccount(final String userName) throws LibraryException, DatabaseException {
		return bankAccountDAO.createBankAccount(userName);
	}
	
	// Given a user's user name they can have an account created for them with an initial balance
	public boolean createBankAccount(final String userName,final double initialBalance ) throws BankingSystemException, LibraryException, DatabaseException {
		if(initialBalance < 0) {
			BankingSystemException invalidInitialBalance = new BankingSystemException("Initial Balance Must Be Greater Than Or Equal To $0.00");
			throw invalidInitialBalance;
		}
		return bankAccountDAO.createBankAccount(userName, initialBalance);
	}

	public RequestTicket generateRequestTicket(final String userName, final double balance) {
		return new RequestTicket(userName, balance);
	}

	public boolean applyForBankAccount(final RequestTicket ticket) {
		return bankAccountDAO.applyForBankAccount(ticket);
	}

	public List<BankAccount> getAccounts(final String userName) {
		return bankAccountDAO.getAccounts(userName);
	}

	public BankAccount getAccount(final int accountNumber) {
		return bankAccountDAO.getAccount(accountNumber);
	}

	public boolean makeDeposit(final int accountNumber, final double amount) throws BankingSystemException {
		if(amount <= 0) {
			throw new BankingSystemException("Invalid Deposit Amount, insert deposit greater than $0.00");
		}
		if(bankAccountDAO.makeDeposit(accountNumber, amount)) {
			logOneWayTransaction(TransactionType.Deposit, accountNumber, amount);
			return true;
		}
		
		return false;
	}

	public double makeWithdrawal(final int accountNumber ,final double amountToWithdrawal) throws BankingSystemException {
		double money = bankAccountDAO.makeWithdrawal(accountNumber, amountToWithdrawal);
		if(money > 0.00) {
			logOneWayTransaction(TransactionType.Withdrawal, accountNumber, amountToWithdrawal);
			return money;
		}
		return 0.00;
		
	}

	public boolean postMoneyTransfer(final int sourceAccountNumber, final int destinationAccountNumber, final double amount) throws BankingSystemException {
		if(amount < 0) {
			throw new BankingSystemException("Invalid Transfer Amount, Must Be Greater Than Or Equal To $0.00");
		}
		
		int moneyTransferId = moneyTransferDAO.postMoneyTransfer(sourceAccountNumber, destinationAccountNumber, amount);
		if(moneyTransferId > 0) {
			logMoneyTransfers(sourceAccountNumber, destinationAccountNumber, amount, moneyTransferId);
			return true;
		}
		
		return false;
	}

	public List<MoneyTransfer> viewMoneyTransfers(String destinationUserName) {
		return moneyTransferDAO.viewMoneyTransfers(destinationUserName);
	}

	public boolean acceptMoneyTransfer(final int transferId) throws BankingSystemException {
		MoneyTransfer transfer = moneyTransferDAO.acceptMoneyTransfer(transferId);
		double withdrawalAmount = makeWithdrawal(transfer.getSourceAccountNumber() , transfer.getAmount());
		boolean successfulDeposit = makeDeposit(transfer.getDestinationAccountNumber(), transfer.getAmount());
		if(withdrawalAmount >= 0 && successfulDeposit) {
			return true;
		}
		return false;
		
	}
	
	public boolean logOneWayTransaction(final TransactionType type,final int accountNumber, final double amount) {
		return oneWayTransactionDAO.logTransaction(type, accountNumber, amount);
	}
	
	public boolean logMoneyTransfers(final int sourceAccount,final int destinationAccount,final double amount, final int transferId ) {
		return moneyTransferTransactionDAO.logTransaction(sourceAccount, destinationAccount, transferId, amount);
	}

}
