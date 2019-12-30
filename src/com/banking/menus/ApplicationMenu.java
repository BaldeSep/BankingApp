package com.banking.menus;

import java.io.BufferedReader;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.banking.bo.BankingSystem;
import com.banking.bo.RequestTicket;
import com.banking.bo.User;
import com.banking.exception.BankingSystemException;
import com.banking.exception.DatabaseException;
import com.banking.exception.LibraryException;
import com.banking.menu.options.ApplicationMenuOptions;
import com.banking.util.MenuHelper;

public class ApplicationMenu implements Menu {
	private static final ApplicationMenu applicationMenu = new ApplicationMenu();
	private static final Logger log = Logger.getLogger(ApplicationMenu.class);
	private Menu prevMenu;
	
	private ApplicationMenu() {
		prevMenu = null;
	}
	
	public static ApplicationMenu getMenu() {
		return applicationMenu;
	}
	
	@Override
	public void presentMenu() {
		BufferedReader reader = MenuHelper.getReader();
		ApplicationMenuOptions menuOptions[] = ApplicationMenuOptions.values();
		int userInput = -1;
		do {
			try {
				log.info("Application Menu");
				MenuHelper.printMenuOptions(menuOptions);
				userInput = Integer.parseInt(reader.readLine().trim());
				if(userInput >= 0 && userInput <= menuOptions.length - 1) {
					break;
				}
			}catch(IOException e) {
				log.error(e);
				log.info("Sorry An Error Occured  When Getting Your Input. Contact Support.");
			}catch(NumberFormatException e) {
				log.error(e);
				log.info("Invalid Input. Enter Whole Numbers Only.");
			}
		}while(true);
		parseUserInput(userInput);
		
	}
	
	private void parseUserInput(int userInput) {
		ApplicationMenuOptions option = ApplicationMenuOptions.fromInt(userInput);
		boolean applicationSuccess = false;
		switch(option) {
		case Go_Back:
			prevMenu.presentMenu();
		case Enter_Initial_Balance:
			applicationSuccess = applyForBankAccount(ApplicationMenuOptions.Enter_Initial_Balance);
			break;
		case Default_Initial_Balance:
			applicationSuccess = applyForBankAccount(ApplicationMenuOptions.Default_Initial_Balance);
			break;
		}
		
		BufferedReader reader = MenuHelper.getReader();
		if(applicationSuccess) {
			log.info("Application Successfully Sent!!!");
			log.info("Check Back Later To See If It Was Accepted...");
		}else {
			log.info("Application Failed To Get Sent!!!");
			log.info("Check Back Later And Try Again, Sorry...");
		}
		try {
			log.info("Press Enter To Go Back To The Menu...");
			reader.readLine();
		}catch(IOException e) {
			log.error(e);
		}
		
		prevMenu.presentMenu();
	}
	
	private RequestTicket getRequestTicket(String userName, double initialBalance) {
		RequestTicket ticket = null;
		try {
			ticket = BankingSystem.getInstance().generateRequestTicket(userName, initialBalance);
		} catch (BankingSystemException e) {
			log.info(e.getMessage());
		}
		return ticket;
	}
	
	private boolean applyForBankAccount(ApplicationMenuOptions option) {
		BufferedReader reader = MenuHelper.getReader();
		BankingSystem system = BankingSystem.getInstance();
		double initialBalance = 0.00;
		if(option == ApplicationMenuOptions.Enter_Initial_Balance) {
			do {
				try {
					log.info("Enter Initial Balance Below(Eg. 120.99)...");
					initialBalance = Double.parseDouble(reader.readLine().trim());
					log.info("Initial Balance Set To: " + initialBalance);
					log.info("Please Press Enter To Continue...");
					reader.readLine();
					break;
				}catch(IOException e) {
					log.error(e);
					log.info("Sorry An Error Occured When Reading Input. Please Contact Support");
				}catch(NumberFormatException e) {
					log.error(e);
					log.info("Invalid Input. Enter Only Whole Numbers");
				}
			}while(true);
		}
		RequestTicket ticket = getRequestTicket(system.getActiveUser().getUserName(), initialBalance);
		boolean successfulApplication =  false;
		try{
			successfulApplication = system.applyForBankAccount(ticket);
		}catch(DatabaseException | LibraryException e) {
			log.error(e);
			log.info(e.getMessage());
		}
		return successfulApplication;
	}
	
	public void presentMenu(Menu prevMenu) {
		setPreviousMenu(prevMenu);
		presentMenu();
	}
	
	private void setPreviousMenu(Menu prevMenu) {
		this.prevMenu = prevMenu;
	}

}
