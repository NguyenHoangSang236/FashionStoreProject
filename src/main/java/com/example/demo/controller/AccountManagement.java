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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.demo.entity.Account;
import com.example.demo.entity.Customer;
import com.example.demo.entity.dto.SelectedCustomerID;
import com.example.demo.respository.AccountRepository;
import com.example.demo.respository.CustomerRepository;
import com.example.demo.util.LoginState;

@SessionAttributes("currentuser")
@Controller
public class AccountManagement {
	@Autowired
	AccountRepository accRepo;
	
	@Autowired
	CustomerRepository cusRepo;
	
	@Autowired
	LoginPageController currentUser;
	
	Account account = new Account();
	Customer customer = new Customer();
	SelectedCustomerID selectedCustomerID = new SelectedCustomerID();
	Account accountEdited;
	
	
	public String renderToAccountManagement(Model model, HttpSession session, String action) {
		Account currentAccount = (Account)session.getAttribute("currentuser");
		
		if(currentAccount != null) {
			if(action != null) {
				int selectedAccId = Integer.valueOf(action);
				
				Account selectedAccount = accRepo.findByUserID(selectedAccId);
				if(selectedAccount.getStatus().equals("allowed")) {
					selectedAccount.setStatus("banned");
				}
				else if (selectedAccount.getStatus().equals("banned")) {
					selectedAccount.setStatus("allowed");
				}
				
				accRepo.save(selectedAccount);
			}
			
			List<Customer> AllCustomers = cusRepo.getCustomerById();
			List<Account> AllAcount = accRepo.getAllCustomerAccounts();
			
			model.addAttribute("ListCustomer", AllCustomers);
			model.addAttribute("ListAccount", AllAcount);
			
			return "accountcontrol";
		}
		else {
            return "redirect:/loginpage";
        }
	}
	
	
	@GetMapping("/accountmanagement")
	public String allCustomer(Model model, HttpSession session) {
		return renderToAccountManagement(model, session, null);
	}
	
	
	@PostMapping("/accountmanagement")
	public String deleteCustomer(Model model, HttpSession session, @RequestParam(value="action") String action) {
		return renderToAccountManagement(model, session, action);
	}
	
	
	@GetMapping("/editcustomer-id={uID}")
	public String showEditAccount(Model model, @PathVariable("uID") int uID ) {
		
		System.out.println(uID);
		accountEdited = accRepo.findByUserID(uID);
		System.out.println(accountEdited.getRole());
		System.out.println(accountEdited.getId());
		model.addAttribute("customerAccount",accountEdited);
		model.addAttribute("accObj",accountEdited);
		
		
		return "editaccount";
	}
	
	
	@PostMapping("/saveEditAccount")
	public String saveEditAccount(Model model, @ModelAttribute("accObj") Account accountObj ) {
		Customer customer = cusRepo.getCustomerById(accountEdited.getCustomer().getId());
		
		customer.setName(accountObj.getCustomer().getName());
		customer.setEmail(accountObj.getCustomer().getEmail());
		customer.setPhoneNumber(accountObj.getCustomer().getPhoneNumber());
		
		
		accountEdited.setUserName(accountObj.getUserName());
		accountEdited.setPassword(accountObj.getPassword());
		accountEdited.setCustomer(customer);
		
		
		accRepo.save(accountEdited);
		
		return "redirect:/accountmanagement";
	}
}
