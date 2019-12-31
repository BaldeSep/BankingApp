package com.banking.menus;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import com.banking.bo.BankAccount;
import com.banking.bo.BankingSystem;
import com.banking.bo.MoneyTransfer;
import com.banking.bo.User;
import com.banking.exception.BankingSystemException;
import com.banking.exception.DatabaseException;
import com.banking.exception.LibraryException;
import com.banking.menu.options.MoneyTransferMenuOptions;
import com.banking.util.MenuHelper;

public class MoneyTransferMenu implements Menu {
	private static final MoneyTransferMenu moneyTransferMenu = new MoneyTransferMenu();
	private static final Logger log = Logger.getLogger(MoneyTransferMenu.class);
	private Menu prevMenu;
	
	private MoneyTransferMenu() {
		prevMenu = null;
	}
	
	public static MoneyTransferMenu getMenu() {
		return moneyTransferMenu;
	}
	
	@Override
	public void presentMenu() {
		BufferedReader reader = MenuHelper.getReader();
		MoneyTransferMenuOptions menuOptions[] = MoneyTransferMenuOptions.values();
		int userInput;
		do {
			try {
				MenuHelper.printMenuOptions(menuOptions);
				userInput = Integer.parseInt(reader.readLine().trim());
				if(userInput >= 0 && userInput <= menuOptions.length - 1) {
					break;
				}else {
					log.info("Invalid Input. Enter Numbers Between 0-" +  (menuOptions.length - 1));
				}
			} catch (IOException e) {
				log.error(e);
				log.info("Sorry An Error Occured When Reading User Input");
			} catch(NumberFormatException e) {
				log.error(e);
				log.info("Invalid Input. Enter Whole Numbers Only");
			}
		}while(true);
		parseUserInput(userInput);
	}
	
	private void parseUserInput(int userInput) {
		MoneyTransferMenuOptions option = MoneyTransferMenuOptions.fromInt(userInput);
		switch(option) {
		case Go_Back:
			prevMenu.presentMenu();
		case Post_Transfer:
			postTransfer();
			break;
		case View_Transfers:
			viewTransfers();
			break;
		case Accept_Transfers:
			acceptTransfers();
			break;
		}
	}
	
	private List<BankAccount> getAccounts(User user){
		BankingSystem system = BankingSystem.getInstance();
		List<BankAccount> accounts = null;
		try {
			accounts = system.getAccounts(user.getUserName());
		} catch (LibraryException | DatabaseException e) {
			log.error(e);
			log.info(e.getMessage());
		}
		return accounts;
		
	}
	
	private void printAccounts(List<BankAccount> accounts) {
		for(BankAccount account: accounts) {
			log.info(account);
		}
	}
	
	private void postTransfer() {
		BankingSystem system = BankingSystem.getInstance();
		BufferedReader reader = MenuHelper.getReader();
		User activeUser = system.getActiveUser();
		List<BankAccount> accounts = getAccounts(activeUser);
		boolean successfulPost = false;
		if(accounts != null && accounts.size() > 0) {
			do {
				try {
					log.info("Enter A Source Account To Send Money From...");
					printAccounts(accounts);
					int sourceAccountNumber = Integer.parseInt(reader.readLine().trim());
					log.info("Enter A Destination Account To Send Money To...");
					int destinationAccountNumber = Integer.parseInt(reader.readLine().trim());
					log.info("Enter An Amount Of Money To Send...");
					double amount = Double.parseDouble(reader.readLine().trim());
					successfulPost = system.postMoneyTransfer(sourceAccountNumber, destinationAccountNumber, amount);
					if(successfulPost) {
						break;
					}else {
						log.info("Sorry There Was An Error When Making Your Post.");
					}
				}catch(IOException e) {	
					log.error(e);
					log.info("Sorry An Error Occured When Getting User Input");
				}catch(NumberFormatException e) {
					log.error(e);
					log.info("Invalid Input.");
				} catch (BankingSystemException | DatabaseException | LibraryException e) {
					log.info(e.getMessage());
				}
				
			}while(true);
		}
		
		try {
			if(successfulPost) {
				log.info("Money Transfer Posted. Come Back To See If It Was Accepted Later!!!");
			}
			
			log.info("Please Press Enter To Go Back To The Main Menu");
			reader.readLine();
		}catch(IOException e) {
			log.error(e);
		}
		prevMenu.presentMenu();
	}
	
	private void acceptTransfers() {
		System.out.println("Accept Transfer");
	}
	
	private void viewTransfers() {
		BankingSystem system = BankingSystem.getInstance();
		BufferedReader reader = MenuHelper.getReader();
		User activeUser = system.getActiveUser();
		try {
			log.info("All Transfers With You(" + activeUser.getUserName() + ") As The Sender And/Or Reciever");
			List<MoneyTransfer> transfers = system.viewMoneyTransfers(activeUser.getUserName());
			for(MoneyTransfer transfer: transfers) {
				log.info(transfer);
			}
		}catch(DatabaseException | LibraryException e) {
			log.error(e);
			log.info(e.getMessage());
		}
		try {
			log.info("Press Enter To Return To Main Menu...");
			reader.readLine();			
		}catch(IOException e) {
			log.error(e);
		}
		prevMenu.presentMenu();
		
	}
	
	public void presentMenu(Menu prevMenu) {
		setPreviousMenu(prevMenu);
		presentMenu();
	}
	
	private void setPreviousMenu(Menu prevMenu) {
		this.prevMenu = prevMenu;
	}

}
 