package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
		Account currentAccount = (Account)session.getAttribute("currentuser");
	    
	    if(currentAccount != null) {
	    	Customer currentCustomer = cusRepo.getCustomerByAccountId(currentAccount.getId());
	    	
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
	
	
	@GetMapping("/invoice-details-id={invoiceId}")
	public String invoiceDetail(HttpSession session,Model model, HttpServletRequest request, @PathVariable("invoiceId") int invoiceId) {
		Account currentAccount = (Account)session.getAttribute("currentuser");
	    
	    if(currentAccount != null) {
	    	Customer currentCustomer = cusRepo.getCustomerByAccountId(currentAccount.getId());
	    	
	    	Invoice selectedInvoice = invoiceRepo.getInvoiceFromId(invoiceId);
	    	
	    	System.out.println(selectedInvoice.getInvoicesWithProducts().get(0).formattedProductTotalPrice());
	    	
	    	model.addAttribute("selectedInvoice", selectedInvoice);
	    	model.addAttribute("productsList", selectedInvoice.getInvoicesWithProducts());
		    model.addAttribute("curentcusImage",currentCustomer.getImage());
		    model.addAttribute("curentcusName",currentCustomer.getName());
	    }
	    else {
			return "redirect:/loginpage";
		}
	    
        return "invoice-detail";
    }
}
