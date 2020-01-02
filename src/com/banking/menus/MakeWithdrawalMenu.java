package com.banking.menus;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.banking.bo.BankAccount;
import com.banking.bo.BankingSystem;
import com.banking.bo.User;
import com.banking.exception.BankingSystemException;
import com.banking.exception.DatabaseException;
import com.banking.exception.LibraryException;
import com.banking.menu.options.MakeWithdrawalMenuOptions;
import com.banking.util.MenuHelper;

public class MakeWithdrawalMenu implements Menu {
	private static final MakeWithdrawalMenu makeWithdrawalMenu = new MakeWithdrawalMenu();
	private static final Logger log = Logger.getLogger(MakeWithdrawalMenu.class);	
	private Menu prevMenu;
	private MakeWithdrawalMenu() {
		prevMenu = null;
	}
	public static MakeWithdrawalMenu getMenu() {
		return makeWithdrawalMenu;
	}
	
	@Override
	public void presentMenu() {
		BufferedReader reader = MenuHelper.getReader(); 
		MakeWithdrawalMenuOptions menuOptions[] = MakeWithdrawalMenuOptions.values();
		int userInput = -1;
		do {
			try {
				MenuHelper.printMenuOptions(menuOptions);
				userInput = Integer.parseInt(reader.readLine().trim());
				if(userInput >= 0 && userInput <= menuOptions.length - 1) {
					break;
				}else {
					log.error("Invalid Input. Please Enter A Number Between 0 -" + (menuOptions.length-1));
				}
			}catch(IOException e) {
				log.fatal("Sorry An Error Occured  When Getting Your Input. Contact Support.");
				QuitMenu.getMenu().presentMenu();
			}catch(NumberFormatException e) {
				log.error("Invalid Input. Enter Only Whole Numbers.");
			}
		}while(true);
		parseUserInput(userInput);
	}
	public void presentMenu(Menu prevMenu) {
		setPreviousMenu(prevMenu);
		presentMenu();
	}
	
	private void parseUserInput(int userInput) {
		MakeWithdrawalMenuOptions option = MakeWithdrawalMenuOptions.fromInt(userInput);
		switch(option) {
		case Go_Back:
			prevMenu.presentMenu();
			break;
		case Enter_Amount_To_Withdrawal:
			makeWithdrawal();
			break;
		}
	}
	private void makeWithdrawal() {
		BufferedReader reader = MenuHelper.getReader();
		BankingSystem system = BankingSystem.getInstance();
		User activeUser = system.getActiveUser();
		List<BankAccount> accounts = getAllAccounts(activeUser);		
		int accountNumber = -1;
		double moneyWithdrew = 0.00;
		
		BankAccount accountToWithdrawFrom = null;
		
		boolean accountFound = false;
		boolean withdrawalMade = false;
		
		String tempUserInput = "";
		
		if(accounts.size() > 0) {
			do {
				try {
					// Get Account Number From User
					log.info("Enter The Account Number From The Account You Would Like To Withdrawal From[Enter Nothing And Press Enter To Quit]");
					// Print All Accounts For User
					for(BankAccount account: accounts) {
						log.info(account);
					}
					tempUserInput = reader.readLine();
					if(tempUserInput.isEmpty()) {
						break;
					}
					
					// Get The Account Number From User Input
					accountNumber = Integer.parseInt(tempUserInput.trim());
					// Search For The Account Within The List
					for(BankAccount account: accounts) {
						// If it finds the account break from the for loop
						if(account.getAccountNumber() == accountNumber) {
							accountToWithdrawFrom = account;
							accountFound = true;
							break;
						}
					}
					
					// If the account is not found breaks from outer while loop
					if(!accountFound) {
						log.error("Sorry We Could Not Find That Account.");
						break;
					}
					
					// Get The Amount To Withdrawal From The User
					double amountToWithdrawal = getWithdrawalAmount();
					// Make Withdrawal on the database
					moneyWithdrew = system.makeWithdrawal(accountNumber, amountToWithdrawal);
					// if an exception does not execute this will assume the withdrawal was made.
					withdrawalMade = true;
					// If the withdrawal was a success break out of outer while loop
					if(withdrawalMade = true) {
						break;
					}
					
				}catch(IOException e) {
					log.fatal("Sorry An Error Occured  When Getting Your Input. Contact Support.");
					QuitMenu.getMenu().presentMenu();
				}catch(NumberFormatException e) {
					log.error("Sorry We Could Not Find That Account Number: " + accountNumber);
				}catch(BankingSystemException | DatabaseException | LibraryException e) {
					log.error(e.getMessage());
				}
			}while(true);
		}
		if(!withdrawalMade) {
			log.error("Sorry We Could Not Complete Your Request Right Now");
		}else {
			accountToWithdrawFrom = getAccount(accountToWithdrawFrom.getAccountNumber());
			log.info("Withdrawal Amount: $" + moneyWithdrew);
			log.info("Account Balance For Account Number " + accountToWithdrawFrom.getAccountNumber() + ": $" + accountToWithdrawFrom.getBalance());
		}
		
		try {
			log.info("Please Press Enter To Go Back To Main Menu...");
			reader.readLine();
		}catch(IOException e) {
			log.fatal("Sorry An Error Occured  When Getting Your Input. Contact Support.");
			QuitMenu.getMenu().presentMenu();
		}
		
		prevMenu.presentMenu();
	}
	
	
	private BankAccount getAccount(int accountNumber) {
		BankingSystem system = BankingSystem.getInstance();
		BankAccount account = null;
		try {
			account = system.getAccount(accountNumber);
		} catch (LibraryException | DatabaseException e) {
			log.error(e.getMessage());
		}
		return account;
	}
	
	private double getWithdrawalAmount() {
		double amountToWithdrawl = 0.00;
		
		BufferedReader reader = MenuHelper.getReader();
		do {
			try {
				log.info("Enter The Amount To Withdrawl Below...");
				amountToWithdrawl = Double.parseDouble(reader.readLine().trim());
				if(amountToWithdrawl >= 0.00) {
					break;
				}
			}catch(IOException e) {
				log.fatal("Sorry An Error Occured  When Getting Your Input. Contact Support.");
				QuitMenu.getMenu().presentMenu();
			}catch(NumberFormatException e) {
				log.error("Invalid Input. Enter Only Valid Decimal Amounts.(Eg. 10.25)");
			}
		}while(true);
		
		return amountToWithdrawl;
	}
	
	private List<BankAccount> getAllAccounts(User user){
		BankingSystem system = BankingSystem.getInstance();
		List<BankAccount> accounts = new ArrayList<>();
		try {
			accounts = system.getAccounts(user.getUserName());
		} catch (LibraryException | DatabaseException e) {
			log.error(e.getMessage());
		}
		return accounts;
	}
	
	
	public void setPreviousMenu(Menu prevMenu) {
		this.prevMenu = prevMenu;
	}

}
