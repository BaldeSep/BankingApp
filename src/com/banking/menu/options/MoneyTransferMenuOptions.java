package com.banking.menu.options;

public enum MoneyTransferMenuOptions {
	Go_Back,
	Post_Transfer,
	View_Transfers,
	Accept_Transfers;
	
	public static MoneyTransferMenuOptions fromInt(int value) {
		switch(value) {
		case 0:
			return Go_Back;
		case 1:
			return Post_Transfer;
		case 2:
			return View_Transfers;
		case 3:
			return Accept_Transfers;
		}
		return null;
	}
}
