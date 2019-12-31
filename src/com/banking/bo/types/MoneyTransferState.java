package com.banking.bo.types;

public enum MoneyTransferState {
	Accepted, Denied;
	
	public static MoneyTransferState fromInt(int value) {
		switch(value) {
		case 0:
			return Accepted;
		case 1:
			return Denied;
		}
		return null;
	}
}
