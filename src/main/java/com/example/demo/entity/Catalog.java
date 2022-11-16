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

import com.example.demo.util.ValueRender;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "catalog")
public class Catalog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	private int id;
	
	@Column(name = "Name")
	private String name;
	
	@ManyToMany
	@JoinTable(
			name = "catalog_with_products", 
			joinColumns = @JoinColumn(name = "Catalog_ID"), 
			inverseJoinColumns = {
				@JoinColumn(name = "Product_Name")})
	private List<Product> products;
	
	public String catalogNameToString() {
	    return ValueRender.linkToString(this.name);
	}

	
	public Catalog() {}
	
    public Catalog(int id, String name, List<Product> products) {
        super();
        this.id = id;
        this.name = name;
        this.products = products;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public List<Product> getProducts() {
        return products;
    }


    public void setProducts(List<Product> products) {
        this.products = products;
    }
	
	
	
}
