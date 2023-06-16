package com.nv.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ErrorResponse> customExceptionHandler(CustomException ex){
		return new ResponseEntity<ErrorResponse>(new ErrorResponse(ex.getMessage(),false),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> otherExceptionHandler(Exception ex){
		return new ResponseEntity<ErrorResponse>(new ErrorResponse(ex.getMessage(),false),HttpStatus.BAD_REQUEST);
	}

}
