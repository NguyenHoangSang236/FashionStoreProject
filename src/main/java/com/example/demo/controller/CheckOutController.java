package com.example.demo.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.demo.entity.Account;
import com.example.demo.entity.Cart;
import com.example.demo.entity.Invoice;
import com.example.demo.entity.Product;
import com.example.demo.entity.Customer;
import com.example.demo.entity.dto.CheckoutInfo;
import com.example.demo.entity.dto.InvoicesWithProducts;
import com.example.demo.respository.CartRepository;
import com.example.demo.respository.CustomerRepository;
import com.example.demo.respository.InvoiceInsertRepository;
import com.example.demo.respository.InvoiceRepository;
import com.example.demo.respository.ProductRepository;
import com.example.demo.util.GlobalStaticValues;
import com.example.demo.util.LoginState;
import com.example.demo.util.ValueRender;

@SessionAttributes("currentuser")
@Controller
public class CheckOutController {
	@Autowired
	CartRepository cartRepo;
	
	@Autowired 
	ProductRepository productRepo;
	
	@Autowired
	CustomerRepository customerRepo;
	
	@Autowired
	InvoiceRepository invoiceRepo;
	
	@Autowired
	InvoiceInsertRepository invoiceInsertRepository = new InvoiceInsertRepository();
	
