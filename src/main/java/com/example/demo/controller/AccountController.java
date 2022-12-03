package com.example.demo.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.example.demo.respository.CartRepository;
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
	Account accountEdited;

	@PostMapping("/account")
	public Account saveAccount(@Validated @RequestBody Account account) {
		return accRepo.save(account);
	}
	
	
	@GetMapping("/account")
	public List<Account> allAccount() {
		return accRepo.findAll();
	}

	
	@GetMapping("/showaccount")
    public String showAbout(HttpSession session,Model model, HttpServletRequest request ) {
		
		Account Cuser = (Account)session.getAttribute("currentuser");
		accountEdited = Cuser;
		Cookie[] cookies = request.getCookies();
	    if (Cuser != null) {
	        
//	        Customer accountDetail = cusRepo.getCustomerById(Integer.valueOf(cookies[0].getValue()));
	    	Customer accountDetail = cusRepo.getCustomerByAccountId(Cuser.getId());
	 		model.addAttribute("Ccustomer", accountDetail);
	 		model.addAttribute("accObj", Cuser);
	    }
	    else {System.out.println("nulllll");}
		
        return "accounts";
    }
	
	@PostMapping("/saveCurrentAccount")
	public String saveEditAccount(Model model, @ModelAttribute("accObj") Account accountObj ) {
		Customer customer = cusRepo.getCustomerById(accountEdited.getCustomer().getId());
		
		customer.setName(accountObj.getCustomer().getName());
		customer.setEmail(accountObj.getCustomer().getEmail());
		customer.setPhoneNumber(accountObj.getCustomer().getPhoneNumber());
		
		
		accountEdited.setUserName(accountObj.getUserName());
		accountEdited.setPassword(accountObj.getPassword());
		accountEdited.setCustomer(customer);
		
		
		accRepo.save(accountEdited);
		
		return "redirect:/showaccount";
	}

	
}
