package com.project.boot.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.project.boot.entity.Product;

public interface ProductService {

	public boolean saveProduct(Product product);

	public List<Product> getAllProduct();

	public Product getByProductId(String productId);

	public boolean updateProduct(Product product);

	public boolean deleteProductById(String productId);

	public List<Product> sortProducts(String sortByAny);

	public List<Product> sortProducts_Desc(String sortByAny);

	public double countSumOfPriceProduct();

	public long gettotalCountProduct();

	public Product maxPriceProduct();

	public int uploadSheet(CommonsMultipartFile file, HttpSession httpsession); // read input from excel and write in
																				// database
}
