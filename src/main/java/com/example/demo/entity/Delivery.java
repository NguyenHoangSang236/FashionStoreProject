package com.example.demo.entity;

import java.sql.Date;

public class Delivery {
	String invoiceId;
	String shipperId;
	Date deliveryDate;
	String currentStatus;
	
	
	public Delivery(String invoiceId, String shipperId, Date deliveryDate, String currentStatus) {
		super();
		this.invoiceId = invoiceId;
		this.shipperId = shipperId;
		this.deliveryDate = deliveryDate;
		this.currentStatus = currentStatus;
	}
	
	public Delivery() {}
	
	
	public String getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}
	public String getShipperId() {
		return shipperId;
	}
	public void setShipperId(String shipperId) {
		this.shipperId = shipperId;
	}
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public String getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
	
	
	
}
