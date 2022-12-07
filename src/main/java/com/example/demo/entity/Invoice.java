package com.example.demo.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.example.demo.entity.dto.InvoicesWithProducts;
import com.example.demo.util.ValueRender;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Getter
@Setter
@Table(name = "invoice")
public class Invoice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	int id;
	
	@Column(name = "Invoice_Date")
	Date invoiceDate;
	
	@Column(name = "Delivery_Status")
	int deliveryStatus;
	
	@Column(name = "Payment_Status")
	int paymentStatus;
	
	@Column(name = "payment_method")
	String paymentMethod;
	
	@Column(name = "currency")
    String currency;
	
	@Column(name = "intent")
    String intent;
	
	@Column(name = "description")
    String description;
	
	@Column(name = "refund_percentage")
	double refundPercentage;
	
	@Column(name = "total_price")
	double totalPrice;
	
	@Column(name = "reason")
	String reason;
	
	@Column(name = "admin_acceptance")
	String adminAcceptance;
	
	@OneToOne(mappedBy = "invoice")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Delivery delivery;
	
	@OneToMany(mappedBy = "invoice", cascade={CascadeType.ALL})
	List<InvoicesWithProducts> invoicesWithProducts;
	
	@ManyToOne
	@MapsId("customer_id")
	private Customer customer;
	

	public Invoice() {}
    
    public Invoice(int id, Customer customer, Date invoiceDate) {
		super();
		this.id = id;
		this.invoiceDate = invoiceDate;
		this.customer = customer;
	}

	public Invoice(int id, Date invoiceDate, int deliveryStatus, int paymentStatus, String paymentMethod, String currency,
            String intent, String description, double refundPercentage, String reason, Delivery delivery,
            List<InvoicesWithProducts> invoicesWithProducts, Customer customer, String adminAcceptance) {
        super();
        this.id = id;
        this.invoiceDate = invoiceDate;
        this.deliveryStatus = deliveryStatus;
        this.paymentStatus = paymentStatus;
        this.paymentMethod = paymentMethod;
        this.currency = currency;
        this.intent = intent;
        this.description = description;
        this.refundPercentage = refundPercentage;
        this.reason = reason;
        this.delivery = delivery;
        this.invoicesWithProducts = invoicesWithProducts;
        this.customer = customer;
        this.adminAcceptance = adminAcceptance;
    }
	
    public Invoice(int id,Date invoiceDate, int deliveryStatus, int paymentStatus, String paymentMethod, String currency,
			String intent, String description, Customer customer, double totalPrice, String adminAcceptance) {
		super();
        this.id = id;
		this.invoiceDate = invoiceDate;
		this.deliveryStatus = deliveryStatus;
		this.paymentStatus = paymentStatus;
		this.paymentMethod = paymentMethod;
		this.currency = currency;
		this.intent = intent;
		this.description = description;
		this.customer = customer;
		this.totalPrice = totalPrice;
        this.adminAcceptance = adminAcceptance;
	}
    
    

	public double totalPrice() {
        double result = 0;
        
        for(int i = 0; i < this.invoicesWithProducts.size(); i++) {
            int quantity = this.invoicesWithProducts.get(i).getQuantity();
            double productPrice = this.invoicesWithProducts.get(i).getProduct().getPrice();
            result += quantity * productPrice;
        }
        
        return result;
    }
	
	public String formattedInvoiceDate() {
		return ValueRender.formatDateDMY(this.invoiceDate);
	}
    


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public Date getInvoiceDate() {
        return invoiceDate;
    }


    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }


    public int getDeliveryStatus() {
        return deliveryStatus;
    }


    public void setDeliveryStatus(int deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }


    public int getPaymentStatus() {
        return paymentStatus;
    }


    public void setPaymentStatus(int paymentStatus) {
        this.paymentStatus = paymentStatus;
    }


    public String getPaymentMethod() {
        return paymentMethod;
    }


    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }


    public String getCurrency() {
        return currency;
    }


    public void setCurrency(String currency) {
        this.currency = currency;
    }


    public double getRefundPercentage() {
        return refundPercentage;
    }


    public void setRefundPercentage(double refundPercentage) {
        this.refundPercentage = refundPercentage;
    }


    public String getReason() {
        return reason;
    }


    public void setReason(String reason) {
        this.reason = reason;
    }


    public Delivery getDelivery() {
        return delivery;
    }


    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }


    public List<InvoicesWithProducts> getInvoicesWithProducts() {
        return invoicesWithProducts;
    }


    public void setInvoicesWithProducts(List<InvoicesWithProducts> invoicesWithProducts) {
        this.invoicesWithProducts = invoicesWithProducts;
    }


    public Customer getCustomer() {
        return customer;
    }


    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


    public String getIntent() {
        return intent;
    }


    public void setIntent(String intent) {
        this.intent = intent;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getAdminAcceptance() {
		return adminAcceptance;
	}

	public void setAdminAcceptance(String adminAcceptance) {
		this.adminAcceptance = adminAcceptance;
	}
}
