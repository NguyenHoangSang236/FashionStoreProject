package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.example.demo.entity.keyClasses.ProductPk;

import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product {
	@EmbeddedId
	private ProductPk productPk;
	
	@Column(name = "Name")
	String name;
	
	@Column(name = "Price")
	double price;
	
	@Column(name = "Available_Quantity")
	int available_quantity;
	
	@Column(name = "Sold_Quantity")
	int sold_quantity;
	
	@Column(name = "One_star_quantity")
	int one_star_quantity;
	
	@Column(name = "Two_star_quantity")
	int two_star_quantity;
	
	@Column(name = "Three_star_quantity")
	int three_star_quantity;
	
	@Column(name = "Four_star_quantity")
	int four_star_quantity;
	
	@Column(name = "Five_star_quantity")
	int five_star_quantity;
	
	@Column(name = "Discount")
	double discount;
	
	@Column(name = "Brand")
	String brand;

	@ManyToOne
	@MapsId("productId")
	Invoice invoice;
	
	@ManyToMany(mappedBy = "products")
	@MapsId("productId")
	private List<Catalog> catalogs;
	
	@OneToMany(mappedBy = "product")
	@MapsId("productId")
	private List<Cart> carts;
	
	@OneToMany(mappedBy = "product")
	@MapsId("productId")
	private List<Comment> comments;
	

	public Product() {}


	public ProductPk getProductPk() {
		return productPk;
	}


	public void setProductPk(ProductPk productPk) {
		this.productPk = productPk;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public int getAvailable_quantity() {
		return available_quantity;
	}


	public void setAvailable_quantity(int available_quantity) {
		this.available_quantity = available_quantity;
	}


	public int getSold_quantity() {
		return sold_quantity;
	}


	public void setSold_quantity(int sold_quantity) {
		this.sold_quantity = sold_quantity;
	}


	public int getOne_star_quantity() {
		return one_star_quantity;
	}


	public void setOne_star_quantity(int one_star_quantity) {
		this.one_star_quantity = one_star_quantity;
	}


	public int getTwo_star_quantity() {
		return two_star_quantity;
	}


	public void setTwo_star_quantity(int two_star_quantity) {
		this.two_star_quantity = two_star_quantity;
	}


	public int getThree_star_quantity() {
		return three_star_quantity;
	}


	public void setThree_star_quantity(int three_star_quantity) {
		this.three_star_quantity = three_star_quantity;
	}


	public int getFour_star_quantity() {
		return four_star_quantity;
	}


	public void setFour_star_quantity(int four_star_quantity) {
		this.four_star_quantity = four_star_quantity;
	}


	public int getFive_star_quantity() {
		return five_star_quantity;
	}


	public void setFive_star_quantity(int five_star_quantity) {
		this.five_star_quantity = five_star_quantity;
	}


	public double getDiscount() {
		return discount;
	}


	public void setDiscount(double discount) {
		this.discount = discount;
	}


	public String getBrand() {
		return brand;
	}


	public void setBrand(String brand) {
		this.brand = brand;
	}


	public Invoice getInvoice() {
		return invoice;
	}


	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	
}

