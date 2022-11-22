package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.demo.entity.Account;
import com.example.demo.entity.Cart;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Product;
import com.example.demo.entity.dto.CustomerCart;
import com.example.demo.respository.CartRepository;
import com.example.demo.respository.CustomerRepository;
import com.example.demo.util.GlobalStaticValues;
import com.example.demo.util.LoginState;

@SessionAttributes("currentuser")
@Controller
public class CartController {
    @Autowired
    CartRepository cartRepo;
    
    @Autowired
    CustomerRepository customerRepo;
    
    CustomerCart customerCart = new CustomerCart();
    
    
    public String getCartInfo(Account cuser ,Model model, int customerId, HttpServletRequest request, boolean[] globalCheckedCartArr, CustomerCart customerCart) {
    	int[] globalFullCartIdArr = GlobalStaticValues.customerFullCartIdList;
        int[] globalSelectedCartIdArr = GlobalStaticValues.customerSelectedCartIdList;
        
        List<Cart> fullCartList = new ArrayList<Cart>();
    	
        //get List of Cart by List of id
    	for(int i = 0; i < globalFullCartIdArr.length; i++) {
    		Cart cart = this.cartRepo.getCartById(globalFullCartIdArr[i]);
    		fullCartList.add(cart);
    	}
                
    	//check customer logged in or not
        if(cuser != null)
        {   
        	
        	customerCart.setFullCartIdList(globalFullCartIdArr);
        	customerCart.setTotal(calculateTotal(globalFullCartIdArr));
        	customerCart.setSubtotal(calculateTotal(globalSelectedCartIdArr));
        	
//        	for(int i = 0; i < customerCart.getCheckedList().length; i++) {
//    			System.out.println("- " + customerCart.getCheckedList()[i]);
//    		}        	
        	
        	
            model.addAttribute("customerCart", customerCart);
            model.addAttribute("fullCartList", fullCartList);
            model.addAttribute("checkedCartList", customerCart.getCheckedList());
            
            return "shopping-cart";
        }
        else {
            return "redirect:/loginpage";
        }
    }
    
    
    public double calculateTotal(int[] cartIdList) {
        double result = 0;
        
        for(int i = 0; i < cartIdList.length; i++) {
        	Cart cart = cartRepo.getCartById(cartIdList[i]);
        	double price = cart.getProduct().getPrice();
        	int quant = cart.getQuantity();
        	result += price * quant;
        }
        
        return result;
    }
    
    
    
    @GetMapping("/cart-of-customer-id={id}")
    public String showCart(@ModelAttribute("currentuser") Account Cuser, Model model, @PathVariable("id") int id, HttpServletRequest request) {
        GlobalStaticValues.customerFullCartIdList = cartRepo.getFullCartIdListByCustomerId(id);
        
        boolean[] globalCheckedCartArr = GlobalStaticValues.customerCheckedCartList(GlobalStaticValues.customerFullCartIdList);
        
//        for(int i = 0; i < globalCheckedCartArr.length; i++) {
//			System.out.println(globalCheckedCartArr[i]);
//		}
        
        customerCart.setCheckedList(globalCheckedCartArr);
        
        return getCartInfo(Cuser, model, id, request, globalCheckedCartArr, customerCart);
    }
    
    
   @PostMapping("/cart-of-customer-id={id}")
    public String updateCart(@ModelAttribute("currentuser") Account Cuser, Model model, @PathVariable("id") int id, HttpServletRequest request, @RequestParam(value="action") String action, @ModelAttribute("customerCart") CustomerCart cusCart) {
    	boolean[] globalCheckedCartArr = null;
    	if(action.equals("update")) {
    		GlobalStaticValues.customerSelectedCartIdList = cusCart.getSelectedCartIdList();
    		
    		int[] fullArr = GlobalStaticValues.customerFullCartIdList;
    		int[] selectedArr = cusCart.getSelectedCartIdList(); 
    		boolean[] checkedArr = GlobalStaticValues.customerCheckedCartList(fullArr);
    		
    		for(int i = 0; i < fullArr.length; i++) {
    			for(int j = 0; j < selectedArr.length; j++) {
    				if(fullArr[i] == selectedArr[j]) {
    					checkedArr[i] = true;
    				}
    			}
    		}
    		
    		customerCart = cusCart;
    		customerCart.setCheckedList(checkedArr);
	    }
    	
    	return getCartInfo(Cuser, model, id, request, globalCheckedCartArr, customerCart);
    }
    
}
