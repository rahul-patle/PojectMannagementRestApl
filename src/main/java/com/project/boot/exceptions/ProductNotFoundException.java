package com.project.boot.exceptions;

public class ProductNotFoundException extends RuntimeException {

	public ProductNotFoundException(String message) {

		super(message); // super send message  Runtime 
	}

}
// at Restcontrolleradvice is global, due to this advantage is that we dont 
//need to make multiple Product not found exception