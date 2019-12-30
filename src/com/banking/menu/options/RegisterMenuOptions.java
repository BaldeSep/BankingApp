package com.banking.menu.options;

public enum RegisterMenuOptions {
	Register_As_Customer, Register_As_Employee, Go_Back, Quit;
	
	public static RegisterMenuOptions fromInt(int value) {
		switch(value) {
		case 0:
			return Register_As_Customer;
		case 1:
			return Register_As_Employee;
		case 2:
			return Go_Back;
		case 3:
			return Quit;
		}
		return null;
	}
}
