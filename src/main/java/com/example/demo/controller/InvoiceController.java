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
import com.example.demo.respository.CustomerRepository;
import com.example.demo.respository.InvoiceRepository;
import com.example.demo.util.GlobalStaticValues;
import com.example.demo.util.ValueRender;


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
	boolean isInvoiceAcceptanceForm;
	
	
	@GetMapping("/invoice-control")
    public String allInvoices(HttpSession session, Model model, HttpServletRequest request ) {
		Account currentAccount = (Account)session.getAttribute("currentuser");
		GlobalStaticValues.currentPage = "/invoice-control";
		
		if(currentAccount != null) {
			mngInvoiceList = invoiceRepo.getAllInvoices();
			isInvoiceAcceptanceForm = false;
			
			model.addAttribute("allInvoiceList", mngInvoiceList);
			model.addAttribute("isInvoiceAcceptanceForm", isInvoiceAcceptanceForm);
			
	        return "invoice-management";
		}
		else {
			return "redirect:/loginpage";
		}
    }
	
	
	@GetMapping("/invoice-cod-accept")
	public String waitingCodInvoices(HttpSession session, Model model, HttpServletRequest request ) {
		Account currentAccount = (Account)session.getAttribute("currentuser");
		GlobalStaticValues.currentPage = "/invoice-cod-accept";
		
		if(currentAccount != null) {
			mngInvoiceList = invoiceRepo.getWaitingCodInvoices();
			isInvoiceAcceptanceForm = true;
			
			model.addAttribute("allInvoiceList", mngInvoiceList);
			model.addAttribute("isInvoiceAcceptanceForm", isInvoiceAcceptanceForm);
			
	        return "invoice-management";
		}
		else {
			return "redirect:/loginpage";
		}
    }
	
	
	@PostMapping("/invoice-cod-accept")
	public String waitingCodInvoicesEvent(HttpSession session, Model model, HttpServletRequest request, @RequestParam(value="action") String action) {
		Account currentAccount = (Account)session.getAttribute("currentuser");
		
		if(currentAccount != null) {
			isInvoiceAcceptanceForm = true;
			
			if(action.contains("accept invoice")) {
				int selectedInvoiceId = Integer.parseInt(ValueRender.getSubstring(action, 15, action.length()));
				
				Invoice selectedInvoice = invoiceRepo.getInvoiceById(selectedInvoiceId);
				selectedInvoice.setDeliveryStatus("packing");
				selectedInvoice.setAdminAcceptance("accepted");
				
				invoiceRepo.save(selectedInvoice);
			}
			else if(action.contains("refuse invoice")) {
				int selectedInvoiceId = Integer.parseInt(ValueRender.getSubstring(action, 15, action.length()));
				
				Invoice selectedInvoice = invoiceRepo.getInvoiceById(selectedInvoiceId);
				selectedInvoice.setDeliveryStatus("not shipped");
				selectedInvoice.setAdminAcceptance("refused");
				
				invoiceRepo.save(selectedInvoice);
			}
			
			mngInvoiceList = invoiceRepo.getWaitingCodInvoices();
			
			model.addAttribute("allInvoiceList", mngInvoiceList);
			model.addAttribute("isInvoiceAcceptanceForm", isInvoiceAcceptanceForm);
			
	        return "invoice-management";
		}
		else {
			return "redirect:/loginpage";
		}
    }
	
	
	@GetMapping("/invoice-history")
	public String invoiceHistory(HttpSession session, Model model, HttpServletRequest request ) {
		Account currentAccount = (Account)session.getAttribute("currentuser");
	    GlobalStaticValues.currentPage = "/invoice-history";
		
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
	public String invoiceDetails(HttpSession session,Model model, HttpServletRequest request, @PathVariable("invoiceId") int invoiceId) {
		Account currentAccount = (Account)session.getAttribute("currentuser");
		GlobalStaticValues.currentPage = "/invoice-details-id=" + invoiceId;
		
	    if(currentAccount != null) {
	    	Customer currentCustomer = cusRepo.getCustomerByAccountId(currentAccount.getId());
	    	
	    	Invoice selectedInvoice = invoiceRepo.getInvoiceById(invoiceId);
	    	
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
