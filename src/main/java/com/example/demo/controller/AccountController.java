package com.example.demo.controller;


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
import com.example.demo.respository.CartRepository;
import com.example.demo.respository.CustomerRepository;
import com.example.demo.util.GlobalStaticValues;


@SessionAttributes("currentuser")
@Controller
public class AccountController {
	@Autowired
	AccountRepository accRepo;
	
	@Autowired
	CustomerRepository cusRepo;
	
	@Autowired
    CartRepository cartRepo;
	
	Customer customer = new Customer();
	Account accountEdited;
	Account currentAccount;
	
	public String showMyProfileForm(HttpSession session,Model model) {
		currentAccount = (Account)session.getAttribute("currentuser");
		
	    if (currentAccount != null) {
	    	Customer accountDetail = cusRepo.getCustomerByAccountId(currentAccount.getId());
	 		model.addAttribute("Ccustomer", accountDetail);
	 		model.addAttribute("accObj", currentAccount);
	 		model.addAttribute("curentcusName",currentAccount.getCustomer().getName());
	 		model.addAttribute("curentcusImage",currentAccount.getCustomer().convertByteImamgeToBase64String());
	 		model.addAttribute("cartQuantity",cartRepo.getCartQuantityByCustomerId(accountDetail.getId()));

	 		accountEdited = currentAccount;
	    }
	    else {
	    	return "redirect:/loginpage";
	    }
		
        return "user-detail";
	}
	
	
	@GetMapping("/showaccount")
    public String showAbout(HttpSession session, Model model) {
		GlobalStaticValues.currentPage = "/showaccount";
		return showMyProfileForm(session, model);
    }
	
	
	@PostMapping("/showaccount")
	public String saveEditAccount(Model model, @ModelAttribute("accObj") Account accountObj, HttpSession session) {
		try{
			customer = cusRepo.getCustomerById(accountEdited.getCustomer().getId());

			customer.setName(accountObj.getCustomer().getName());
			customer.setImage(accountObj.getCustomer().getImage());
			customer.setEmail(accountObj.getCustomer().getEmail());
			customer.setPhoneNumber(accountObj.getCustomer().getPhoneNumber());

			accountEdited.setUserName(accountObj.getUserName());
			accountEdited.setPassword(accountObj.getPassword());
			accountEdited.setCustomer(customer);

			if(accountObj.getCustomer().getName().length() > 50 || accountObj.getUserName().length() > 50 ||  accountObj.getCustomer().getEmail().length() > 100 || accountObj.getCustomer().getPhoneNumber().length() > 10) {
				System.out.println("data too long");
				return showMyProfileForm(session, model);
			}

			accRepo.save(accountEdited);

			GlobalStaticValues.message = "Edited personal information";
			String message = GlobalStaticValues.message;
			model.addAttribute("message", message);
		}
		catch (Exception e) {
			System.out.println("data too long");
			e.printStackTrace();
		}

		return showMyProfileForm(session, model);
	}
}