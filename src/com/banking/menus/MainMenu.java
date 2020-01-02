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
		
		int userInput = 0;
		log.info("Welcome To The Banking System");
		do {
			try {
				MenuHelper.printMenuOptions(menuOptions);
				userInput = Integer.parseInt(reader.readLine().trim());
				if(userInput >= 0 && userInput <= menuOptions.length - 1) {
					break;
				}else {
					log.error("Invalid Number Input");
				}
				
			} catch (IOException e) {
				log.fatal("Sorry An Error Occured When Reading Your Input Contact Support.");
				QuitMenu.getMenu().presentMenu();
			} catch(NumberFormatException e) {
				log.error("Invalid Input: Please Enter Only Integers");
			}
		}while(true);
		parseUserInput(userInput);
	}
	
	private void parseUserInput(int userInput) {
		MainMenuOptions option = MainMenuOptions.fromInt(userInput);
		switch(option) {
		case Login:
			LoginMenu.getMenu().presentMenu(this);
			break;
		case Register:
			RegisterMenu.getMenu().presentMenu(this);
			break;
		case Quit:
			QuitMenu.getMenu().presentMenu();
		}
	}

}
