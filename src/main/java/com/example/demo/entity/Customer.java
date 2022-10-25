package com.example.demo.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "customers")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	String id;
	
	@Column(name = "Name")
	String name;
	
	@Column(name = "Email")
	String email;
	
	@Column(name = "Phone_Number")
	String phoneNumber;
	
	@Column(name = "Avatar")
	String image;
	
	@OneToOne
    @JoinColumn(name = "Account_ID", referencedColumnName = "id")
    private Account account;
	
	@OneToMany(mappedBy = "customer")
	private List<Invoice> invoices;
	
	@OneToMany(mappedBy = "customer")
	private List<Cart> carts;
	
	@OneToMany(mappedBy = "customer")
	private List<Comment> comments;

	public Customer() {}	
}
