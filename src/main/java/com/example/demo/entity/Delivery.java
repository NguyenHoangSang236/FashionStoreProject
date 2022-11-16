package com.example.demo.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "delivery")
@DynamicInsert
@DynamicUpdate
public class Delivery implements Serializable{
	@Column(name = "Invoice_ID")
	int invoiceId;
	
	@Column(name = "Delivery_Date")
	Date deliveryDate;
	
	@Column(name = "Current_Status")
	int currentStatus;
	
	@ManyToOne
	@MapsId("Shipper_ID")
	Staff staff;
	
	@Id
	@OneToOne
	@JoinColumn(name = "Invoice_ID", referencedColumnName = "ID")
	private Invoice invoice;
	
	
	public Delivery() {}
}
