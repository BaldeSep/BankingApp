package com.banking.menus;

import java.io.BufferedReader;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.banking.bo.BankingSystem;
import com.banking.bo.User;
import com.banking.bo.types.UserType;
import com.banking.exception.DatabaseException;
import com.banking.exception.LibraryException;
import com.banking.menu.options.LoginMenuOptions;
import com.banking.util.MenuHelper;

public class LoginMenu implements Menu {
	private static final LoginMenu loginMenu = new LoginMenu();
	private static final Logger log = Logger.getLogger(LoginMenu.class);
	private Menu prevMenu;
	private LoginMenu() {
		prevMenu = null;
	}
	public static LoginMenu getMenu() {
		return loginMenu;
	}
	@Override
	public void presentMenu() {
		BufferedReader reader = MenuHelper.getReader();
		LoginMenuOptions menuOptions[] = LoginMenuOptions.values();
		int userInput = -1;
		do {
			try {				
				MenuHelper.printMenuOptions(menuOptions);
				userInput = Integer.parseInt(reader.readLine().trim());
				if(userInput >= 0 && userInput <= menuOptions.length - 1) {
					break;
				}else {
					log.info("Invalid Number Input");
				}
			}catch(IOException e) {
				log.error("Sorry An Error Occured When Reading Your Input Contact Support.");
			}catch(NumberFormatException e) {
				log.error(e);
				log.info("Invalid Input: Please Enter An Integer");
			}
		}while(true);
		parseUserInput(userInput);
	}
	
	public void presentMenu(Menu prevMenu) {
		setPreviousMenu(prevMenu);
		presentMenu();
	}
	
	private void parseUserInput(int userInput) {
		switch(userInput) {
		case 0:
			login();
			break;
		case 1:
			MainMenu.getMenu().presentMenu();
			break;
		case 2:
			QuitMenu.getMenu().presentMenu();
			break;
		default:
			break;
		}
	}
	
	private void login() {
		BufferedReader reader = MenuHelper.getReader();
		BankingSystem system = BankingSystem.getInstance();
		User verifiedUser = null;
		String userName, password = "";
		int triesLeft = 3;
		do {
			try {
				log.info("You Have " + triesLeft + " attempts left to Login");
				log.info("Enter User Name Below");
				userName = reader.readLine();
				log.info("Enter Password Below");
				password = reader.readLine();
				verifiedUser = system.verifyUserCredentials(userName, password);
				if(verifiedUser != null) {
					break;
				}
			}catch(IOException e) {
				log.info("Sorry There Was An Issue Readin Your Input. Try Contacting Support");
				log.error(e);
			}catch(DatabaseException | LibraryException e) {
				log.info(e.getMessage());
				log.error(e);
			}
			if(triesLeft == 0) {
				break;
			}
			triesLeft--;
		}while(true);
		
		if(triesLeft <= 0) {
			log.info("Too Many Failed Attempts Try Again Later.");
			prevMenu.presentMenu();
		}
		else if(verifiedUser.getType() == UserType.Customer) {
			System.out.println("Customer Menu");
		}else if(verifiedUser.getType() == UserType.Employee) {
			System.out.println("Employee Menu");
		}
	}
	
	private void setPreviousMenu(Menu prevMenu) {
		this.prevMenu = prevMenu;
	}

}
