package com.banking.menus;

import org.apache.log4j.Logger;

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
		log.info("Register");
	}
	
	public void presentMenu(Menu prevMenu) {
		setPreviousMenu(prevMenu);
		presentMenu();
	}
	
	private void setPreviousMenu(Menu prevMenu) {
		this.prevMenu = prevMenu;
	}

}
