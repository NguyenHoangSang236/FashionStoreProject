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

import lombok.Data;

@Data
@Table(name = "product_management")
@Entity
public class ProductManagement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    
    @Column(name = "product_id", updatable = false, insertable = false)
    private int productId;
    
    @Column(name = "import_date")
    private Date importDate;
    
    @Column(name = "import_quantity")
    int importQuantity;
    
    @Column(name = "out_of_stock_date", nullable = true)
    private Date outOfStockDate;
    
    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

    
    public ProductManagement() {}
    
    public ProductManagement(int id, int productId, Date importDate, int importQuantity, Date outOfStockDate,
            Product product) {
        super();
        this.id = id;
        this.productId = productId;
        this.importDate = importDate;
        this.importQuantity = importQuantity;
        this.outOfStockDate = outOfStockDate;
        this.product = product;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
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
