package com.banking.menus;

import java.io.BufferedReader;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.banking.bo.User;
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
			RegisterMenu.getMenu().presentMenu(this);
			break;
		case 2:
			QuitMenu.getMenu().presentMenu();
			break;
		default:
			break;
		}
	}
	
	private void login() {		
	}
	
	private void setPreviousMenu(Menu prevMenu) {
		this.prevMenu = prevMenu;
	}

}
