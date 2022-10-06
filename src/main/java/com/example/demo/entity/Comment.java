package com.example.demo.entity;

public class Comment {
	int id;
	String productCode;
	String customerId;
	String content;
	
	
	public Comment(int id, String productCode, String customerId, String content) {
		super();
		this.id = id;
		this.productCode = productCode;
		this.customerId = customerId;
		this.content = content;
	}
	
	public Comment () {}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getProductCode() {
		return productCode;
	}


	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}


	public String getCustomerId() {
		return customerId;
	}


	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}
	
}
