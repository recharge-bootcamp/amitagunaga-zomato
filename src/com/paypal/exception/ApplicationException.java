package com.paypal.exception;

public class ApplicationException extends Exception {
	public ApplicationException() {
		
	}
	
	public ApplicationException(String message) {
		super(message);
	}
	
	public ApplicationException(Throwable cause) {
		super(cause);
	}
	
	public ApplicationException(String message, Throwable error) {
		super(message, error);
	}
}
