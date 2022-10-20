package com.example.demo.controller;

import java.util.List;

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
import com.example.demo.respository.Account.AccountRepository;

@Controller
public class LoginPageController {
	@Autowired
	AccountRepository accRepo;
	Account account = new Account();
	
//	@GetMapping("/loginpage")
//	public ModelAndView loginPage() {
//		return new ModelAndView("login");
//	}
	
	
	@GetMapping("/loginpage")
	public String showLoginPage(Model model) {
		model.addAttribute("Account", account);
		return "login";
	}
	 
	
	@PostMapping("/loginpage")
	public String submitForm(@ModelAttribute("Account") Account account) { 
		List<Account> accList = accRepo.findByUserNameAndPassword(account.getUserName(), account.getPassword());
		
		if(accList.size() > 0) {
//		    System.out.println("logged in");
			return "redirect:/home";
		}
		else {
			return "login";
		}
	}
}
