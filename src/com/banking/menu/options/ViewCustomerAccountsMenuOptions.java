package com.banking.menu.options;

public enum ViewCustomerAccountsMenuOptions {
	Go_Back,
	View_All_Customers_Accouts,
	View_One_Customer_Account;
	
	public static ViewCustomerAccountsMenuOptions fromInt(int value) {
		switch(value) {
		case 0: 
			return Go_Back;
		case 1:
			return View_All_Customers_Accouts;
		case 2:
			return View_One_Customer_Account;
		}
		return null;
	}
}
