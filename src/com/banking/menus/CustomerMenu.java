package com.banking.menus;

import java.io.BufferedReader;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.banking.bo.BankingSystem;
import com.banking.bo.User;
import com.banking.exception.DatabaseException;
import com.banking.exception.LibraryException;
import com.banking.menu.options.CustomerMenuOptions;
import com.banking.util.MenuHelper;

public class CustomerMenu implements Menu {
	private static final CustomerMenu customerMenu = new CustomerMenu();
	private static final Logger log = Logger.getLogger(CustomerMenu.class);
	private Menu prevMenu; 
	private CustomerMenu() {
		prevMenu = null;
	}
	
	public static CustomerMenu getMenu() {
		return customerMenu;
	}
	
	@Override
	public void presentMenu() {
		BufferedReader reader = MenuHelper.getReader();
		BankingSystem system = BankingSystem.getInstance();
		User currentUser = system.getActiveUser();
		CustomerMenuOptions menuOptions[] = CustomerMenuOptions.values();
		int userInput = -1;
		log.info("Hello " + currentUser.getUserName());
		do {
			try {
				MenuHelper.printMenuOptions(menuOptions);
				userInput = Integer.parseInt(reader.readLine().trim());
				if(userInput >= 0 && userInput <= menuOptions.length - 1) {
					break;
				}else {
					log.info("Invalid Input, Enter Numbers Between 0" + "-" + (menuOptions.length - 1));
				}
			}catch(IOException e) {
				log.error(e);
				log.info("Sorry An Error Occured When Reading Your Input. Please Contact Support.");
			}catch(NumberFormatException e) {
				log.error(e);
				log.info("Invlid Input Enter Only Whole Numbers");
			}
		}while(true);
		
		parseUserInput(userInput);

	}
	public void presentMenu(Menu prevMenu) {
		setPreviousMenu(prevMenu);
		presentMenu();
	}
	
	private void parseUserInput(int userInput) {
		CustomerMenuOptions option = CustomerMenuOptions.fromInt(userInput);
		switch(option) {
		case Logout:
			logout();
			break;
		case Apply_For_Bank_Account:
			ApplicationMenu.getMenu().presentMenu(this);
			break;
		case View_Balance_Of_Bank_Account:
			ViewBalanceMenu.getMenu().presentMenu(this);
			break;
		case Make_Withdrawal:
			MakeWithdrawalMenu.getMenu().presentMenu(this);
			break;
		case Make_Deposit:
			MakeDepositMenu.getMenu().presentMenu(this);
			break;
		case Money_Transfers:
			MoneyTransferMenu.getMenu().presentMenu(this);
			break;
		case Quit:
			QuitMenu.getMenu().presentMenu();
			break;
			
		}
	}
	
	private void logout() {
		BufferedReader reader = MenuHelper.getReader();
		BankingSystem.getInstance().endUserSession();
		log.info("Logged User Out Press Enter To Go Back To Main Menu...");
		try {
			reader.readLine();
		} catch (IOException e) {
			log.error(e);
		}
		MainMenu.getMenu().presentMenu();
	}
	
	
	private void setPreviousMenu(Menu prevMenu) {
		this.prevMenu = prevMenu;
	}
	
	

}
