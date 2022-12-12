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
public class Delivery implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "Delivery_Date")
	Date deliveryDate;
	
	@Column(name = "Current_Status")
	String currentStatus;
	
	@Column(name = "Additional_shipper_comment")
	String additionalShipperComment;
	
	@Column(name = "Evidence_image")
	String evidenceImage;
	
	@ManyToOne
	@JoinColumn(name = "shipper_id")
	Staff staff;
	
	@Id
	@OneToOne
	@JoinColumn(name = "Invoice_ID", referencedColumnName = "ID")
	private Invoice invoice;
	
	
	public Delivery() {}

	public Delivery(Date deliveryDate, String currentStatus, String additionalShipperComment, String evidenceImage,
			Staff staff, Invoice invoice) {
		super();
		this.deliveryDate = deliveryDate;
		this.currentStatus = currentStatus;
		this.additionalShipperComment = additionalShipperComment;
		this.evidenceImage = evidenceImage;
		this.staff = staff;
		this.invoice = invoice;
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

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public String getAdditionalShipperComment() {
		return additionalShipperComment;
	}

	public void setAdditionalShipperComment(String additionalShipperComment) {
		this.additionalShipperComment = additionalShipperComment;
	}

	public String getEvidenceImage() {
		return evidenceImage;
	}

	public void setEvidenceImage(String evidenceImage) {
		this.evidenceImage = evidenceImage;
	}
}
