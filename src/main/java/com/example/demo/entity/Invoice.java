package com.example.demo.entity;

import java.sql.Date;

public class Invoice {
	String invoiceCode;
	String customerId;
	Date invoiceDate;
	String productCode;
	int quantity;
	String size;
	String consultantId;
	
	
	public Invoice(String invoiceCode, String customerId, Date invoiceDate, String productCode, int quantity,
			String size, String consultantId) {
		super();
		this.invoiceCode = invoiceCode;
		this.customerId = customerId;
		this.invoiceDate = invoiceDate;
		this.productCode = productCode;
		this.quantity = quantity;
		this.size = size;
		this.consultantId = consultantId;
	}

	public Invoice() {}
	

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getConsultantId() {
		return consultantId;
	}

	public void setConsultantId(String consultantId) {
		this.consultantId = consultantId;
	}
	
	
}
