package com.example.demo.entity;

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

import lombok.Data;

@Entity
@Data
@Table(name = "customers")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	int id;
	
	@Column(name = "Name")
	String name;
	
	@Column(name = "Email")
	String email;
	
	@Column(name = "Phone_Number")
	String phoneNumber;
	
	@Column(name = "Avatar")
	String image;
	
	@OneToOne
    @JoinColumn(name = "Account_ID", referencedColumnName = "id")
    private Account account;
	
	@OneToMany(mappedBy = "customer")
	private List<Invoice> invoices;
	
	@OneToMany(mappedBy = "customer")
	private List<Cart> carts;
	
	@OneToMany(mappedBy = "customer")
	private List<Comment> comments;

	
	
	public Customer() {}

    public Customer(int id, String name, String email, String phoneNumber, String image, Account account,
            List<Invoice> invoices, List<Cart> carts, List<Comment> comments) {
        super();
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.image = image;
        this.account = account;
        this.invoices = invoices;
        this.carts = carts;
        this.comments = comments;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }	
}
