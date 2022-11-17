package com.example.demo.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.demo.entity.Account;
import com.example.demo.entity.Customer;
import com.example.demo.respository.AccountRepository;
import com.example.demo.respository.CustomerRepository;
import com.example.demo.util.LoginState;

@SessionAttributes("currentuser")
@Controller
public class AccountController {
	@Autowired
	AccountRepository accRepo;
	
	@Autowired
	CustomerRepository cusRepo;
	
	@Autowired
	LoginPageController currentUser;
	
	Account account = new Account();
	Customer customer = new Customer();
	

	@PostMapping("/account")
	public Account saveAccount(@Validated @RequestBody Account account) {
		return accRepo.save(account);
	}
	
	@GetMapping("/account")
	public List<Account> allAccount() {
		return accRepo.findAll();
	}

	@GetMapping("/showaccount")
    public String showAbout(@ModelAttribute("currentuser") Account Cuser, Model model, HttpServletRequest request ) {
		
		Cookie[] cookies = request.getCookies();
	    if (cookies != null) {
	        
//	        Customer accountDetail = cusRepo.getCustomerById(Integer.valueOf(cookies[0].getValue()));
	    	Customer accountDetail = cusRepo.getCustomerById(Cuser.getId());
	 		model.addAttribute("userpic", accountDetail.getImage());
	 		model.addAttribute("username", accountDetail.getName());
	 		model.addAttribute("useremail", accountDetail.getEmail());
	 		model.addAttribute("userphonenumber", accountDetail.getPhoneNumber());
	    }
	    else {System.out.println("nulllll");}
		
        return "accounts";
    }

	
}
