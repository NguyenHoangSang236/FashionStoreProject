package com.example.demo.entity.embededkey;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProductPrimaryKey implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2696916899223821930L;

	@Column(name = "ID", nullable = false)
    private int id;

	@Column(name = "Color", nullable = false)
    private String color;
	
	@Column(name = "Size", nullable = false)
    private String size;
    
    public ProductPrimaryKey() {}	
}
