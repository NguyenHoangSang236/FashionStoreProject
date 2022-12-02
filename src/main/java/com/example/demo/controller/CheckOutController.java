package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.demo.entity.Account;
import com.example.demo.entity.Cart;
import com.example.demo.respository.CartRepository;
import com.example.demo.util.GlobalStaticValues;
import com.example.demo.util.ValueRender;

@SessionAttributes("currentuser")
@Controller
public class CheckOutController {
	@Autowired
	CartRepository cartRepo;
	
	List<Cart> cartList;
	
	
    @GetMapping("/checkout")
    public String Checkout(HttpSession session, Model model) {
        
    	Account currentAccount = (Account)session.getAttribute("currentuser");
        //check customer logged in or not
        if(currentAccount != null)
        {   
            cartList = ValueRender.getCartListFromIdList(GlobalStaticValues.customerSelectedCartIdList, cartRepo);
            
            
        	
            return "checkout";
        }
        else {
            return "redirect:/loginpage";
        }
    }
}
