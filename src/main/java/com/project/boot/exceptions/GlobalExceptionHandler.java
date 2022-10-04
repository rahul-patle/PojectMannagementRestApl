package com.project.boot.exceptions;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


	// method argument not valid exception
	@ResponseStatus (code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public HashMap<String, Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {

		HashMap<String, Object> hashmap = new HashMap<>();
		hashmap.put("Time", new Date());
		BindingResult bindingResult = ex.getBindingResult();
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		fieldErrors.forEach((error) -> hashmap.put(error.getField(), error.getDefaultMessage()));

		return hashmap;
	}

	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<String> ProductNotFoundException(ProductNotFoundException ex) {

		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.OK);
		
	}
	@ExceptionHandler(ProductAlreadyExistException.class)
	public ResponseEntity<String> ProductAlreadyExistException(ProductAlreadyExistException ex) {

		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.OK);
		
	}
	
}
