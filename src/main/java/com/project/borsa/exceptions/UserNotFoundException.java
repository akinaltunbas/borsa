package com.project.borsa.exceptions;

public class UserNotFoundException extends RuntimeException  {
	
	public UserNotFoundException() {
		super();
	}
	
	public UserNotFoundException(String message) {
		super(message);
	}

}
