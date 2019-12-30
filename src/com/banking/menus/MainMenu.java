package com.banking.menus;

import java.io.BufferedReader;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.banking.menu.options.MainMenuOptions;
import com.banking.util.MenuHelper;

public class MainMenu implements Menu {
	private static final Logger log = Logger.getLogger(MainMenu.class);
	private static final Menu mainMenu = new MainMenu();
	private MainMenu() {
	}
	
	public static Menu getMenu() {
		return mainMenu;
	}
	
	@Override
	public void presentMenu() {
		BufferedReader reader = MenuHelper.getReader();
		MainMenuOptions menuOptions[] = MainMenuOptions.values();
		
		int userInput = -1;
		log.info("Welcome To The Banking System");
		do {
			try {
				MenuHelper.printMenuOptions(menuOptions);
				userInput = Integer.parseInt(reader.readLine().trim());
				if(userInput >= 0 && userInput <= menuOptions.length - 1) {
					break;
				}else {
					log.info("Invalid Number Input");
				}
				
			} catch (IOException e) {
				log.error("Sorry An Error Occured When Reading Your Input Contact Support.");
			} catch(NumberFormatException e) {
				log.error("Invalid Input: Please Enter Only Integers");
			}
		}while(true);
		parseUserInput(userInput);
	}
	
	private void parseUserInput(int userInput) {
		switch(userInput) {
		case 0:
			LoginMenu.getMenu().presentMenu(this);
			break;
		case 1:
			RegisterMenu.getMenu().presentMenu();
			break;
		case 2:
			QuitMenu.getMenu().presentMenu();
		default:
			break;
		}
	}

}
