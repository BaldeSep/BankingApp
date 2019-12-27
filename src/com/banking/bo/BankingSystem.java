package com.banking.bo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.banking.bo.types.UserType;
import com.banking.dao.BankAccountDAO;
import com.banking.dao.UserDAO;
import com.banking.exception.BankingSystemException;

public class BankingSystem {
	private static final BankingSystem bankingSystem = new BankingSystem();
	private String currentUser;
	private UserDAO userDAO;
	private BankAccountDAO bankAccountDAO;
	private BankingSystem() {
		userDAO = new UserDAO();
		bankAccountDAO = new BankAccountDAO();
	}
	
	public static BankingSystem getInstance() {
		return bankingSystem;
	}
	
	public boolean registerUser(final String userName, final String password, final UserType type) {
		return userDAO.registerUser(userName, password, type);
	}
	
	public boolean verifyUserCredentials(final String userName, final String password) {
		if(userDAO.verifyUserCredentials(userName, password)) {
			currentUser = userName;
			return true;
		}
		return false;
	}

	public boolean createBankAccount(final String userName) {
		return bankAccountDAO.createBankAccount(userName);
	}
	
	public boolean createBankAccount(final String userName,final double initialBalance ) {
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

	public boolean makeDeposit(final int accountNumber, final double balance) throws BankingSystemException {
		if(balance < 0.00) {
			throw new BankingSystemException("Invalid Balance Amount, Must Be Greater Than Or Equal To $0.00 ");
		}
		return bankAccountDAO.makeDeposit(accountNumber, balance);
		
		
	}

	public double makeWithdrawal(final int accountNumber ,final double amountToWithdrawal) throws BankingSystemException {
		return bankAccountDAO.makeWithdrawal(accountNumber, amountToWithdrawal);
	}
}
