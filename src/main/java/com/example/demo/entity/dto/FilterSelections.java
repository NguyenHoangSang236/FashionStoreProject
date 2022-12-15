package com.example.demo.entity.dto;

public class FilterSelections {
	String[] catalogs;
	String brand;
	String priceRange;
	double price1, price2;
	
	
	public FilterSelections() {}
	
	public FilterSelections(String[] catalogs, String brand, String priceRange) {
		super();
		this.catalogs = catalogs;
		this.brand = brand;
		this.priceRange = priceRange;
	}
	
	
	public void setPriceRangeLimits() {
		if(this.priceRange != null) {
			if(this.priceRange.equals("10000000++")) {
				int plusPos = this.priceRange.indexOf("++");
				this.price1 = Double.parseDouble(this.priceRange.substring(0, plusPos));
				this.price2 = 500000000;
				
				System.out.println(price1 + " " + price2);
			}
			else {
				int dashPos = this.priceRange.indexOf("-");
				
				this.price1 = Double.parseDouble(this.priceRange.substring(0, dashPos));
				this.price2 = Double.parseDouble(this.priceRange.substring(dashPos + 1));
				
				System.out.println(price1 + " " + price2);
			}
		}
		else {
			this.price1 = 0;
			this.price2 = 0;
		}
	}
	

	public String[] getCatalogs() {
		return catalogs;
	}

	public void setCatalogs(String[] catalogs) {
		this.catalogs = catalogs;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public double getPrice1() {
		return price1;
	}

	public void setPrice1(double price1) {
		this.price1 = price1;
	}

	public double getPrice2() {
		return price2;
	}

	public void setPrice2(double price2) {
		this.price2 = price2;
	}

	public String getPriceRange() {
		return priceRange;
	}

	public void setPriceRange(String priceRange) {
		this.priceRange = priceRange;
	}
}
