package com.example.demo.entity.dto;

public class ProductManagement {
    private int[] idList;
    private String newCatalogName;
    
    
    public ProductManagement() {}

	public ProductManagement(int[] idList, String newCatalogName) {
		super();
		this.idList = idList;
		this.newCatalogName = newCatalogName;
	}


	public int[] getIdList() {
		return idList;
	}


	public void setIdList(int[] idList) {
		this.idList = idList;
	}


	public String getNewCatalogName() {
		return newCatalogName;
	}


	public void setNewCatalogName(String newCatalogName) {
		this.newCatalogName = newCatalogName;
	}
}