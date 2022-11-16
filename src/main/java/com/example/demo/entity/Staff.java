package com.example.demo.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "staffs")
@DynamicInsert
@DynamicUpdate
public class Staff {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	int id;
	
	@Column(name = "Name")
	String name;
	
	@Column(name = "Birth_Date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
	Date birthDate;
	
	@Column(name = "Hometown")
	String hometown;
	
	@Column(name = "Position")
	String position;
	
	@Column(name = "Email")
	String email;
	
	@Column(name = "Phone_Number")
	String phoneNumber;
	
	@Column(name = "Avatar", columnDefinition = "text")
	String image;
	
	@OneToOne
    @JoinColumn(name = "Account_ID", referencedColumnName = "id")
    private Account account;
	
	@OneToMany(mappedBy = "staff")
	private List<Delivery> deliveries;
	
	
	public Staff(int id, String name, Date birthDate, String hometown, String position, String email, String phoneNumber, String image) {
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

