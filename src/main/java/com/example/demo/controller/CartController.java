package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.entity.Cart;
import com.example.demo.entity.dto.CustomerCart;
import com.example.demo.respository.CartRepository;
import com.example.demo.respository.CustomerRepository;
import com.example.demo.util.LoginState;

@Controller
public class CartController {
    @Autowired
    CartRepository cartRepo;
    
    @Autowired
    CustomerRepository customerRepo;
    
    
    @GetMapping("/cart-of-customer-id={id}")
    public String showCart(Model model, @PathVariable("id") int id, HttpServletRequest request) {
        List<Cart> cartList = cartRepo.getCurrentCartByCustomerId(id);
        CustomerCart customerCart = new CustomerCart();
        
        if(LoginState.isLoggedIn(model, request, customerRepo) == true)
        {            
            customerCart.setFullCartList(cartList);
            
//            System.out.println(customerCart.cartSubtotal());
//            System.out.println(customerCart.cartTotal());
            
            model.addAttribute("cartList", cartList);
            model.addAttribute("customerCart", customerCart);
            
            return "shopping-cart";
        }
        else {
            return "redirect:/loginpage";
        }
    }
}
