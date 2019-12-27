package com.banking.bo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.banking.bo.types.UserType;
import com.banking.dao.BankAccountDAO;
import com.banking.dao.UserDAO;

public class BankingSystem {
	private static final BankingSystem bankingSystem = new BankingSystem();
	private UserDAO userDAO;
	private BankAccountDAO bankAccountDAO;
	public BankingSystem() {
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
		return userDAO.verifyUserCredentials(userName, password);
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
}
