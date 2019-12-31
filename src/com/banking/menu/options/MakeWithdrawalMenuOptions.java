package com.banking.menu.options;

public enum MakeWithdrawalMenuOptions {
	Go_Back, 
	Enter_Amount_To_Withdrawal;
	
	
	public static MakeWithdrawalMenuOptions fromInt(int value) {
		switch(value) {
		case 0:
			return Go_Back;
		case 1:
			return Enter_Amount_To_Withdrawal;
		}
		
		return null;
	}
}
