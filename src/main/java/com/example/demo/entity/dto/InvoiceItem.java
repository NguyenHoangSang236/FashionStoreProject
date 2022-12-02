package com.example.demo.entity.dto;

public class InvoiceItem {
	String productName;
	double productTotalPrice;
	
	
	public InvoiceItem() {}
	
	public InvoiceItem(String productName, double productTotalPrice) {
		super();
		this.productName = productName;
		this.productTotalPrice = productTotalPrice;
	}

	
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getProductTotalPrice() {
		return productTotalPrice;
	}

	public void setProductTotalPrice(double productTotalPrice) {
		this.productTotalPrice = productTotalPrice;
	}
}
