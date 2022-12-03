package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.demo.entity.Account;
import com.example.demo.entity.Cart;
import com.example.demo.entity.Customer;
import com.example.demo.entity.dto.CheckoutInfo;
import com.example.demo.respository.CartRepository;
import com.example.demo.respository.CustomerRepository;
import com.example.demo.util.GlobalStaticValues;
import com.example.demo.util.ValueRender;

@SessionAttributes("currentuser")
@Controller
public class CheckOutController {
	@Autowired
	CartRepository cartRepo;
	
	@Autowired
	CustomerRepository customerRepo;
	
	List<Cart> cartList;
	CheckoutInfo checkoutInfo = new CheckoutInfo();
	
	
    @GetMapping("/checkout")
    public String checkout(HttpSession session, Model model) {
    	Account currentAccount = (Account)session.getAttribute("currentuser");
	    
	    if(currentAccount != null) {
	    Customer Ccustomer = customerRepo.getCustomerByAccountId(currentAccount.getId());
	    
	    model.addAttribute("curentcusImage",Ccustomer.getImage());
	    model.addAttribute("curentcusName",Ccustomer.getName());
	    
	    }
        //check customer logged in or not
        if(currentAccount != null)
        {   
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
            return "redirect:/loginpage";
        }
    }
}
