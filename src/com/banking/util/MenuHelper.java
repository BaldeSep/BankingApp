package com.banking.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import com.banking.menu.options.MainMenuOptions;

public class MenuHelper {
	private static final Logger log = Logger.getLogger(MenuHelper.class);
	
	private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	
	public static BufferedReader getReader() {
		return reader;
	}
	
	public static void printMenuOptions(Enum menuOptions[]) {
		for(Enum option : menuOptions) {
			log.info(option.ordinal() + ") " + option);
		}
		log.info("Enter Any Number Between " + 0 + " - " + (menuOptions.length - 1) + " Below");
	}

}
