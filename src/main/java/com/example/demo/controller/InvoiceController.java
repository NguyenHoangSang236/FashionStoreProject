package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.Account;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Invoice;
import com.example.demo.respository.AccountRepository;
import com.example.demo.respository.CustomerRepository;
import com.example.demo.respository.InvoiceRepository;


@Controller
public class InvoiceController {
	@Autowired
	AccountRepository accRepo;
	
	@Autowired
	CustomerRepository cusRepo;
	
	@Autowired
	InvoiceRepository invoiceRepo;
	
	String message = "";
	List<Invoice> mngInvoiceList;
	List<Invoice> customerInvoiceHistoryList;
	
	
	@GetMapping("/invoice-control")
    public String invoiceControl(HttpSession session, Model model, HttpServletRequest request ) {
		mngInvoiceList = invoiceRepo.getAllInvoices();
		
		model.addAttribute("allInvoiceList", mngInvoiceList);
		
        return "invoice-management";
    }
	
	@GetMapping("/invoice-history")
	public String invoiceHistory(HttpSession session,Model model, HttpServletRequest request ) {
		Account Cuser = (Account)session.getAttribute("currentuser");
	    
	    if(Cuser != null) {
	    	Customer currentCustomer = cusRepo.getCustomerByAccountId(Cuser.getId());
	    	
	    	customerInvoiceHistoryList = invoiceRepo.getPaymentHistoryByCustomerId(currentCustomer.getId());
	    	
		    model.addAttribute("curentcusImage",currentCustomer.getImage());
		    model.addAttribute("curentcusName",currentCustomer.getName());
		    model.addAttribute("invoiceHistoryList",customerInvoiceHistoryList);
	    }
	    else {
			return "redirect:/loginpage";
		}
	    
        return "invoice-history";
    }
}
