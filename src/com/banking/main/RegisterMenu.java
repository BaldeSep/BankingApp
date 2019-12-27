package com.banking.main;

import java.io.BufferedReader;

import org.apache.log4j.Logger;

import com.banking.util.MenuHelper;

public class RegisterMenu implements Menu {
	private static final Logger log = Logger.getLogger(RegisterMenu.class);
	private static final Menu registerMenu = new RegisterMenu();
	public RegisterMenu() {
		
	}
	public static Menu getInstance() {
		return registerMenu;
	}
	@Override
	public void presentMenu() {
		System.out.println("Register User Here");
	}

	public void switchMenu(int option) {
		System.out.println("Register Switch Menus Here");
	}

}
