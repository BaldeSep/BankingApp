package com.banking.menu.options;

public enum CustomerMenuOptions {
	Logout, 
	Apply_For_Bank_Account, 
	View_Balance_Of_Bank_Account, 
	Make_Withdrawal, 
	Make_Deposit,  
	Money_Transfers,
	Quit;
	public static CustomerMenuOptions fromInt(int value) {
		switch(value) {
		case 0:
			return Logout;
		case 1:
			return Apply_For_Bank_Account;
		case 2:
			return View_Balance_Of_Bank_Account;
		case 3:
			return Make_Withdrawal;
		case 4:
			return Make_Deposit;
		case 5:
			return Money_Transfers;
		case 6:
			return Quit;
		}
		return null;
	}
}
