package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.entity.Account;
import com.example.demo.entity.Customer;
import com.example.demo.respository.AccountRepository;
import com.example.demo.respository.CustomerRepository;

@Controller
public class AccountController {
	@Autowired
	AccountRepository accRepo;
	Account account = new Account();
	@Autowired
	CustomerRepository cusRepo;
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
    public String showAbout(Model model ) {
        
		Customer accountDetail = cusRepo.getCustomerById(1);
		System.out.println(accountDetail.getImage());
		
        return "accounts";
    }
	
}
