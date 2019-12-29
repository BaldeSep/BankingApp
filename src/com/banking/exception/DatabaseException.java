package com.banking.exception;

public class DatabaseException extends Exception {
	public DatabaseException() {
		this("There Was An Issue Getting Your Data Or Adding Your Data Sorry Try Again Later");
	}
	
	public DatabaseException(final String message) {
		super(message);
	}
}
