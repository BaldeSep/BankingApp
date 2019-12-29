package com.banking.exception;

public class LibraryException extends Exception {
	public LibraryException() {
		this("Sorry There Was An Unexpected Error, Please Try Again Later");
	}

	public LibraryException(final String message) {
		super(message);
	}
	
}
