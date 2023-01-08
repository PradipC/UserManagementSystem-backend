package com.user.mgmt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	
	@ExceptionHandler(UserNotValidException.class)
	public ResponseEntity<ErrorResponse> catchUserNotValidException(UserNotValidException exception) {

	
		ErrorResponse response = new ErrorResponse();
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		response.setMessage(exception.getMessage());
		response.setTimeStamp(System.currentTimeMillis());
		
		System.out.println("return response is : "+response);
		
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		
	}

	
	
	@ExceptionHandler(UserAlreadyExistException.class)
	public ResponseEntity<ErrorResponse> catchUserAlreadyExistException(UserAlreadyExistException exception) {

	
		ErrorResponse response = new ErrorResponse();
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		response.setMessage(exception.getMessage());
		response.setTimeStamp(System.currentTimeMillis());
		
		System.out.println("return response is : "+response);
		
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		
	}	
}
