package com.example.demo.entity;

import java.util.ArrayList;

public class Product {
	String code;
	String name;
	String size;
	String color;
	double price;
	int available_quantity;
	int sold_quantity;
	int one_star_quantity;
	int two_star_quantity;
	int three_star_quantity;
	int four_star_quantity;
	int five_star_quantity;
	ArrayList<Comment> commentList;
	String[] imageList; 


	public Product(String code, String name, String size, String color, double price, int available_quantity, int sold_quantity,
			int one_star_quantity, int two_star_quantity, int three_star_quantity, int four_star_quantity,
			int five_star_quantity, String[] imageList, ArrayList<Comment> commentList) {
		super();
		this.code = code;
		this.name = name;
		this.size = size;
		this.color = color;
		this.price = price;
		this.available_quantity = available_quantity;
		this.sold_quantity = sold_quantity;
		this.one_star_quantity = one_star_quantity;
		this.two_star_quantity = two_star_quantity;
		this.three_star_quantity = three_star_quantity;
		this.four_star_quantity = four_star_quantity;
		this.five_star_quantity = five_star_quantity;
		this.imageList = imageList;
		this.commentList = commentList;
	}

	public Product() {}
	
	
	
	public String[] getImageList() {
		return imageList;
	}

	public void setImageList(String[] imageList) {
		this.imageList = imageList;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
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

	public int getSold_quantity() {
		return sold_quantity;
	}

	public void setSold_quantity(int sold_quantity) {
		this.sold_quantity = sold_quantity;
	}

	public int getOne_star_quantity() {
		return one_star_quantity;
	}

	public void setOne_star_quantity(int one_star_quantity) {
		this.one_star_quantity = one_star_quantity;
	}

	public int getTwo_star_quantity() {
		return two_star_quantity;
	}

	public void setTwo_star_quantity(int two_star_quantity) {
		this.two_star_quantity = two_star_quantity;
	}

	public int getThree_star_quantity() {
		return three_star_quantity;
	}

	public void setThree_star_quantity(int three_star_quantity) {
		this.three_star_quantity = three_star_quantity;
	}

	public int getFour_star_quantity() {
		return four_star_quantity;
	}

	public void setFour_star_quantity(int four_star_quantity) {
		this.four_star_quantity = four_star_quantity;
	}

	public int getFive_star_quantity() {
		return five_star_quantity;
	}

	public void setFive_star_quantity(int five_star_quantity) {
		this.five_star_quantity = five_star_quantity;
	}
	
	
	public ArrayList<Comment> getCommentList() {
		return commentList;
	}

	public void setCommentList(ArrayList<Comment> commentList) {
		this.commentList = commentList;
	}
}

