package com.banking.menu.options;

public enum ViewLogsMenuOptions {
	Go_Back,
	View_Deposit_And_Withdrawal_Logs,
	View_Money_Transfer_Logs;
	
	public static ViewLogsMenuOptions fromInt(int value) {
		switch(value) {
		case 0:
			return Go_Back;
		case 1:
			return View_Deposit_And_Withdrawal_Logs;
		case 2:
			return View_Money_Transfer_Logs;
		}
		
		return null;
	}
}
