package com.banking.menus;

import java.io.BufferedReader;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.banking.bo.BankingSystem;
import com.banking.bo.types.UserType;
import com.banking.exception.BankingSystemException;
import com.banking.exception.DatabaseException;
import com.banking.exception.LibraryException;
import com.banking.menu.options.RegisterMenuOptions;
import com.banking.util.MenuHelper;

public class RegisterMenu implements Menu {
	private static final RegisterMenu registerMenu = new RegisterMenu();
	private static final Logger log = Logger.getLogger(RegisterMenu.class);
	private Menu prevMenu;
	private RegisterMenu() {
		
	}
	public static RegisterMenu getMenu() {
		return registerMenu;
	}
	@Override
	public void presentMenu() {
		BufferedReader reader = MenuHelper.getReader();
		RegisterMenuOptions menuOptions[] = RegisterMenuOptions.values();
		int userInput = -1;
		do {
			try {
				MenuHelper.printMenuOptions(menuOptions);
				userInput = Integer.parseInt(reader.readLine().trim());
				if(userInput >= 0 && userInput <= menuOptions.length - 1) {
					break;
				}else {
					log.error("Please Enter Numbers Between: " + 0 + "-" + (menuOptions.length-1));
				}
			}catch(IOException e) {
				log.fatal("Sorry There Was An Error When Getting Your Input Contact Support");
				QuitMenu.getMenu().presentMenu();
			}catch(NumberFormatException e) {
				log.error("Invalid Input. Enter Only Whole Numbers.");
			}
		}while(true);
		
		parseUserInput(userInput);
		
	}
	
	private void parseUserInput(int userInput) {
		RegisterMenuOptions option = RegisterMenuOptions.fromInt(userInput);
		switch(option) {
		case Register_As_Customer:
			registerUser(UserType.Customer);
			break;
		case Register_As_Employee:
			registerUser(UserType.Employee);
			break;
		case Go_Back:
			prevMenu.presentMenu();
			break;
		case Quit:
			QuitMenu.getMenu().presentMenu();
			break;
		default:
			break;
		}
	}
	
	private void registerUser(UserType type) {
		BufferedReader reader = MenuHelper.getReader();
		BankingSystem system = BankingSystem.getInstance();
		String userName, password = "";
		boolean registerSuccessful = false;
		do {
			try {
				log.info("Please Enter Your User Name Below");
				userName = reader.readLine();
				log.info("Please Enter Your Password Below");
				password = reader.readLine();
				if(system.registerUser(userName, password, type)) {
					registerSuccessful = true;
					break;
				}
			}catch(IOException e) {
				log.fatal("Sorry There Was An Error When Trying To Read Your Input Contact Support");
				QuitMenu.getMenu().presentMenu();
			}catch(BankingSystemException | DatabaseException | LibraryException e) {
				log.error(e.getMessage());
			}
		}while(true);
		
		if(registerSuccessful && type == UserType.Customer) {
			log.info("Registered Cutomer: " + userName );
		}else if(registerSuccessful && type == UserType.Employee) {
			log.info("Registered Employee: " + userName);
		}else {
			log.error("Sorry There Was An Issue Registering User: " + userName + ". Contact Support As Soon As Possible");
		}
		try {
			log.info("Please Press Enter To Return To The Main Menu...");
			reader.readLine();
		} catch (IOException e) {
			log.fatal("Sorry There Was An Error When Trying To Read Your Input Contact Support");
			QuitMenu.getMenu().presentMenu();
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
