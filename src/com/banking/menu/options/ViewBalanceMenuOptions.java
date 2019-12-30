package com.banking.menu.options;

public enum ViewBalanceMenuOptions {
	Go_Back, 
	View_Balance_Of_All_Accounts,
	View_Balance_Of_Single_Account;
	
	public static ViewBalanceMenuOptions fromInt(int value) {
		switch(value) {
		case 0:
			return Go_Back;
		case 1:
			return View_Balance_Of_All_Accounts;
		case 2:
			return ViewBalanceMenuOptions.View_Balance_Of_Single_Account;
		}
		return null;
	}
}
