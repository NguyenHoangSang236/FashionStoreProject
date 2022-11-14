package com.example.demo.entity.dto;

import java.util.List;

import com.example.demo.entity.Cart;
import com.example.demo.util.ValueRender;

public class CustomerCart {
    List<Cart> fullCartList;
    List<Cart> selectedCartList;
    double subtotal;
    double total;
    
    
    public CustomerCart() {}
    
    public CustomerCart(List<Cart> fullCartList, List<Cart> selectedCartList, double subtotal, double total) {
        super();
        this.fullCartList = fullCartList;
        this.selectedCartList = selectedCartList;
        this.subtotal = subtotal;
        this.total = total;
    }
    
    
    public double cartSubtotal() {
        double result = 0;
        
        for(int i = 0; i < this.selectedCartList.size(); i++) {
            result += this.selectedCartList.get(i).totalPrice();
        }
        
        this.subtotal = result;
        
        return this.subtotal;
    }
    
    public double cartTotal() {
        double result = 0;
        
        for(int i = 0; i < this.fullCartList.size(); i++) {
            result += this.fullCartList.get(i).totalPrice();
        }
        
        this.total = result;
        
        return this.total;
    }
    
    public String formatedCartSubtotal() {
        return ValueRender.formatDoubleNumber(this.subtotal);
    }
    
    public String formatedCartTotal() {
        return ValueRender.formatDoubleNumber(this.total);
    }
    

    
    public List<Cart> getFullCartList() {
        return fullCartList;
    }

    public void setFullCartList(List<Cart> fullCartList) {
        this.fullCartList = fullCartList;
    }

    public List<Cart> getSelectedCartList() {
        return selectedCartList;
    }

    public void setSelectedCartList(List<Cart> selectedCartList) {
        this.selectedCartList = selectedCartList;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
