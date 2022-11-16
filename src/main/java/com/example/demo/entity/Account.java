package com.example.demo.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "login_accounts")
@DynamicInsert
@DynamicUpdate
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	int id;
	
	@Column(name = "User_Name")
	String userName;
	
	@Column(name = "Password")
	String password;
	
	@Column(name = "Role")
	String role;
	
	@OneToOne(mappedBy = "account")
    private Customer customer;
	
	@OneToOne(mappedBy = "account")
    private Staff staff;
	
	public Account() {}
	
	public Account(int id, String name, String pass, String role) {
		this.id = id;
		this.userName = name;
		this.password = pass;
		this.role = role;
	}
	
	public Account(String name, String pass) {
		this.userName = name;
		this.password = pass;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }
	
	
	
}

