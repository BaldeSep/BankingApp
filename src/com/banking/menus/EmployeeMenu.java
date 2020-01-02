package com.banking.menus;

import java.io.BufferedReader;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.banking.bo.BankingSystem;
import com.banking.menu.options.EmployeeMenuOptions;
import com.banking.util.MenuHelper;

public class EmployeeMenu implements Menu {
	private static final EmployeeMenu employeeMenu = new EmployeeMenu();
	private static final Logger log = Logger.getLogger(EmployeeMenu.class);
	private Menu prevMenu;
	private EmployeeMenu() {
		prevMenu = null;
	}
	
	public static EmployeeMenu getMenu() {
		return employeeMenu;
	}
	
	@Override
	public void presentMenu() {
		BankingSystem system = BankingSystem.getInstance();
		BufferedReader reader = MenuHelper.getReader();
		EmployeeMenuOptions menuOptions[] = EmployeeMenuOptions.values();
		int userInput = -1;
		do {
			try {
				log.info("Hello " + system.getActiveUser().getUserName());
				MenuHelper.printMenuOptions(menuOptions);
				userInput = Integer.parseInt(reader.readLine().trim());
				if(userInput >= 0 && userInput <= menuOptions.length - 1) {
					break;
				}else {
					log.error("Invalid Input. Enter A Number Between 0-" + (menuOptions.length - 1));
				}
			}catch(IOException e) {
				log.fatal("Sorry An Error Ocurred While Getting User Input");
				QuitMenu.getMenu().presentMenu();
			}catch(NumberFormatException e) {
				log.error("Invalid Input. Enter Only Whole Numbers.");
			}
			
		}while(true);
		parseUserInput(userInput);
	}
	
	private void parseUserInput(int userInput) {
		EmployeeMenuOptions option = EmployeeMenuOptions.fromInt(userInput);
		switch (option) {
		case Logout:
			logout();
		case View_Customer_Bank_Account:
			ViewCustomerAccountsMenu.getMenu().presentMenu(this);
			break;
		case View_Log_Of_Transactions:
			ViewLogsMenu.getMenu().presentMenu(this);
			break;
		case Evaluate_Account_Applications:
			EvaluateAccountApplications.getMenu().presentMenu(this);
			break;
		case Quit:
			QuitMenu.getMenu().presentMenu();
		}
	}
	
	private void logout() {
		BufferedReader reader = MenuHelper.getReader();
		BankingSystem.getInstance().endUserSession();
		log.info("Logged User Out Press Enter To Go Back To Main Menu...");
		try {
			reader.readLine();
		} catch (IOException e) {
			log.fatal("Sorry An Error Ocurred While Getting User Input");
			QuitMenu.getMenu().presentMenu();
		}
		MainMenu.getMenu().presentMenu();
	}
	
	public void presentMenu(Menu prevMenu) {
		setPreviousMenu(prevMenu);
		presentMenu();
	}
	
	private void setPreviousMenu(Menu prevMenu) {
		this.prevMenu = prevMenu;
	}

}
