package com.banking.menus;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.banking.bo.BankingSystem;
import com.banking.bo.RequestTicket;
import com.banking.exception.DatabaseException;
import com.banking.exception.LibraryException;
import com.banking.menu.options.EvaluateAccountApplicationsOptions;
import com.banking.util.MenuHelper;

public class EvaluateAccountApplications implements Menu {
	private static final EvaluateAccountApplications evaluateAccountApplications = new EvaluateAccountApplications(); 
	private static final Logger log = Logger.getLogger(EvaluateAccountApplications.class);
	private Menu prevMenu;
	
	private EvaluateAccountApplications() {
		prevMenu = null;
	}
	
	public static EvaluateAccountApplications getMenu() {
		return evaluateAccountApplications;
	}
	
	@Override
	public void presentMenu() {
		BufferedReader reader = MenuHelper.getReader();
		EvaluateAccountApplicationsOptions menuOptions[] = EvaluateAccountApplicationsOptions.values();
		int userInput = -1;
		do {
			try {
				MenuHelper.printMenuOptions(menuOptions);
				userInput = Integer.parseInt(reader.readLine().trim());
				if(userInput >= 0 && userInput <= menuOptions.length - 1) {
					break;
				}else {
					log.info("Invalid Input Only Enter Numbers Between 0-" + (menuOptions.length - 1));
				}
			}catch(IOException e) {
				log.error(e);
				log.info("Sorry An Error Occured When Reading User Input.");
			}catch(NumberFormatException e) {
				log.error(e);
				log.info("Invalid Input. Enter Only Whole Numbers");
			}
		}while(true);
		parseUserInput(userInput);
	}
	
	private void parseUserInput(int userInput) {
		EvaluateAccountApplicationsOptions option = EvaluateAccountApplicationsOptions.fromInt(userInput);
		switch(option) {
		case Go_Back:
			prevMenu.presentMenu();
			break;
		case View_Account_Applications:
			viewAccountApplications();
			break;
		case Accept_Account_Applications:
			approveAccountApplications();
			break;
		}
	}
	
	private void approveAccountApplications() {
		
	}
	
	private void viewAccountApplications() {
		List<RequestTicket> applications = new ArrayList<>();
		BufferedReader reader = MenuHelper.getReader();
		BankingSystem system = BankingSystem.getInstance();
		String userName = null;
		do {
			try {
				log.info("Enter The User Name Of The Customer To View Their Accounts [Enter Nothing And Press Enter To Quit]");
				userName = reader.readLine();
				if(userName.isEmpty()) {
					break;
				}
				applications = system.viewAccountApplications(userName);
				if(applications.size() == 0) {
					break;
				}
				for(RequestTicket ticket: applications) {
					log.info(ticket);
				}
			} catch (IOException e) {
				log.error(e);
				log.info("Sorry An Error Occured When Reading User Input.");
			} catch(DatabaseException | LibraryException e) {
				log.error(e);
				log.info(e.getMessage());
			}
		}while(true);
		
		try {
			log.info("Press Enter To Go Back To Previous Menu");
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
