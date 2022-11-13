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

import com.example.demo.util.ValueRender;

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
    @JoinColumn(name = "customer_id")
    Customer customer;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

    
    public Cart() {}
    
    public Cart(String id, int quantity, int buyingStatus, Customer customer, Product product) {
        super();
        this.id = id;
        this.quantity = quantity;
        this.buyingStatus = buyingStatus;
        this.customer = customer;
        this.product = product;
    }
    
    
    public double totalPrice() {
        double result = 0;
        
        int quantity = this.quantity;
        double productPrice = this.product.getPrice();
        result += quantity * productPrice;
        
        return result;
    }
    
    public String formatedTotalPrice() {
        return ValueRender.formatDoubleNumber(this.totalPrice());
    }
    
    public String formatedPrice() {
        return ValueRender.formatDoubleNumber(this.product.getPrice());
    }
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getBuyingStatus() {
        return buyingStatus;
    }

    public void setBuyingStatus(int buyingStatus) {
        this.buyingStatus = buyingStatus;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    
    
    
}
