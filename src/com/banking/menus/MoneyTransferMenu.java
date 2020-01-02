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
					log.error("Invalid Input. Enter Numbers Between 0-" +  (menuOptions.length - 1));
				}
			} catch (IOException e) {
				log.fatal("Sorry An Error Occured  When Getting Your Input. Contact Support.");
				QuitMenu.getMenu().presentMenu();
			} catch(NumberFormatException e) {
				log.error("Invalid Input. Enter Whole Numbers Only");
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
			log.error(e.getMessage());
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
		String tempUserInput = "";
		if(accounts != null && accounts.size() > 0) {
			do {
				try {
					log.info("Enter A Source Account To Send Money From...[Enter Nothing An Press Enter To Quit]");
					printAccounts(accounts);
					tempUserInput = reader.readLine();
					if(tempUserInput.isEmpty()) {
						break;
					}
					int sourceAccountNumber = Integer.parseInt(tempUserInput.trim());
					log.info("Enter A Destination Account To Send Money To...");
					int destinationAccountNumber = Integer.parseInt(reader.readLine().trim());
					log.info("Enter An Amount Of Money To Send...");
					double amount = Double.parseDouble(reader.readLine().trim());
					successfulPost = system.postMoneyTransfer(sourceAccountNumber, destinationAccountNumber, amount);
					if(successfulPost) {
						break;
					}else {
						log.error("Sorry There Was An Error When Making Your Post.");
					}
				}catch(IOException e) {	
					log.fatal("Sorry An Error Occured  When Getting Your Input. Contact Support.");
					QuitMenu.getMenu().presentMenu();
				}catch(NumberFormatException e) {
					log.error("Invalid Input.");
				} catch (BankingSystemException | DatabaseException | LibraryException e) {
					log.error(e.getMessage());
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
			log.fatal("Sorry An Error Occured  When Getting Your Input. Contact Support.");
			QuitMenu.getMenu().presentMenu();
		}
		prevMenu.presentMenu();
	}
	
	private void acceptTransfers() {
		BankingSystem system = BankingSystem.getInstance();
		BufferedReader reader = MenuHelper.getReader();
		User activeUser = system.getActiveUser();
		String tempUserInput = "";
		MoneyTransfer acceptedTransfer = null;
		try {
			log.info("Enter The Transfer Id That You Wish To Accept[Enter Nothing And Press Enter To Quit]");
			List<MoneyTransfer> transfers = system.viewMoneyTransfers(activeUser.getUserName());
			printAllTransfers(activeUser, transfers);
			tempUserInput = reader.readLine();
			if(!tempUserInput.isEmpty()) {
				int transferId = Integer.parseInt(tempUserInput.trim());
				acceptedTransfer = system.acceptMoneyTransfer(transferId);
			}
		}catch(IOException e) {
			log.fatal("Sorry An Error Occured  When Getting Your Input. Contact Support.");
			QuitMenu.getMenu().presentMenu();
		}catch(NumberFormatException e) {
			log.error("invalid Input Enter A Whole Number");
		}catch(DatabaseException | LibraryException | BankingSystemException e) {
			log.error(e.getMessage());
		}	
		try {
			if(acceptedTransfer != null) {
				log.info("Transfer Accepted From Account " + acceptedTransfer.getSourceAccountNumber() + " To Account " + acceptedTransfer.getDestinationAccountNumber());
				log.info("For The Amount Of: " + acceptedTransfer.getAmount());
			}
			
			log.info("Press Enter To Return To Main Menu...");
			reader.readLine();			
		}catch(IOException e) {
			log.fatal("Sorry An Error Occured  When Getting Your Input. Contact Support.");
			QuitMenu.getMenu().presentMenu();
		}
		prevMenu.presentMenu();
		
	}
	
	private void viewTransfers() {
		BankingSystem system = BankingSystem.getInstance();
		BufferedReader reader = MenuHelper.getReader();
		User activeUser = system.getActiveUser();
		try {
			log.info("All Transfers With You(" + activeUser.getUserName() + ") As The Sender And/Or Reciever");
			List<MoneyTransfer> transfers = system.viewMoneyTransfers(activeUser.getUserName());
			printAllTransfers(activeUser, transfers);
		}catch(DatabaseException | LibraryException e) {
			log.error(e.getMessage());
		}
		try {
			log.info("Press Enter To Return To Main Menu...");
			reader.readLine();			
		}catch(IOException e) {
			log.fatal("Sorry An Error Occured  When Getting Your Input. Contact Support.");
			QuitMenu.getMenu().presentMenu();
		}
		prevMenu.presentMenu();
	}
	
	private void printAllTransfers(User activeUser, List<MoneyTransfer> transfers) {
		for(MoneyTransfer transfer: transfers) {
			log.info(transfer);
		}
	}
	
	public void presentMenu(Menu prevMenu) {
		setPreviousMenu(prevMenu);
		presentMenu();
	}
	
	private void setPreviousMenu(Menu prevMenu) {
		this.prevMenu = prevMenu;
	}

}
 