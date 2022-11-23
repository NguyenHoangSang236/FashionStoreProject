package com.example.demo.entity.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Cart;
import com.example.demo.respository.CartRepository;
import com.example.demo.util.ValueRender;

@Component
public class CustomerCart {
    int[] fullCartIdList;
    int[] selectedCartIdList;
    int[] fullCartQuantityList;
    int[] fullCartSelectStatusList;
    boolean[] checkedList;
    double subtotal;
    double total;
    
    @Autowired
    CartRepository cartRepo;
    
    
    public CustomerCart() {}
    
	public CustomerCart(int[] fullCartIdList, int[] selectedCartIdList, int[] fullCartQuantityList,
			int[] fullCartSelectStatusList, boolean[] checkedList, double subtotal, double total) {
		super();
		this.fullCartIdList = fullCartIdList;
		this.selectedCartIdList = selectedCartIdList;
		this.fullCartQuantityList = fullCartQuantityList;
		this.fullCartSelectStatusList = fullCartSelectStatusList;
		this.checkedList = checkedList;
		this.subtotal = subtotal;
		this.total = total;
	}



	public double cartSubtotal() {
        this.subtotal = 0;
        
        for(int i = 0; i < this.selectedCartIdList.length; i++) {
        	Cart cart = cartRepo.getCartById(this.selectedCartIdList[i]);
        	double price = cart.getProduct().getPrice();
        	int quant = cart.getQuantity();
        	this.subtotal += price * quant;
        }
        
        return this.subtotal;
    }
    
    public double cartTotal() {
    	this.subtotal = 0;
        
        for(int i = 0; i < this.fullCartIdList.length; i++) {
        	Cart cart = cartRepo.getCartById(this.fullCartIdList[i]);
        	double price = cart.getProduct().getPrice();
        	int quant = cart.getQuantity();
        	this.subtotal += price * quant;
        }
 
        return this.total;
    }
    
    public String formatedCartSubtotal() {
        return ValueRender.formatDoubleNumber(this.subtotal);
    }
    
    public String formatedCartTotal() {
        return ValueRender.formatDoubleNumber(this.total);
    }
    
    

	public int[] getFullCartIdList() {
		return fullCartIdList;
	}

	public void setFullCartIdList(int[] fullCartIdList) {
		this.fullCartIdList = fullCartIdList;
	}

	public int[] getSelectedCartIdList() {
		return selectedCartIdList;
	}

	public void setSelectedCartIdList(int[] selectedCartIdList) {
		this.selectedCartIdList = selectedCartIdList;
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

	public CartRepository getCartRepo() {
		return cartRepo;
	}

	public void setCartRepo(CartRepository cartRepo) {
		this.cartRepo = cartRepo;
	}

	public boolean[] getCheckedList() {
		return checkedList;
	}

	public void setCheckedList(boolean[] checkedList) {
		this.checkedList = checkedList;
	}

	public int[] getFullCartQuantityList() {
		return fullCartQuantityList;
	}

	public void setFullCartQuantityList(int[] fullCartQuantityList) {
		this.fullCartQuantityList = fullCartQuantityList;
	}

	public int[] getFullCartSelectStatusList() {
		return fullCartSelectStatusList;
	}

	public void setFullCartSelectStatusList(int[] fullCartSelectStatusList) {
		this.fullCartSelectStatusList = fullCartSelectStatusList;
	}
}
