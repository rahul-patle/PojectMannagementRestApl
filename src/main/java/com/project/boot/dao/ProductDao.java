package com.project.boot.dao;

import java.util.List;

import com.project.boot.entity.Product;

public interface ProductDao {
	// input is given by client
	public boolean saveProduct(Product product);

	public List<Product> getAllProduct();

	public Product getByProductId(String productId);

	public boolean updateProduct(Product product);

	public boolean deleteProductById(String productId);

	public List<Product> sortProducts(String sortByAny);

	public List<Product> sortProducts_Desc(String sortByAny);

	public Product maxPriceProduct();

	public double countSumOfPriceProduct();

	public long gettotalCountProduct();

	public int  saveProductList (List<Product>list);

}
