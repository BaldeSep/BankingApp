package com.banking.menu.options;

public enum CustomerMenuOptions {
	Logout, 
	Apply_For_Bank_Account, 
	View_Balance_Of_Bank_Account, 
	Make_Withdrawal, 
	Make_Deposit,  
	Post_Money_Transfer,
	Accept_Money_Transfer,
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
			return Post_Money_Transfer;
		case 6:
			return Accept_Money_Transfer;
		case 7:
			return Quit;
		}
		return null;
	}
}
