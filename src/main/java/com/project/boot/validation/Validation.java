package com.project.boot.validation;

import com.project.boot.entity.Product;

public class Validation {

	public static boolean vaildationProduct(Product product) {
		
		if (product.getProductId().equals("") || product.getProductId() == null)
			return false;

		else if (product.getProductName().equals("") || product.getProductName() == null)
			return false;
		else if (product.getProductPrice() <= 0)
			return false;

		else if (product.getProductQuantity() <= 0)

			return false;
		else if (product.getProductType().equals("") || product.getProductType() == null)

			return false;
		return true;

	}

}
