package com.banking.exception;

public class DatabaseException extends Exception {
	public DatabaseException() {
		super();
	}
	
	public DatabaseException(final String message) {
		super(message);
	}
}
