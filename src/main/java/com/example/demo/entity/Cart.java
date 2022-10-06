package com.example.demo.entity;

import java.util.ArrayList;

public class Cart {
	String customerId;
	int quantity;
	ArrayList<Product> productsList;
	

	public ArrayList<Product> getProductsList() {
		return productsList;
	}


	public void setProductsList(ArrayList<Product> productsList) {
		this.productsList = productsList;
	}


	public Cart(String customerId, int quantity, ArrayList<Product> productsList) {
		super();
		this.customerId = customerId;
		this.quantity = quantity;
		this.productsList = productsList;
	}


	public Cart() {	}


	
	public String getCustomerId() {
		return customerId;
	}


	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
