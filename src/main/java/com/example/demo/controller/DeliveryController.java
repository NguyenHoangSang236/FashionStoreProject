package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Account;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Invoice;
import com.example.demo.entity.dto.CheckoutInfo;
import com.example.demo.respository.AccountRepository;
import com.example.demo.respository.CatalogRepository;
import com.example.demo.respository.CustomerRepository;
import com.example.demo.respository.InvoiceRepository;
import com.example.demo.respository.ProductRepository;
import com.example.demo.service.ProductService;
import com.example.demo.util.GlobalStaticValues;
import com.example.demo.util.ValueRender;

@Controller
public class DeliveryController {
	
	 @Autowired
	 ProductRepository productRepo;

	 @Autowired
	 ProductService productService;
	    
	 @Autowired
	 CatalogRepository catalogRepo;
	    
	 @Autowired
	 CustomerRepository cusRepo;
	 Customer customer = new Customer();
	
	@GetMapping("/Delivery-home")
    public String deliveryHome(HttpSession session, Model model, HttpServletRequest request ) {
		Account Cuser = (Account)session.getAttribute("currentuser");
		GlobalStaticValues.currentPage = "/invoice-control";
		
		if(Cuser != null) {
			Customer Ccustomer = cusRepo.getCustomerByAccountId(Cuser.getId());
		    
		    model.addAttribute("curentcusImage",Ccustomer.convertByteImamgeToBase64String());
		    model.addAttribute("curentcusName",Ccustomer.getName());
			
	        return "shipper-home";
		}
		else {
			return "redirect:/loginpage";
		}
    }
	@GetMapping("/Delivery-id")
    public String deliveryDetail(HttpSession session, Model model, HttpServletRequest request ) {
		Account Cuser = (Account)session.getAttribute("currentuser");
		GlobalStaticValues.currentPage = "/invoice-control";
		
		if(Cuser != null) {
			Customer Ccustomer = cusRepo.getCustomerByAccountId(Cuser.getId());
		    
		    model.addAttribute("curentcusImage",Ccustomer.convertByteImamgeToBase64String());
		    model.addAttribute("curentcusName",Ccustomer.getName());
			
	        return "deliver-order-details";
		}
		else {
			return "redirect:/loginpage";
		}
    }
}
