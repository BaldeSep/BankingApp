package com.banking.menu.options;

public enum EvaluateAccountApplicationsOptions {
	Go_Back,
	View_Account_Applications,
	Accept_Account_Applications;
	
	public static EvaluateAccountApplicationsOptions fromInt(int value) {
		switch(value) {
		case 0:
			return Go_Back;
		case 1:
			return View_Account_Applications;
		case 2:
			return Accept_Account_Applications;
		}
		return null;
	}
}
