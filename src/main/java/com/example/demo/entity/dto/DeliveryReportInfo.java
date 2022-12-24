package com.example.demo.entity.dto;

import java.util.List;

import com.example.demo.entity.Delivery;
import com.example.demo.entity.Invoice;

public class DeliveryReportInfo {
	List<InvoicesWithProducts> invoiceProductsList;
	Delivery delivery;
	Invoice invoice;
	
	
	
	public DeliveryReportInfo() {}
	
	public DeliveryReportInfo(List<InvoicesWithProducts> invoiceProductsList, Delivery delivery, Invoice invoice) {
		super();
		this.invoiceProductsList = invoiceProductsList;
		this.delivery = delivery;
		this.invoice = invoice;
	}

	
	
	public List<InvoicesWithProducts> getInvoiceProductsList() {
		return invoiceProductsList;
	}

	public void setInvoiceProductsList(List<InvoicesWithProducts> invoiceProductsList) {
		this.invoiceProductsList = invoiceProductsList;
	}

	public Delivery getDelivery() {
		return delivery;
	}

	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
}
