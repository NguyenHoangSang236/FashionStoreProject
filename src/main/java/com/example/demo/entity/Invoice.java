package com.example.demo.entity;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

@Entity
@Data
@Table(name = "invoice")
@DynamicInsert
@DynamicUpdate
public class Invoice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	int id;
	
	@Column(name = "Invoice_Date")
	Date invoiceDate;
	
	@Column(name = "Quantity")
	int quantity;
	
	@Column(name = "Size")
	String size;
	
	@Column(name = "Payment_Status")
	int paymentStatus;
	
	@OneToOne(mappedBy = "invoice")
	private Delivery delivery;
	
	@ManyToMany
	@JoinTable(
            name = "invoices_with_products", 
            joinColumns = @JoinColumn(name = "Invoice_ID"), 
            inverseJoinColumns = {
                @JoinColumn(name = "Product_ID")})
	private List<Product> products;
	
	@ManyToOne()
	@MapsId("Customer_ID")
	private Customer customer;
	

	public Invoice() {}
}
