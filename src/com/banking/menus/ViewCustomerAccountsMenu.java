package com.banking.menus;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.banking.bo.BankAccount;
import com.banking.bo.BankingSystem;
import com.banking.exception.DatabaseException;
import com.banking.exception.LibraryException;
import com.banking.menu.options.ViewBalanceMenuOptions;
import com.banking.menu.options.ViewCustomerAccountsMenuOptions;
import com.banking.util.MenuHelper;

public class ViewCustomerAccountsMenu implements Menu {
	private static final ViewCustomerAccountsMenu viewCustomerAccountMenu = new ViewCustomerAccountsMenu();
	private static final Logger log = Logger.getLogger(ViewCustomerAccountsMenu.class);
	private Menu prevMenu; 
	
	private ViewCustomerAccountsMenu() {
		prevMenu = null;
	}
	
	public static ViewCustomerAccountsMenu getMenu() {
		return viewCustomerAccountMenu;
	}
	
	@Override
	public void presentMenu() {
		BufferedReader reader = MenuHelper.getReader();
		ViewCustomerAccountsMenuOptions menuOptions[] = ViewCustomerAccountsMenuOptions.values();
		int userInput = -1;
		do {
			try {
				MenuHelper.printMenuOptions(menuOptions);
				userInput = Integer.parseInt(reader.readLine().trim());
				if(userInput >= 0 && userInput <= menuOptions.length - 1) {
					break;
				}else {
					log.error("Invalid Input. Enter A Number Between 0-" + (menuOptions.length - 1));
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
	
	
	private void parseUserInput(int userInput) {
		ViewCustomerAccountsMenuOptions option = ViewCustomerAccountsMenuOptions.fromInt(userInput);
		switch(option) {
		case Go_Back:
			prevMenu.presentMenu();
			break;
		case View_All_Customers_Accouts:
			viewAllCustomersAccounts();
			break;
		case View_One_Customer_Account:
			viewOneCustomerAccount();
			break;
		}
	}
	
	private void viewAllCustomersAccounts() {
		BankingSystem system = BankingSystem.getInstance();
		BufferedReader reader = MenuHelper.getReader();
		List<BankAccount> accounts = Collections.<BankAccount>emptyList();
		String userName = null;
		try {
			log.info("Enter The User Name For The Customer You Wish View Their Accounts...");
			userName = reader.readLine();
			accounts = system.getAccounts(userName);
			if(accounts.size() > 0) {
				for(BankAccount account: accounts) {
					log.info(account);
				}
			}else {
				log.error("Sorry The User " + userName + " Does Not Seem To Have Any Accounts With Us...");
			}
		} catch (IOException e) {
			log.fatal("Sorry An Error Occured  When Getting Your Input. Contact Support.");
			QuitMenu.getMenu().presentMenu();
		} catch ( DatabaseException | LibraryException e) {
			log.error(e.getMessage());
		}
		
		try {
			log.info("Please Press Enter To Go Back To The Main Menu");
			reader.readLine();
		}catch(IOException e) {
			log.fatal("Sorry An Error Occured  When Getting Your Input. Contact Support.");
			QuitMenu.getMenu().presentMenu();
		}
		
		prevMenu.presentMenu();
		
	}
	
	private void viewOneCustomerAccount() {
		BankingSystem system = BankingSystem.getInstance();
		BufferedReader reader = MenuHelper.getReader();
		List<BankAccount> accounts = Collections.<BankAccount>emptyList();
		String userName = null;
		int accountNumber = -1;
		do {
			try {
				log.info("Enter The User Name.[Enter Nothing And Press Enter To Quit]");
				userName = reader.readLine();
				if(userName.isEmpty()) {
					break;
				}
				
				accounts = system.getAccounts(userName);
				if(accounts.size() > 0) {
					for(BankAccount account : accounts) {
						log.info("Account Number: " + account.getAccountNumber() + " For User[" +  userName +"]");
					}
					log.info("Enter The Account Number You Wish To View.");
					accountNumber = Integer.parseInt(reader.readLine().trim());
					boolean accountFound = false;
					for(BankAccount account: accounts) {
						if(account.getAccountNumber() == accountNumber) {
							log.info(account);
							accountFound = true;
							break;
						}
					}
					if(!accountFound) {
						log.error("Sorry We Could Not Find Account: " + accountNumber);
					}else {
						break;
					}
				}else {
					log.error("Sorry We Could Not Find The Accounts For User: " + userName);
				}
			}catch(IOException e) {
				log.fatal("Sorry An Error Occured  When Getting Your Input. Contact Support.");
				QuitMenu.getMenu().presentMenu();
			}catch(DatabaseException | LibraryException e) {
				log.error(e.getMessage());
			}catch(NumberFormatException e) {
				log.error("Invalid Input. Must Be A Whole Number.");
			}
		}while(true);
		
		try {
			log.info("Please Press Enter To Return To The Main Menu");
			reader.readLine();
		}catch(IOException e) {
			log.fatal("Sorry An Error Occured  When Getting Your Input. Contact Support.");
			QuitMenu.getMenu().presentMenu();
		}
		
		prevMenu.presentMenu();
	}
	
	
	public void presentMenu(Menu preMenu) {
		setPreviousMenu(preMenu);
		presentMenu();
	}
	
	private void setPreviousMenu(Menu prevMenu) {
		this.prevMenu = prevMenu;
	}

}
