package com.project.boot.sort;

import java.util.Comparator;

import com.project.boot.entity.Product;

public class SortByName implements Comparator<Product> {

	@Override
	public int compare(Product p1, Product p2) {

		return (p1.getProductName()).compareTo(p2.getProductName());

	}

}
