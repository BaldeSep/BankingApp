package com.banking.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.banking.main.menuoptions.LoginMenuOptions;
import com.banking.util.MenuHelper;

public class LoginMenu implements Menu{
	private LoginMenuOptions[] loginMenuOptions;
	private static final Logger log = Logger.getLogger(LoginMenu.class);
	private static final Menu loginMenu = new LoginMenu();
	private LoginMenu() {
		loginMenuOptions =  LoginMenuOptions.values();
	}
	public static Menu getInstance() {
		return loginMenu;
	}
	
	@Override
	public void presentMenu() {
		BufferedReader input = MenuHelper.getReader();
		MenuHelper.printMenuItems(loginMenuOptions);
		String option = "";
		try {
			option = input.readLine();
		}catch(IOException e) {
			log.error(e);
		}
		option = MenuHelper.verifyInput(option, loginMenuOptions);
		switchMenu(Integer.parseInt(option.trim()));
	}
	private void switchMenu(int userSelection) {
		Map<Integer, Menu> nextMenus = getNextMenus();
		for(LoginMenuOptions option : loginMenuOptions) {
			if(userSelection == option.ordinal()) {
				switch (option){
				case ENTER_CREDENTIALS:
					getUserCredentials();
					break;
				case GO_BACK:
					nextMenus.get(option.ordinal()).presentMenu();
					break;
				case QUIT:
					nextMenus.get(option.ordinal()).presentMenu();
					break;
				}
			}
		}
	}
	
	private Map<Integer, Menu> getNextMenus(){
		Map<Integer, Menu> nextMenus = new HashMap<>();
		for(LoginMenuOptions option : loginMenuOptions ) {
			switch(option) {
			case ENTER_CREDENTIALS:
				nextMenus.put(option.ordinal(), null);
				break;
			case GO_BACK:
				nextMenus.put(option.ordinal(), WelcomeMenu.getInstance());
				break;
			case QUIT:
				nextMenus.put(option.ordinal(), QuitMenu.getInstance());
				break;
			}
		}
		return nextMenus;
	}
	
	private void getUserCredentials() {
		BufferedReader input = MenuHelper.getReader();
		String userName, password = "";
		try {
			log.info("Enter Your User Name");
			userName = input.readLine();
			log.info("Enter Your Password");
			password = input.readLine();
		}catch(IOException e) {
			log.error(e);
		}
		
		// Implement Validation Through the Backend Later...
		boolean userExists = true;
		
		// Call MainMenu Here
		
	}
}
