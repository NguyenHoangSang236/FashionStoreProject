package com.example.demo.entity.keyclasses;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProductPk implements Serializable{
	@Column(name = "ID", nullable = false)
    private int id;

	@Column(name = "Color", nullable = false)
    private String color;
	
	@Column(name = "Size", nullable = false)
    private String size;
    
    public ProductPk() {}	
}
