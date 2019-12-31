package com.banking.menus;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.DataValidationException;

import com.banking.bo.BankAccount;
import com.banking.bo.BankingSystem;
import com.banking.bo.User;
import com.banking.exception.BankingSystemException;
import com.banking.exception.DatabaseException;
import com.banking.exception.LibraryException;
import com.banking.menu.options.MakeDepositMenuOptions;
import com.banking.util.MenuHelper;

public class MakeDepositMenu implements Menu{
	private static final MakeDepositMenu makeDepositMenu = new MakeDepositMenu();
	private static final Logger log = Logger.getLogger(MakeDepositMenu.class);
	private Menu prevMenu;
	
	private MakeDepositMenu() {
		prevMenu = null;
	}
	
	public static MakeDepositMenu getMenu() {
		return makeDepositMenu;
	}

	@Override
	public void presentMenu() {
		BufferedReader reader = MenuHelper.getReader();
		MakeDepositMenuOptions menuOptions[] = MakeDepositMenuOptions.values();
		int userInput = -1;
		do {
			try {
				MenuHelper.printMenuOptions(menuOptions);
				userInput = Integer.parseInt(reader.readLine().trim());
				if(userInput >= 0 && userInput <= menuOptions.length - 1) {
					break;
				}else {
					log.info("Invalid Input. Number Must Be Between 0-" + (menuOptions.length - 1) );
				}
				
			}catch(IOException e) {
				log.error(e);
				log.info("Sorry An Error Occured When Reading Your Input. Try Again Later");
			}catch(NumberFormatException e) {
				log.error(e);
				log.info("Invalid Input. Enter Only Whole Numbers");
			}
		}while(true);
		parseUserInput(userInput);
	}
	public void presentMenu(Menu prevMenu) {
		setPreviousMenu(prevMenu);
		presentMenu();
	}
	
	private void parseUserInput(int userInput) {
		MakeDepositMenuOptions option = MakeDepositMenuOptions.fromInt(userInput);
		switch(option) {
		case Go_Back:
			prevMenu.presentMenu();
			break;
		case Enter_Amount_To_Deposit:
			makeDeposit();
			break;
		}
	}
	
	private void makeDeposit() {
		BufferedReader reader = MenuHelper.getReader();
		BankingSystem system = BankingSystem.getInstance();
		User activeUser = system.getActiveUser();
		List<BankAccount> accounts = getAccounts(activeUser);
		
		BankAccount accountToDepositInto = null;
		
		boolean depositSuccess = false;
		boolean accountFound = false;
		
		
		if(accounts.size() > 0) {
			do {
				try {
					log.info("Please Enter The Account Number To Deposit Into...");
					for(BankAccount account: accounts ) {
						log.info(account);
					}
					int accountNumber = Integer.parseInt(reader.readLine().trim());
					for(BankAccount account: accounts) {
						if(accountNumber == account.getAccountNumber()) {
							accountFound = true;
							accountToDepositInto = account;
							break;
						}
					}
					if(!accountFound) {
						log.info("Sorry We Could Not Find Account Number: " + accountNumber + ". Try Again...");
						continue;
					}
					
					double amount = 0.00;
					log.info("Enter The Amount You Wish To Deposit...");
					amount = Double.parseDouble(reader.readLine().trim());
					depositSuccess = system.makeDeposit(accountNumber, amount);
					if(depositSuccess) {
						break;
					}else {
						log.info("Sorry We Could Not Deposit The Money Try Again Later...");
						break;
					}
					
				} catch (IOException e) {
					log.error(e);
					log.info("Sorry An Error Occured When Getting User Input");
				}catch(NumberFormatException e) {
					log.error(e);
					log.info("Invalid Input. Enter Only Whole Numbers");
				}catch(DatabaseException | LibraryException | BankingSystemException e) {
					log.error(e);
					log.info(e.getMessage());
				}
			}while(true);
			
		}
		
		try {
			if(depositSuccess && accountToDepositInto != null) {
				accountToDepositInto = system.getAccount(accountToDepositInto.getAccountNumber());
				log.info("Deposit Success For Account: " + accountToDepositInto.getAccountNumber());
				log.info("New Balance: " + accountToDepositInto.getBalance());
			}
			log.info("Please Press Enter To Go Back To The Main Menu");
			reader.readLine();
		}catch(IOException | LibraryException | DatabaseException e) {
			log.error(e);
			if(e instanceof LibraryException || e instanceof DatabaseException ) {
				log.error(e.getMessage());
			}
		}
		prevMenu.presentMenu();
		
	}
	
	private List<BankAccount> getAccounts(User activeUser){
		BankingSystem system = BankingSystem.getInstance();
		List<BankAccount> accounts = new ArrayList<>();
		try {
			accounts = system.getAccounts(activeUser.getUserName());
		} catch (LibraryException | DatabaseException e) {
			log.info(e.getMessage());
		}
		return accounts;
	}
	
	
	private void setPreviousMenu(Menu prevMenu) {
		this.prevMenu = prevMenu;
	}
	
	
}
