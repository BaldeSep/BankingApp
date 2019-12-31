package com.banking.menu.options;

public enum MakeDepositMenuOptions {
	Go_Back, 
	Enter_Amount_To_Deposit;
	
	public static MakeDepositMenuOptions fromInt(int value) {
		switch(value) {
		case 0:
			return Go_Back;
		case 1:
			return Enter_Amount_To_Deposit;
		}
		return null;
	}
}
