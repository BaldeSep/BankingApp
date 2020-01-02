package com.banking.main;

import org.apache.log4j.Logger;

import com.banking.menus.MainMenu;
import com.banking.menus.QuitMenu;

public class Main {
	private static final Logger log = Logger.getLogger(Main.class);
	public static void main(String[] args) {
		try{
			MainMenu.getMenu().presentMenu();
		}catch(Exception e) {
			log.fatal("A Fatal Error Occured During The Excution Of The Program...");
			QuitMenu.getMenu().presentMenu();
		}
	}

}
