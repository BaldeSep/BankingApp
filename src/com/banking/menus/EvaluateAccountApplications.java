package com.banking.menus;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.banking.bo.BankingSystem;
import com.banking.bo.RequestTicket;
import com.banking.exception.BankingSystemException;
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
					log.error("Invalid Input Only Enter Numbers Between 0-" + (menuOptions.length - 1));
				}
			}catch(IOException e) {
				log.fatal("Sorry An Error Occured  When Getting Your Input. Contact Support.");
				QuitMenu.getMenu().presentMenu();
			}catch(NumberFormatException e) {
				log.error("Invalid Input. Enter Only Whole Numbers");
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
		List<RequestTicket> applications = new ArrayList<>();
		BufferedReader reader = MenuHelper.getReader();
		BankingSystem system = BankingSystem.getInstance();
		String userName = null;
		int ticketId = -1;
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
				printApplications(applications);
				log.info("Enter The ID Of The Applications You Wish To Approve");
				ticketId = Integer.parseInt(reader.readLine().trim());
				boolean applicationFound = false;
				for(RequestTicket application: applications) {
					if(ticketId == application.getId()) {
						system.approveAccountApplications(ticketId);
						applicationFound = true;
					}
				}
				if(!applicationFound) {
					log.error("Sorry We Could Not Find Application Number: [" + ticketId +"]" );
				}else {
					log.info("Account Created For User: [" +  userName + "]");
					break;
				}
			} catch (IOException e) {
				log.fatal("Sorry An Error Occured  When Getting Your Input. Contact Support.");
				QuitMenu.getMenu().presentMenu();
			}catch(NumberFormatException e) {
				log.error("Invalid Input For ID Number");
			} catch(BankingSystemException | DatabaseException | LibraryException e) {
				log.error(e.getMessage());
			}
		}while(true);
		
		try {
			log.info("Press Enter To Go Back To Previous Menu");
			reader.readLine();
		}catch(IOException e) {
			log.fatal("Sorry An Error Occured  When Getting Your Input. Contact Support.");
			QuitMenu.getMenu().presentMenu();
		}
		
		prevMenu.presentMenu();
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
				printApplications(applications);
			} catch (IOException e) {
				log.fatal("Sorry An Error Occured  When Getting Your Input. Contact Support.");
				QuitMenu.getMenu().presentMenu();
			} catch(DatabaseException | LibraryException e) {
				log.error(e.getMessage());
			}
		}while(true);
		
		try {
			log.info("Press Enter To Go Back To Previous Menu");
			reader.readLine();
		}catch(IOException e) {
			log.fatal("Sorry An Error Occured  When Getting Your Input. Contact Support.");
			QuitMenu.getMenu().presentMenu();
		}
		
		prevMenu.presentMenu();
		
	}
	
	
	private void printApplications(List<RequestTicket> applications) {
		for(RequestTicket application: applications) {
			log.info(application);
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
