package com.banking.bo.types;

public enum TransactionType {
	Deposit, Withdrawal;
	
	public static TransactionType fromInt(int value) {
		switch(value) {
		case 0:
			return Deposit;
		case 1:
			return Withdrawal;
		}
		return null;
	}
}
