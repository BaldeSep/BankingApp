package com.banking.menus;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.banking.bo.BankAccount;
import com.banking.bo.BankingSystem;
import com.banking.bo.User;
import com.banking.exception.DatabaseException;
import com.banking.exception.LibraryException;
import com.banking.menu.options.ViewBalanceMenuOptions;
import com.banking.util.MenuHelper;

public class ViewBalanceMenu implements Menu {
	private static final ViewBalanceMenu viewBalanceMenu = new ViewBalanceMenu();
	private static final Logger log = Logger.getLogger(ViewBalanceMenu.class);
	private Menu prevMenu;
	
	private ViewBalanceMenu() {
		prevMenu = null;
	}
	
	public static ViewBalanceMenu getMenu() {
		return viewBalanceMenu;
	}
	
	@Override
	public void presentMenu() {
		BufferedReader reader = MenuHelper.getReader();
		ViewBalanceMenuOptions menuOptions[] = ViewBalanceMenuOptions.values();
		int userInput = -1;
		do {
			try {
				MenuHelper.printMenuOptions(menuOptions);
				userInput = Integer.parseInt(reader.readLine().trim());
				if(userInput >= 0 && userInput <= menuOptions.length - 1) {
					break;
				}else {
					log.error("Invalid Input Must Be Between 0" + "-" + (menuOptions.length - 1));
				}
			}catch(IOException e) {
				log.fatal("Sorry An Error Occured  When Getting Your Input. Contact Support.");
				QuitMenu.getMenu().presentMenu();
			}catch(NumberFormatException e) {
				log.error("Invalid Input. Only Enter Whole Numbers");
			}
		}while(true);
		parseUserInput(userInput);
	}
	
	public void presentMenu(Menu prevMenu) {
		setPreviousMenu(prevMenu);
		presentMenu();
	}
	
	private void parseUserInput(int userInput) {
		ViewBalanceMenuOptions option = ViewBalanceMenuOptions.fromInt(userInput);
		switch(option) {
		case Go_Back:
			prevMenu.presentMenu();
			break;
		case View_Balance_Of_All_Accounts:
			viewBalanceOfAllAccounts();
			break;
		case View_Balance_Of_Single_Account:
			viewBalanceOfSingleAccount();
			break;
		}
	}
	
	private void viewBalanceOfAllAccounts() {
		User currentUser = BankingSystem.getInstance().getActiveUser();
		List<BankAccount> accounts = getAllAccounts(currentUser);
		for(BankAccount account: accounts) {
			log.info(account);
		}
		
		BufferedReader reader = MenuHelper.getReader();
		try {
			log.info("Press Enter To Return To The Previous Menu");
			reader.readLine();
		} catch (IOException e) {
			log.fatal("Sorry An Error Occured  When Getting Your Input. Contact Support.");
			QuitMenu.getMenu().presentMenu();
		}
		
		prevMenu.presentMenu();
	}
	
	private void viewBalanceOfSingleAccount() {
		BufferedReader reader = MenuHelper.getReader();
		BankingSystem system = BankingSystem.getInstance();
		User currentUser = system.getActiveUser();
		List<BankAccount> accounts = getAllAccounts(currentUser);
		if(accounts.size() > 0) {
			for(BankAccount account: accounts) {
				log.info("Account Number: " + account.getAccountNumber());
			}
			int userInput = -1;
			try {
				log.info("Please Enter One Of The Account Numbers Above To Get The Balance...[Enter Nothing And Press Enter To Quit]");
				userInput = Integer.parseInt(reader.readLine().trim());
			}catch(IOException e) {
				log.fatal("Sorry An Error Occured  When Getting Your Input. Contact Support.");
				QuitMenu.getMenu().presentMenu();
			}catch(NumberFormatException e) {
				log.error("Invalid Input Must Be A Whole Number");
			}
			boolean accountFound = false;
			for(BankAccount account: accounts) {
				if(userInput == account.getAccountNumber()) {
					accountFound = true;
					log.info(account);
				}
			}
			
			if(!accountFound) {
				log.error("Sorry We Could Not Find That Account...");
			}
		}
		try {
			log.info("Please Press Enter To Go Back To The Previous Menu...");
			reader.readLine();
		}catch(IOException e) {
			log.fatal("Sorry An Error Occured  When Getting Your Input. Contact Support.");
			QuitMenu.getMenu().presentMenu();
		}
		prevMenu.presentMenu();
	}
	
	private List<BankAccount> getAllAccounts(User user){
		List<BankAccount> accounts = new ArrayList<>();
		BankingSystem system = BankingSystem.getInstance();
		try {
			accounts = system.getAccounts(user.getUserName());
		} catch (LibraryException | DatabaseException e) {
			log.error(e.getMessage());
		}
		
		return accounts;
	}
	
	private void setPreviousMenu(Menu prevMenu) {
		this.prevMenu = prevMenu;
	}

}
