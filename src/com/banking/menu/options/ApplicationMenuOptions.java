package com.banking.menu.options;

public enum ApplicationMenuOptions {
	Go_Back,
	Enter_Initial_Balance,
	Default_Initial_Balance;
	
	public static ApplicationMenuOptions fromInt(int value) {
		switch(value) {
		case 0:
			return Go_Back;
		case 1:
			return Enter_Initial_Balance;
		case 2:
			return Default_Initial_Balance;
		}
		return null;
	}
}
