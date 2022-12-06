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


@Controller
public class InvoiceController {
	@Autowired
	AccountRepository accRepo;
	
	@Autowired
	CustomerRepository cusRepo;
	
	@GetMapping("/invoice-control")
    public String showAbout(HttpSession session,Model model, HttpServletRequest request ) {
		
        return "Invoice";
    }

}
