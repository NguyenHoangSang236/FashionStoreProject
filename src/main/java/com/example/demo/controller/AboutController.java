package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Account;
import com.example.demo.entity.Customer;
import com.example.demo.respository.CartRepository;
import com.example.demo.respository.CustomerRepository;


@Controller
public class AboutController {
	@Autowired
	CustomerRepository cusRepo;
    
    @Autowired
    CartRepository cartRepo;

    Customer customer = new Customer();
	
    @GetMapping("/about")
    public String showAbout(HttpSession session, Model model) {
    	Account currentUser = (Account)session.getAttribute("currentuser");
	    
	    if(currentUser != null) {
		    Customer currentCustomer = cusRepo.getCustomerByAccountId(currentUser.getId());
		    
		    model.addAttribute("curentcusImage",currentCustomer.convertByteImamgeToBase64String());
		    model.addAttribute("curentcusName",currentCustomer.getName());
		    model.addAttribute("cartQuantity",cartRepo.getCartQuantityByCustomerId(currentCustomer.getId()));
	    }
        return "about";
    }
}
