package com.banking.menu.options;

public enum EmployeeMenuOptions {
	Logout,
	View_Customer_Bank_Account,
	View_Log_Of_Transactions,
	Evaluate_Account_Applications,
	Quit;
	
	public static EmployeeMenuOptions fromInt(int value) {
		switch(value) {
		case 0:
			return Logout;
		case 1:
			return View_Customer_Bank_Account;
		case 2:
			return View_Log_Of_Transactions;
		case 3:
			return Evaluate_Account_Applications;
		case 4:
			return Quit;
		}
		return null;
	}
}
