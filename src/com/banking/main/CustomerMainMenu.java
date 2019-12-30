package com.banking.main;

import org.apache.log4j.Logger;

public class CustomerMainMenu implements Menu {
	private static final Logger log = Logger.getLogger(CustomerMainMenu.class);
	private static final Menu customerMainMenu = new CustomerMainMenu();
	
	private CustomerMainMenu() {
	}
	
	public static Menu getInstance() {
		return customerMainMenu;
	}
	
	@Override
	public void presentMenu() {
		log.info("Customer Menu");
	}

}
