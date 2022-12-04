package com.example.demo.entity.dto;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.example.demo.entity.Invoice;
import com.example.demo.entity.Product;
import com.example.demo.entity.embededkey.InvoicesWithProductsPrimaryKeys;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@Table(name = "invoices_with_products")
public class InvoicesWithProducts {
    @EmbeddedId
    InvoicesWithProductsPrimaryKeys id;
    
    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    Product product;
    
    @ManyToOne
    @MapsId("invoiceId")
    @JoinColumn(name = "invoice_id")
    Invoice invoice;
    
    @Column(name = "quantity")
    int quantity;

    
    public InvoicesWithProducts() {}
    
    public InvoicesWithProducts(InvoicesWithProductsPrimaryKeys id, Product product, Invoice invoice, int quantity) {
        super();
        this.id = id;
        this.product = product;
        this.invoice = invoice;
        this.quantity = quantity;
    }

    public InvoicesWithProductsPrimaryKeys getId() {
        return id;
    }

    public void setId(InvoicesWithProductsPrimaryKeys id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
