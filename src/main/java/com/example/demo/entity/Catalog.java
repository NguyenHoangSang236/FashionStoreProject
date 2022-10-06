package com.example.demo.entity;

import java.util.ArrayList;

public class Catalog {
	int code;
	String name;
	ArrayList<Product> productsList;
	
	
	public Catalog(int code, String name, ArrayList<Product> productsList) {
		this.code = code;
		this.name = name;
		this.productsList = productsList;
	}

	public ArrayList<Product> getProductsList() {
		return productsList;
	}

	public void setProductsList(ArrayList<Product> productsList) {
		this.productsList = productsList;
	}

	public Catalog() {}

	public int getCode() {
		return code;
	}
	

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
