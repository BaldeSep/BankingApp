package core.entry;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class WelcomeMenu implements Menu {
	private WelcomeMenuOptions[] welcomeMenuOptions;
	private static final Logger log = Logger.getLogger(WelcomeMenuOptions.class);
	private static final Menu welcomeMenu = new WelcomeMenu();
	private WelcomeMenu() {
		welcomeMenuOptions = WelcomeMenuOptions.values();
	}
	
	public static Menu getInstance() {
		return welcomeMenu;
	}
	
	@Override
	public void presentMenu() {
		BufferedReader input = MenuHelper.getReader();
		log.info("Welcome to the Banking System");
		MenuHelper.printMenuItems(welcomeMenuOptions);
		String option = "";
		try{
			option = input.readLine();
		}catch(IOException e) {
			log.error(e);
		}
		option = MenuHelper.verifyInput(option, welcomeMenuOptions);
		switchMenu(Integer.parseInt(option.trim()));
	}
	private void switchMenu(int userSelection) {
		Map<Integer, Menu> nextMenus = getNextMenus();
		for(WelcomeMenuOptions menuOption: welcomeMenuOptions) {
			if(userSelection ==  menuOption.ordinal() ) {
				switch(menuOption) {
				case QUIT:
					log.info("Program Shutting Down Goodbye");
					System.exit(0);
					break;
				default:
					nextMenus.get(menuOption.ordinal()).presentMenu();
					break;
				}
			}
		}
	}
	
	private Map<Integer, Menu> getNextMenus(){
		Map<Integer, Menu> nextMenus = new HashMap<>();
		for(WelcomeMenuOptions option : welcomeMenuOptions ) {
			switch(option) {
			case LOGIN:
				nextMenus.put(option.ordinal(), LoginMenu.getInstance());
				break;
			case REGISTER:
				nextMenus.put(option.ordinal(), RegisterMenu.getInstance());
				break;
			case QUIT:
				nextMenus.put(option.ordinal(), null);
				break;
			}
		}
		return nextMenus;
	}
}
