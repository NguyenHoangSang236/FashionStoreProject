package com.example.demo.entity.dto;

public class ProductComment {
    String customerName;
    String content;
    String imageUrl;
    
    
    public ProductComment() {}

    public ProductComment(String customerName, String content, String imageUrl) {
        super();
        this.customerName = customerName;
        this.content = content;
        this.imageUrl = imageUrl;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
