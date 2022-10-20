package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.entity.Account;
import com.example.demo.respository.Account.AccountRepository;


@org.springframework.web.bind.annotation.RestController
public class AccountController {
	@Autowired
	AccountRepository accRepo;
	Account account = new Account();

	@PostMapping("/account")
	public Account saveAccount(@Validated @RequestBody Account account) {
		return accRepo.save(account);
	}
	
	@GetMapping("/account")
	public List<Account> allAccount() {
		return accRepo.findAll();
	}
}
