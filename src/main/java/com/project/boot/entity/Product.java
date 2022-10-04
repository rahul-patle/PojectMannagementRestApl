package com.project.boot.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

    @Entity
    public class Product {

	@Id
	private String productId;
	@NotNull(message = "Product name is required")
	private String productName;
	@Min(1)
	private int productQuantity;
	@Min(1)
	private double productPrice;
	@NotNull(message = "Product type is required")
	private String productType;

	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Product(String productId, String productName, int productQuantity, double productPrice, String productType) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productQuantity = productQuantity;
		this.productPrice = productPrice;
		this.productType = productType;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productName=" + productName + ", productQuantity="
				+ productQuantity + ", productPrice=" + productPrice + ", productType=" + productType + "]";
	}

}