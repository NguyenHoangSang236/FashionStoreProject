package com.example.demo.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "catalog")
public class Catalog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	int id;
	
	@Column(name = "Name")
	String name;
	
	@ManyToMany
	@JoinTable(
			name = "catalog_with_products", 
			joinColumns = @JoinColumn(name = "Catalog_ID"), 
			inverseJoinColumns = {
				@JoinColumn(name = "Product_Name")})
	List<Product> products;
}
