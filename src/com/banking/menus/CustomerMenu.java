package com.banking.menus;

import java.io.BufferedReader;

import com.banking.menu.options.CustomerMenuOptions;
import com.banking.util.MenuHelper;

public class CustomerMenu implements Menu {
	private static final CustomerMenu customerMenu = new CustomerMenu();
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
		CustomerMenuOptions menuOptions[] = CustomerMenuOptions.values();
		MenuHelper.printMenuOptions(menuOptions);

	}
	public void presentMenu(Menu prevMenu) {
		setPreviousMenu(prevMenu);
		presentMenu();
	}
	private void setPreviousMenu(Menu prevMenu) {
		this.prevMenu = prevMenu;
	}
	
	

}
