package com.example.demo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "products_management")
@Entity
@DynamicInsert
@DynamicUpdate
public class ProductManagement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    
    @Column(name = "import_date")
    private Date importDate;
    
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "import_quantity")
    int importQuantity;
    
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "out_of_stock_date", nullable = true)
    private Date outOfStockDate;
    
    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

    
    public ProductManagement() {}
    
    public ProductManagement(int id, Date importDate, int importQuantity, Date outOfStockDate, Product product) {
        super();
        this.id = id;
        this.importDate = importDate;
        this.importQuantity = importQuantity;
        this.outOfStockDate = outOfStockDate;
        this.product = product;
    }
    
    public ProductManagement(Date importDate, int importQuantity, Product product) {
		super();
		this.importDate = importDate;
		this.importQuantity = importQuantity;
		this.product = product;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getImportDate() {
        return importDate;
    }

    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

    public int getImportQuantity() {
        return importQuantity;
    }

    public void setImportQuantity(int importQuantity) {
        this.importQuantity = importQuantity;
    }

    public Date getOutOfStockDate() {
        return outOfStockDate;
    }

    public void setOutOfStockDate(Date outOfStockDate) {
        this.outOfStockDate = outOfStockDate;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
