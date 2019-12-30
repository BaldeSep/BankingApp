package com.banking.menu.options;

import com.banking.menus.QuitMenu;

public enum MainMenuOptions{
	Login, Register, Quit;
	
	public static MainMenuOptions fromInt(int value) {
		switch(value) {
		case 0:
			return MainMenuOptions.Login;
		case 1:
			return MainMenuOptions.Register;
		case 2:
			return MainMenuOptions.Quit;
		}
		return null;
	}
}
