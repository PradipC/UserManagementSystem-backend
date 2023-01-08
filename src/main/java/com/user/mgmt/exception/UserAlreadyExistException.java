package com.user.mgmt.exception;

public class UserAlreadyExistException extends RuntimeException{
	
	public UserAlreadyExistException(String message) {
		super(message);
	}

}
