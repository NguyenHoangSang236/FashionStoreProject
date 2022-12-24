package com.example.demo.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.demo.entity.Account;
import com.example.demo.entity.Customer;
import com.example.demo.respository.AccountRepository;
import com.example.demo.respository.CustomerRepository;
import com.example.demo.util.GlobalStaticValues;
import com.example.demo.util.ValueRender;

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

	
	public String showMyProfileForm(HttpSession session,Model model, HttpServletRequest request) {
		Account currentuser = (Account)session.getAttribute("currentuser");
		accountEdited = currentuser;
		
	    if (currentuser != null) {
	    	Customer accountDetail = cusRepo.getCustomerByAccountId(currentuser.getId());
	 		model.addAttribute("Ccustomer", accountDetail);
	 		model.addAttribute("accObj", currentuser);
	 		model.addAttribute("curentcusName",currentuser.getCustomer().getName());
	 		model.addAttribute("curentcusImage",currentuser.getCustomer().convertByteImamgeToBase64String());
	 		
	    }
	    else {
	    	return "redirect:/loginpage";
	    }
		
        return "user-detail";
	}
	
	
//	@PostMapping("/account")
//	public Account saveAccount(@Validated @RequestBody Account account) {
//		return accRepo.save(account);
//	}
//	
//	@GetMapping("/account")
//	public List<Account> allAccount() {
//		return accRepo.findAll();
//	}

	
	@GetMapping("/showaccount")
    public String showAbout(HttpSession session, Model model, HttpServletRequest request ) {
		GlobalStaticValues.currentPage = "/showaccount";
		return showMyProfileForm(session, model, request);
    }
	
	
	@PostMapping("/showaccount")
	public String saveEditAccount(Model model, @ModelAttribute("accObj") Account accountObj, HttpSession session, HttpServletRequest request) {
		customer = cusRepo.getCustomerById(accountEdited.getCustomer().getId());
		
		customer.setName(accountObj.getCustomer().getName());
		customer.setImage(accountObj.getCustomer().getImage());
		customer.setEmail(accountObj.getCustomer().getEmail());
		customer.setPhoneNumber(accountObj.getCustomer().getPhoneNumber());
		
		accountEdited.setUserName(accountObj.getUserName());
		accountEdited.setPassword(accountObj.getPassword());
		accountEdited.setCustomer(customer);
//		System.out.println(ValueRender.convertByteToString(accountObj.getCustomer().getImage()));
//		System.out.println("..." + ValueRender.formattedInputString(accountObj.getCustomer().getName()) + "...");
		
		accRepo.save(accountEdited);
		GlobalStaticValues.message = "Edited personal information";
    	String message = GlobalStaticValues.message;
    	model.addAttribute("message", message);
		
		return showMyProfileForm(session, model, request);
	}
}