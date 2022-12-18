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
import com.example.demo.respository.CustomerRepository;


@Controller
public class AboutController {
	
	@Autowired
	CustomerRepository cusRepo;
    Customer customer = new Customer();
	
    @GetMapping("/about")
    public String showAbout(HttpSession session, Model model) {
        
    	Account Cuser = (Account)session.getAttribute("currentuser");
	    
	    if(Cuser != null) {
	    Customer Ccustomer = cusRepo.getCustomerByAccountId(Cuser.getId());
	    
	    model.addAttribute("curentcusImage",Ccustomer.convertByteImamgeToBase64String());
	    model.addAttribute("curentcusName",Ccustomer.getName());
	    
	    }
        return "about";
    }
}
