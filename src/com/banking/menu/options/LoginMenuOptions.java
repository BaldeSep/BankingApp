package com.banking.menu.options;

public enum LoginMenuOptions{
	Enter_Credentials, Go_Back, Quit;
	
	public static LoginMenuOptions fromInt(int value) {
		switch(value) {
		case 0:
			return LoginMenuOptions.Enter_Credentials;
		case 1:
			return LoginMenuOptions.Go_Back;
		case 2:
			return LoginMenuOptions.Quit;
		}
		return null;
	}
}
