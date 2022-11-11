package com.example.demo.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

@Entity
@Data
@Table(name = "cart")
@DynamicInsert
@DynamicUpdate
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	String id;
	
	@Column(name = "Quantity")
	int quantity;
	
	@Column(name = "Buying_Status")
	int buyingStatus;
	
	@ManyToOne
    @JoinColumn(name = "customerId")
    Customer customer;

    @ManyToOne
    @JoinColumn(name = "productId")
    Product product;
}
