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
import com.example.demo.entity.Delivery;
import com.example.demo.entity.Invoice;
import com.example.demo.entity.Staff;
import com.example.demo.entity.dto.CheckoutInfo;
import com.example.demo.respository.AccountRepository;
import com.example.demo.respository.CustomerRepository;
import com.example.demo.respository.InvoiceRepository;
import com.example.demo.respository.StaffRepository;
import com.example.demo.service.InvoiceService;
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
	
	@Autowired
	StaffRepository staffRepo;
	
	@Autowired
	InvoiceService invoiceService;
	
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
				
				invoiceService.cancelOrder(selectedInvoiceId);
				
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
	    	
		    model.addAttribute("curentcusImage",currentCustomer.convertByteImamgeToBase64String());
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
	    	Staff currentStaff = staffRepo.getStaffByAccountId(currentAccount.getId());
	    	
	    	if(currentCustomer != null) {
	    		Invoice selectedInvoice = invoiceRepo.getInvoiceById(invoiceId);
	    		
	    		model.addAttribute("selectedInvoice", selectedInvoice);
	    		model.addAttribute("productsList", selectedInvoice.getInvoicesWithProducts());
	    		model.addAttribute("curentcusImage",currentCustomer.convertByteImamgeToBase64String());
	    		model.addAttribute("curentcusName",currentCustomer.getName());
	    		
	    		return "invoice-detail";
	    	}
	    	else {
	    		if(currentStaff.getAccount().getRole().equals("shipper")) {
	    			Invoice selectedInvoice = invoiceRepo.getInvoiceById(invoiceId);
		    		
		    		model.addAttribute("selectedInvoice", selectedInvoice);
		    		model.addAttribute("productsList", selectedInvoice.getInvoicesWithProducts());
		    		model.addAttribute("curentcusImage",currentStaff.convertByteImamgeToBase64String());
		    		model.addAttribute("curentcusName",currentStaff.getName());
		    		
		    		return "shipper-invoice-detail";
	    		}
	    		else {
	    			Invoice selectedInvoice = invoiceRepo.getInvoiceById(invoiceId);
	    			Delivery delivery = selectedInvoice.getDelivery();
	    			String shipperPhoneNum = "...";
	    			String shipperAdditionalCmt = "";
	    			String shipperImage = "https://api-private.atlassian.com/users/6b5c1609134a5887d7f3ab1b73557664/avatar";
	    			
	    			if(delivery != null) {
	    				shipperPhoneNum = delivery.getStaff().getPhoneNumber();
	    				shipperAdditionalCmt = delivery.getAdditionalShipperComment();
	    				shipperImage = delivery.getStaff().convertByteImamgeToBase64String();
	    			}
	    			
	    			model.addAttribute("delivery", delivery);
	    			model.addAttribute("productsList", selectedInvoice.getInvoicesWithProducts());
	    			model.addAttribute("curentcusImage", currentStaff.convertByteImamgeToBase64String());
	    			model.addAttribute("curentcusName", currentStaff.getName());
	    			model.addAttribute("selectedInvoice", selectedInvoice);
	    			model.addAttribute("shipperPhoneNum", shipperPhoneNum);
	    			model.addAttribute("shipperAdditionalCmt", shipperAdditionalCmt);
	    			model.addAttribute("shipperImage", shipperImage);
	    			
	    			return "invoice-delivery-details";
	    		}
			}
	    }
	    else {
			return "redirect:/loginpage";
		}
    }
	
	
	
//	@GetMapping("/invoice-details-id={id}")
//	public String invoiceDetailsAdmin(HttpSession session,Model model, HttpServletRequest request, @PathVariable("id") int invoiceId) {
//		Account currentAccount = (Account)session.getAttribute("currentuser");
//		//GlobalStaticValues.currentPage = "/invoice-details-id=" + invoiceId;
//		
//	    if(currentAccount != null) {
//	    	Customer currentCustomer = cusRepo.getCustomerByAccountId(currentAccount.getId());
//	    	
//	    	//Invoice selectedInvoice = invoiceRepo.getInvoiceById(invoiceId);
//	    	
//	    	return "invoice-delivery-details";
//	    }
//	    else {
//			return "redirect:/loginpage";
//		}
//	    
//    }
}
