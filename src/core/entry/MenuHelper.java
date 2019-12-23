package core.entry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

public class MenuHelper {
	private static final Logger log = Logger.getLogger(Menu.class);
	private static final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	public MenuHelper() {
		// TODO Auto-generated constructor stub
	}
	public static BufferedReader getReader() {
		return input;
	}
	
	public static boolean validateUserInput(String userInput, int numberOfOptions ) {
		if(userInput == null || userInput.matches("\\s*") || !userInput.matches("\\s*[0-" + numberOfOptions + "]\\s*")) {
			return false;
		}
		return true;
	}
	
	public static String verifyInput(String userInput, Enum menuItems[]) {
		while(!validateUserInput(userInput, menuItems.length - 1)) {
			log.error("Invalid Input Enter Number Between [0 - " + (menuItems.length-1) + "]");
			printMenuItems(menuItems);
			try{
				userInput = input.readLine();
			}catch(IOException e) {
				log.error(e);
			}
		}
		
		return userInput;
	}
	
	public static void printMenuItems(Enum menuItems[]) {
		log.info("Please Enter A Number Below to Make a Choice");
		for (int i = 0; i < menuItems.length; i++) {
			log.info(i+". " + menuItems[i] );
		}
		log.info("Enter Below Here");
	}
}
