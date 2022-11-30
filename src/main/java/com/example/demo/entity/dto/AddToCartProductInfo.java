package com.example.demo.entity.dto;

public class AddToCartProductInfo {
	String productName;
	String productColor;
	String productSize;
	int quantity;
	
	
	
	public AddToCartProductInfo() {}
	
	public AddToCartProductInfo(String productName, String productColor, String productSize, int quantity) {
		super();
		this.productName = productName;
		this.productColor = productColor;
		this.productSize = productSize;
		this.quantity = quantity;
	}
	
	

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductColor() {
		return productColor;
	}

	public void setProductColor(String productColor) {
		this.productColor = productColor;
	}

	public String getProductSize() {
		return productSize;
	}

	public void setProductSize(String productSize) {
		this.productSize = productSize;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
