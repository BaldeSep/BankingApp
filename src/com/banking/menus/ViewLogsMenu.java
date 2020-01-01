package com.banking.menus;

import java.io.BufferedReader;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.banking.menu.options.ViewLogsMenuOptions;
import com.banking.util.MenuHelper;

public class ViewLogsMenu implements Menu {
	private static final ViewLogsMenu viewLogsMenu = new ViewLogsMenu();
	private static final Logger log = Logger.getLogger(ViewLogsMenu.class);
	private Menu prevMenu;
	
	private ViewLogsMenu() {
		prevMenu = null;
	}
	
	public static ViewLogsMenu getMenu() {
		return viewLogsMenu;
	}
	
	@Override
	public void presentMenu() {
		BufferedReader reader = MenuHelper.getReader();
		ViewLogsMenuOptions menuOptions[] = ViewLogsMenuOptions.values();
		int userInput = -1;
		do {
			try {
				MenuHelper.printMenuOptions(menuOptions);
				userInput = Integer.parseInt(reader.readLine().trim());
				if(userInput >= 0 && userInput <= menuOptions.length - 1) {
					break;
				}else {
					log.info("Invalid Input. Number Must Be Between 0-" + (menuOptions.length - 1) );
				}
			}catch(IOException e) {
				log.error(e);
				log.info("Sorry An Error Occured When Reading User Input");
			}catch(NumberFormatException e) {
				log.error(e);
				log.info("Invalid Input. Enter Only Whole Numbers");
			}
		}while(true);
		
		parseUserInput(userInput);

	}
	
	private void parseUserInput(int userInput) {
		ViewLogsMenuOptions option = ViewLogsMenuOptions.fromInt(userInput);
		switch(option) {
		case Go_Back:
			prevMenu.presentMenu();
			break;
		case View_Deposit_And_Withdrawal_Logs:
			viewDepositAndWithdrawalLogs();
			break;
		case View_Money_Transfer_Logs:
			viewMoneyTransferLogs();
			break;
		}
	}
	
	private void viewDepositAndWithdrawalLogs() {
		
	}
	
	private void viewMoneyTransferLogs() {
		
	}
	
	public void presentMenu(Menu prevMenu) {
		setPreviousMenu(prevMenu);
		presentMenu();
	}
	
	public void setPreviousMenu(Menu prevMenu) {
		this.prevMenu = prevMenu;
	}

}
