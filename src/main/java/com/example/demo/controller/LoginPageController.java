package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

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
import com.example.demo.respository.AccountRepository;
import com.example.demo.respository.CustomerRepository;
import com.example.demo.respository.StaffRepository;
import com.example.demo.util.LoginState;
import com.example.demo.util.ValueRender;

@Controller
public class LoginPageController {
	@Autowired
	AccountRepository accRepo;
	Account account = new Account();
	
	@Autowired
	CustomerRepository cusRepo;
	
	@Autowired
	StaffRepository staffRepo;
	
	
	@GetMapping("/loginpage")
	public String showLoginPage(Model model) {
		model.addAttribute("Account", account);
		return "login";
	}
	 
	
	@PostMapping("/loginpage")
	public String submitForm(@ModelAttribute("Account") Account account, HttpServletResponse response) { 
		Account acc = accRepo.findByUserNameAndPassword(account.getUserName(), account.getPassword());
		
		if(acc.getUserName().equals(null) == false) {
//		    Cookie cookie = new Cookie(acc.getUserName(), ValueRender.encodePassword(acc.getPassword()));
			Cookie cookie = new Cookie(acc.getUserName(),  Integer.toString(acc.getId()));
		    cookie.setMaxAge(7 * 24 * 60 * 60);
		    response.addCookie(cookie);
		    
		    LoginState.currentAccount = acc;
		    
		    		    
		    if(acc.getRole().equals("admin")) {
		        LoginState.currentStaff = acc.getStaff();
		        return "redirect:/allproduct";
		    }
		    else {
	            LoginState.currentCustomer = acc.getCustomer();
	            return "redirect:/home";
		    }
		}
		else {
			return "login";
		}
	}
}
