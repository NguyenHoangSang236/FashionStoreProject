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
	
	
	//create a new invoice -> insert into invoice and invoices_with_products table -> update sold_quantity and available_quantity of each product in invoice
	public void createNewInvoice(CheckoutInfo checkoutInfo, Customer customer, List<Cart> cartList) {
		Date currentDate = new Date();
		int newInvoiceID = invoiceRepo.getLastestInvoiceId() + 1;
		Invoice newInvoice = new Invoice(newInvoiceID, currentDate, 0, 1, "paypal", "vnd", checkoutInfo.getPaymentIntentNote(), checkoutInfo.getInvoiceNote(), customer, GlobalStaticValues.customerInvoiceTotalPrice, "waiting");
		
		List<InvoicesWithProducts> invoiceProductsList = ValueRender.getInvoiceProductsListByCartIdList(cartList, newInvoice);
		newInvoice.setInvoicesWithProducts(invoiceProductsList);
		
		invoiceInsertRepository.insertNewInvoice(newInvoice);
		
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
	    		Customer Ccustomer = customerRepo.getCustomerByAccountId(currentAccount.getId());
	    		
	    		model.addAttribute("curentcusImage",Ccustomer.getImage());
	    		model.addAttribute("curentcusName",Ccustomer.getName());
	    		
	    		cartList = ValueRender.getCartListFromIdList(GlobalStaticValues.customerSelectedCartIdList, cartRepo);
	    		
	    		for(int i = 0; i < cartList.size(); i++) {
	    			System.out.println(cartList.get(i).formatedTotalPrice());
	    		}
	    		
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
            return "redirect:/loginpage";
        }
    }
    
    
    @PostMapping("/checkout")
    public String placeOrderSubmit(HttpSession session, Model model, HttpServletRequest request,@ModelAttribute("checkoutInfo") CheckoutInfo modelCheckoutInfo, @RequestParam(value="action") String action) {
    	if(action.equals("place order")) {
    		if(modelCheckoutInfo.getAddress() == null || modelCheckoutInfo.getCity() == null || modelCheckoutInfo.getCountry() == null || modelCheckoutInfo.getPaymentIntentNote() == null) {
        		GlobalStaticValues.message = "Address, Town/City, Country and Payment Intent notes must be filled in !!";
        	}
        	else {
        		checkoutInfo = new CheckoutInfo(GlobalStaticValues.currentCustomer.getName(), modelCheckoutInfo.getCountry(), modelCheckoutInfo.getAddress(), modelCheckoutInfo.getCity(), GlobalStaticValues.currentCustomer.getPhoneNumber(), GlobalStaticValues.currentCustomer.getEmail(), modelCheckoutInfo.getInvoiceNote(), modelCheckoutInfo.getPaymentIntentNote());
        		
//        		System.out.println(checkoutInfo.getAddress() + " " + checkoutInfo.getCity()+ " " + checkoutInfo.getCountry() + " " + checkoutInfo.getInvoiceNote() + " " + checkoutInfo.getPaymentIntentNote() + " " + checkoutInfo.getFullName() + " " + checkoutInfo.getPhone());

        		Account currentAccount = (Account)session.getAttribute("currentuser");
        	    
            	//check customer logged in or not
        	    if(currentAccount != null) {
        		    Customer Ccustomer = customerRepo.getCustomerByAccountId(currentAccount.getId());
        		    
        		    model.addAttribute("curentcusImage",Ccustomer.getImage());
        		    model.addAttribute("curentcusName",Ccustomer.getName());
        	    
                    cartList = ValueRender.getCartListFromIdList(GlobalStaticValues.customerSelectedCartIdList, cartRepo);
                    
//                    for(int i = 0; i < cartList.size(); i++) {
//                    	System.out.println(cartList.get(i).formatedTotalPrice());
//                    }
                    
                    createNewInvoice(checkoutInfo, Ccustomer, cartList);
                    
                    model.addAttribute("invoiceTotal", ValueRender.formatDoubleNumber(GlobalStaticValues.customerInvoiceTotalPrice));
                    model.addAttribute("cartList", cartList);
                    model.addAttribute("checkoutInfo", checkoutInfo);
                    model.addAttribute("currentCustomer", GlobalStaticValues.currentCustomer);
                	
                    return "checkout";
                }
        	}
    	}
    	return "checkout";
    }
}
