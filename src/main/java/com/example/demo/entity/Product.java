package com.example.demo.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private int id;

    @Column(name = "Color", nullable = false)
    private String color;
    
    @Column(name = "Size", nullable = false)
    private String size;
	
	@Column(name = "Name")
	String name;
	
	@Column(name = "Price")
	double price;
	
	@Column(name = "Available_Quantity")
	int available_quantity;
	
	@Column(name = "Sold_Quantity")
	int soldQuantity;
	
	@Column(name = "One_star_quantity")
	int oneStarQuantity;
	
	@Column(name = "Two_star_quantity")
	int twoStarQuantity;
	
	@Column(name = "Three_star_quantity")
	int threeStarQuantity;
	
	@Column(name = "Four_star_quantity")
	int fourStarQuantity;
	
	@Column(name = "Five_star_quantity")
	int fiveStarQuantity;
	
	@Column(name = "Discount")
	double discount;
	
	@Column(name = "Brand")
	String brand;

	@ManyToOne
	Invoice invoice;
	
	@ManyToMany(mappedBy = "products")
	private List<Catalog> catalogs;
	
	@OneToMany(mappedBy = "product")
	private List<Cart> carts;
	
	@OneToMany(mappedBy = "product")
	private List<Comment> comments;
	
	@ManyToOne
	@JoinColumn(name = "Name", referencedColumnName = "Product_Name", insertable = false, updatable = false)
	Product_Different_Images productDifferentImages;
	

	public Product() {}


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getColor() {
        return color;
    }


    public void setColor(String color) {
        this.color = color;
    }


    public String getSize() {
        return size;
    }


    public void setSize(String size) {
        this.size = size;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
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


    public int getSoldQuantity() {
        return soldQuantity;
    }


    public void setSoldQuantity(int soldQuantity) {
        this.soldQuantity = soldQuantity;
    }


    public int getOneStarQuantity() {
        return oneStarQuantity;
    }


    public void setOneStarQuantity(int oneStarQuantity) {
        this.oneStarQuantity = oneStarQuantity;
    }


    public int getTwoStarQuantity() {
        return twoStarQuantity;
    }


    public void setTwoStarQuantity(int twoStarQuantity) {
        this.twoStarQuantity = twoStarQuantity;
    }


    public int getThreeStarQuantity() {
        return threeStarQuantity;
    }


    public void setThreeStarQuantity(int threeStarQuantity) {
        this.threeStarQuantity = threeStarQuantity;
    }


    public int getFourStarQuantity() {
        return fourStarQuantity;
    }


    public void setFourStarQuantity(int fourStarQuantity) {
        this.fourStarQuantity = fourStarQuantity;
    }


    public int getFiveStarQuantity() {
        return fiveStarQuantity;
    }


    public void setFiveStarQuantity(int fiveStarQuantity) {
        this.fiveStarQuantity = fiveStarQuantity;
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


    public List<Catalog> getCatalogs() {
        return catalogs;
    }


    public void setCatalogs(List<Catalog> catalogs) {
        this.catalogs = catalogs;
    }


    public List<Cart> getCarts() {
        return carts;
    }


    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }


    public List<Comment> getComments() {
        return comments;
    }


    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }


    public Product_Different_Images getProductDifferentImages() {
        return productDifferentImages;
    }


    public void setProductDifferentImages(Product_Different_Images productDifferentImages) {
        this.productDifferentImages = productDifferentImages;
    }
	
	
	
}

