package com.project.boot.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.project.boot.entity.Product;
import com.project.boot.exceptions.ProductAlreadyExistException;
import com.project.boot.exceptions.ProductNotFoundException;
import com.project.boot.service.ProductService;

@RestController
public class ProductController {

	@Autowired
	private ProductService service;

	@PostMapping(value = "/saveProduct")
	public ResponseEntity<Boolean> saveProduct(@Valid @RequestBody Product product) throws Exception {
		boolean isAdded = service.saveProduct(product);

		if (isAdded) {
			// type must be wrapper class
			throw new ProductAlreadyExistException("product already exist ");
		} else {
			return new ResponseEntity<Boolean>(isAdded, HttpStatus.CREATED);
		}
	}

	@GetMapping(value = "/getAllProduct")
	public ResponseEntity<List<Product>> getAllProduct() {
		// directly call by ctrl+shft+L
		List<Product> allProduct = service.getAllProduct();
		if (!allProduct.isEmpty()) {
			return new ResponseEntity<List<Product>>(allProduct, HttpStatus.OK);
		} else {
			throw new ProductNotFoundException("product not found");
		}
	}

	@GetMapping(value = "/getByProductId")
	public ResponseEntity<Product> getByProductId(@RequestParam String productId) {
		Product product = service.getByProductId(productId);
		if (product != null) {
			return new ResponseEntity<Product>(product, HttpStatus.OK);
		} else {
			throw new ProductNotFoundException("product not found for Id:" + productId);
		}
	}

	@PutMapping(value = "/updateProduct")
	public ResponseEntity<Boolean> updateProduct(@RequestBody Product product) {
		boolean isUpdated = service.updateProduct(product);
		if (product != null) {
			return new ResponseEntity<Boolean>(isUpdated, HttpStatus.OK);
		} else {
			throw new ProductNotFoundException("Product not found");
		}
	}

	@DeleteMapping(value = "/deleteProductById")
	public ResponseEntity<Boolean> deleteProductById(@RequestParam String productId) {
		boolean isDeleted = service.deleteProductById(productId);

		if (isDeleted) {
			return new ResponseEntity<Boolean>(isDeleted, HttpStatus.OK);
		} else {
			throw new ProductNotFoundException("Product not found");
		}
	}

	@GetMapping(value = "/sortProducts_Asc")
	public ResponseEntity<List<Product>> sortProducts(@RequestParam String sortByAny) {
		List<Product> alllist = service.sortProducts(sortByAny);

		if (!alllist.isEmpty()) {
			return new ResponseEntity<List<Product>>(alllist, HttpStatus.OK);
		} else {
			throw new ProductNotFoundException("Product not found");
		}

	}

	@GetMapping(value = "/sortProducts_Desc")
	public ResponseEntity<List<Product>> sortProducts_Desc(@RequestParam String sortByAny) {
		List<Product> list = service.sortProducts_Desc(sortByAny);

		if (list != null) {
			return new ResponseEntity<List<Product>>(list, HttpStatus.OK);
		} else {
			throw new ProductNotFoundException("Product not found");
		}
	}

	@GetMapping(value = "/countSumOfPriceProduct")
	public ResponseEntity<Double> countSumOfPriceProduct() {
		Double sum = service.countSumOfPriceProduct();

		if (sum > 0) {
			return new ResponseEntity<Double>(sum, HttpStatus.OK);
		} else {
			throw new ProductNotFoundException("Product not found");
		}
	}

	@GetMapping(value = "/getTotalCountProduct")
	public ResponseEntity<Long> gettotalCountProduct() {
		long count = service.gettotalCountProduct();
		if (count > 0) {
			return new ResponseEntity<Long>(count, HttpStatus.OK);
		} else {
			throw new ProductNotFoundException("Product not found");
		}
	}

	@GetMapping(value = "/maxPriceProduct")
	public ResponseEntity<Product> maxPriceProduct() {
		Product product = service.maxPriceProduct();
		if (product != null) {
			return new ResponseEntity<Product>(product, HttpStatus.OK);
		} else {
			throw new ProductNotFoundException("Product not found");
		}

	}
	// to findout the path of webapp use serveltcontext

	// The servlet container uses this interface to create a session between
	// an HTTP client and an HTTP server.
	@PostMapping("/uploadSheets")
	public ResponseEntity<Integer> uploadSheets(
			@RequestParam CommonsMultipartFile file, HttpSession session) {

		int countOfProduct = service.uploadSheet(file, session);
		return new ResponseEntity<Integer>(countOfProduct, HttpStatus.OK);

	}
}