	List<Cart> cartList;
	CheckoutInfo checkoutInfo = new CheckoutInfo();
	Invoice newInvoice = new Invoice();
	
	
	//create a new invoice 
	//-> insert into invoice and invoices_with_products table 
	//-> update sold_quantity and available_quantity of each product in invoice
	//-> set
	public void createNewInvoice(CheckoutInfo checkoutInfo, Customer customer, List<Cart> cartList, String paymentType) {
		Date currentDate = new Date();
		int newInvoiceID = invoiceRepo.getLastestInvoiceId() + 1;
		String deliveryStatus;
		String adminAcceptance;
		
		if(paymentType.equals("cod")) {
			deliveryStatus = "acceptance waiting";
			adminAcceptance = "waiting";
		}
		else {
			deliveryStatus = "packing";
			adminAcceptance = "accepted";
		}
		
		Invoice newInvoice = new Invoice(newInvoiceID, currentDate, deliveryStatus, 1, paymentType, "vnd", checkoutInfo.getPaymentIntentNote(), checkoutInfo.getInvoiceNote(), customer, GlobalStaticValues.customerInvoiceTotalPrice, adminAcceptance);
		
		List<InvoicesWithProducts> invoiceProductsList = ValueRender.getInvoiceProductsListByCartIdList(cartList, newInvoice);
		newInvoice.setInvoicesWithProducts(invoiceProductsList);
		
		invoiceInsertRepository.insertNewInvoice(newInvoice);
		
		for(int i = 0; i < cartList.size(); i++) {
			cartList.get(i).setBuyingStatus(1);
		}
		
		for(int i = 0; i < invoiceProductsList.size(); i++) {
			invoiceInsertRepository.insertInvoicesWithProducts(invoiceProductsList.get(i));
			
			Product product = productRepo.getProductById(invoiceProductsList.get(i).getId().getProductId());
			int currentAvaiQuant = product.getAvailableQuantity();
			int currentSoldQuant = product.getSoldQuantity();
			product.setAvailableQuantity(currentAvaiQuant - invoiceProductsList.get(i).getQuantity());
			product.setSoldQuantity(currentSoldQuant + currentSoldQuant);
			
			productRepo.save(product);
		}
		
		GlobalStaticValues.message = "Place order successfully !!";
	}
	
	
    @GetMapping("/checkout")
    public String checkout(HttpSession session, Model model) {
    	Account currentAccount = (Account)session.getAttribute("currentuser");
	    
    	//check customer logged in or not
	    if(currentAccount != null) {
	    	if(GlobalStaticValues.customerSelectedCartIdList.length > 0) {
	    		Customer currentCustomer = customerRepo.getCustomerByAccountId(currentAccount.getId());
	    		
	    		model.addAttribute("curentcusImage",currentCustomer.convertByteImamgeToBase64String());
	    		model.addAttribute("curentcusName",currentCustomer.getName());
	    		model.addAttribute("cartQuantity",cartRepo.getCartQuantityByCustomerId(currentCustomer.getId()));
	    		
	    		cartList = ValueRender.getCartListFromIdList(GlobalStaticValues.customerSelectedCartIdList, cartRepo);
	    		
	    		model.addAttribute("invoiceTotal", ValueRender.formatDoubleNumber(GlobalStaticValues.customerInvoiceTotalPrice));
	    		model.addAttribute("cartList", cartList);
	    		model.addAttribute("checkoutInfo", checkoutInfo);
	    		model.addAttribute("currentCustomer", GlobalStaticValues.currentCustomer);
	    		
	    		return "checkout";
	    	}
	    	else {
	    		GlobalStaticValues.message = "Please select products from your Cart that you want to buy!!";
	    		return "redirect:/cart-of-customer-account-id=" + currentAccount.getId();
	    	}
        }
        else {
        	GlobalStaticValues.currentPage = "/checkout";
            return "redirect:/loginpage";
        }
    }
    
    
    @SuppressWarnings("unused")
	@PostMapping("/checkout")
    public String placeOrderSubmit(HttpSession session, Model model, HttpServletRequest request,@ModelAttribute("checkoutInfo") CheckoutInfo modelCheckoutInfo, @RequestParam(required=false,value="action") String action) {
    	//if click at Place Order button --> COD payment
    	if(action != null) {
    		checkoutInfo = new CheckoutInfo(GlobalStaticValues.currentCustomer.getName(), modelCheckoutInfo.getCountry(), modelCheckoutInfo.getAddress(), modelCheckoutInfo.getCity(), GlobalStaticValues.currentCustomer.getPhoneNumber(), GlobalStaticValues.currentCustomer.getEmail(), modelCheckoutInfo.getInvoiceNote(), modelCheckoutInfo.getPaymentIntentNote());
    		
    		Account currentAccount = (Account)session.getAttribute("currentuser");
    	    
        	//check customer logged in or not
    	    if(currentAccount != null) {
    		    Customer currentCustomer = customerRepo.getCustomerByAccountId(currentAccount.getId());
    		    
    		    model.addAttribute("curentcusImage",currentCustomer.convertByteImamgeToBase64String());
    		    model.addAttribute("curentcusName",currentCustomer.getName());
    	    
                cartList = ValueRender.getCartListFromIdList(GlobalStaticValues.customerSelectedCartIdList, cartRepo);
                
                createNewInvoice(checkoutInfo, currentCustomer, cartList, "cod");
                
                model.addAttribute("invoiceTotal", ValueRender.formatDoubleNumber(GlobalStaticValues.customerInvoiceTotalPrice));
                model.addAttribute("cartList", cartList);
                model.addAttribute("checkoutInfo", checkoutInfo);
                model.addAttribute("currentCustomer", GlobalStaticValues.currentCustomer);
                
                GlobalStaticValues.message = "Thank you for buying";
		    	String message = GlobalStaticValues.message;
	        	model.addAttribute("message", message);
                return "index";
    	    }
    	    else {
    	    	GlobalStaticValues.currentPage = "/checkout";
    	    	return "redirect:/loginpage";
    	    }
    	}
    	//if click at PayPal button --> PayPal payment
	    if(action == null) {
	    	checkoutInfo = new CheckoutInfo(GlobalStaticValues.currentCustomer.getName(), modelCheckoutInfo.getCountry(), modelCheckoutInfo.getAddress(), modelCheckoutInfo.getCity(), GlobalStaticValues.currentCustomer.getPhoneNumber(), GlobalStaticValues.currentCustomer.getEmail(), modelCheckoutInfo.getInvoiceNote(), modelCheckoutInfo.getPaymentIntentNote());
	    		
	    	Account currentAccount = (Account)session.getAttribute("currentuser");
	    	    
	      	//check customer logged in or not
	        if(currentAccount != null) {
	    	    Customer currentCustomer = customerRepo.getCustomerByAccountId(currentAccount.getId());
	    	    
	    	    model.addAttribute("curentcusImage",currentCustomer.getImage());
	    	    model.addAttribute("curentcusName",currentCustomer.getName());
	        
	            cartList = ValueRender.getCartListFromIdList(GlobalStaticValues.customerSelectedCartIdList, cartRepo);
	               
	            createNewInvoice(checkoutInfo, currentCustomer, cartList, "paypal");
	                
	            model.addAttribute("invoiceTotal", ValueRender.formatDoubleNumber(GlobalStaticValues.customerInvoiceTotalPrice));
	            model.addAttribute("cartList", cartList);
	            model.addAttribute("checkoutInfo", checkoutInfo);
	            model.addAttribute("currentCustomer", GlobalStaticValues.currentCustomer);
	            
	            GlobalStaticValues.message = "Thank you for buying";
		    	String message = GlobalStaticValues.message;
	        	model.addAttribute("message", message);
	            return "redirect:/home";
	        }
	        else {
    	    	GlobalStaticValues.currentPage = "/checkout";
    	    	return "redirect:/loginpage";
    	    }
	    }    
	    
    	//if click at Place Order button --> COD payment
	    else if(action.equals("place order")) {
    		checkoutInfo = new CheckoutInfo(GlobalStaticValues.currentCustomer.getName(), modelCheckoutInfo.getCountry(), modelCheckoutInfo.getAddress(), modelCheckoutInfo.getCity(), GlobalStaticValues.currentCustomer.getPhoneNumber(), GlobalStaticValues.currentCustomer.getEmail(), modelCheckoutInfo.getInvoiceNote(), modelCheckoutInfo.getPaymentIntentNote());
    		
    		Account currentAccount = (Account)session.getAttribute("currentuser");
    	    
        	//check customer logged in or not
    	    if(currentAccount != null) {
    		    Customer currentCustomer = customerRepo.getCustomerByAccountId(currentAccount.getId());
    		    
    		    model.addAttribute("curentcusImage",currentCustomer.getImage());
    		    model.addAttribute("curentcusName",currentCustomer.getName());
    	    
                cartList = ValueRender.getCartListFromIdList(GlobalStaticValues.customerSelectedCartIdList, cartRepo);
                
                createNewInvoice(checkoutInfo, currentCustomer, cartList, "cod");
                
                model.addAttribute("invoiceTotal", ValueRender.formatDoubleNumber(GlobalStaticValues.customerInvoiceTotalPrice));
                model.addAttribute("cartList", cartList);
                model.addAttribute("checkoutInfo", checkoutInfo);
                model.addAttribute("currentCustomer", GlobalStaticValues.currentCustomer);
            	
                return "checkout";
    	    }
    	    else {
    	    	GlobalStaticValues.currentPage = "/checkout";
    	    	return "redirect:/loginpage";
    	    }
    	}
	    return "checkout";
    }
}
