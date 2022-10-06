package com.example.demo.entity;

import java.sql.Date;

public class Staff {
	String id;
	String name;
	Date birthDate;
	String hometown;
	String position;
	String email;
	String phoneNumber;
	String image;
	
	
	public Staff(String id, String name, Date birthDate, String hometown, String position, String email, String phoneNumber, String image) {
		super();
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
		this.hometown = hometown;
		this.position = position;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.image = image;
	}

	public Staff() {}
	

	public String getImageBase64() {
		return image;
	}

	public void setImageBase64(String image) {
		this.image = image;
	}	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getHometown() {
		return hometown;
	}
	public void setHometown(String hometown) {
		this.hometown = hometown;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
}

