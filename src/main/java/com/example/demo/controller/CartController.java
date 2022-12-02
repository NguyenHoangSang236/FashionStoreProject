package com.example.demo.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.demo.entity.Account;
import com.example.demo.entity.Cart;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Product;
import com.example.demo.entity.dto.CustomerCart;
import com.example.demo.respository.CartRepository;
import com.example.demo.respository.CustomerRepository;
import com.example.demo.util.GlobalStaticValues;
import com.example.demo.util.LoginState;
import com.example.demo.util.ValueRender;

@SessionAttributes("currentuser")
@Controller
public class CartController {
    @Autowired
    CartRepository cartRepo;
    
    @Autowired
    CustomerRepository customerRepo;
    
    static CustomerCart customerCart = new CustomerCart();
    
    
    public String getCartInfo(HttpSession session, Model model, int customerId, HttpServletRequest request, boolean[] globalCheckedCartArr, CustomerCart customerCart) {
    	int[] globalFullCartIdArr = GlobalStaticValues.customerFullCartIdList;
    	int[] globalFullCartQuantityArr = GlobalStaticValues.customerFullCartQuantityList;
        int[] globalSelectedCartIdArr = GlobalStaticValues.customerSelectedCartIdList;
        List<Cart> fullCartList = GlobalStaticValues.customerFullCartList;
        
        Account currentAccount = (Account)session.getAttribute("currentuser");
        //check customer logged in or not
        if(currentAccount != null)
        {   
        	Customer currentCustomer = customerRepo.getCustomerByAccountId(currentAccount.getId());
    	    
    	    model.addAttribute("curentcusImage",currentCustomer.getImage());
    	    model.addAttribute("curentcusName",currentCustomer.getName());
        	customerCart.setFullCartIdList(globalFullCartIdArr);
        	customerCart.setTotal(calculateTotal(fullCartList));
        	customerCart.setSubtotal(calculateTotal(ValueRender.getCartListFromIdList(globalSelectedCartIdArr, cartRepo)));
        	customerCart.setFullCartQuantityList(globalFullCartQuantityArr);
        	
            model.addAttribute("customerCart", customerCart);
            model.addAttribute("fullCartList", fullCartList);
            model.addAttribute("checkedCartList", customerCart.getCheckedList());
            
    		GlobalStaticValues.customerInvoiceTotalPrice = customerCart.getSubtotal();
            
            return "shopping-cart";
        }
        else {
            return "redirect:/loginpage";
        }
    }
    
    
    public double calculateTotal(List<Cart> fullCartList) {
        double result = 0;
        
        for(int i = 0; i < fullCartList.size(); i++) {
        	Cart cart = fullCartList.get(i);
        	double price = cart.getProduct().getPrice();
        	int quant = cart.getQuantity();
        	result += price * quant;
        }
        
        return result;
    }
    
    
    public void saveUpdatedCart(List<Cart> cartList, int[] quantList, boolean[] checkedList) {
    	for(int i = 0 ; i < cartList.size(); i++) {
    		int selectInd;
    		
    		if(checkedList[i] == true) {
    			selectInd = 1;
    		} else selectInd = 0;
    		
    		Cart updatedCart = cartRepo.getCartById(cartList.get(i).getId());
    		updatedCart.setQuantity(quantList[i]);
    		updatedCart.setSelectStatus(selectInd);
    		
//    		System.out.println(updatedCart.getId() + " " + updatedCart.getQuantity() + " " + updatedCart.getSelectStatus() + " " + updatedCart.getCustomer().getName());
    		
    		cartRepo.save(updatedCart);
    	}
    }
    
    
    public void updateCart(CustomerCart cusCart) {
    	GlobalStaticValues.customerSelectedCartIdList = cusCart.getSelectedCartIdList();
		GlobalStaticValues.customerFullCartQuantityList = cusCart.getFullCartQuantityList();
		GlobalStaticValues.customerFullCartList = ValueRender.getCartListFromIdList(GlobalStaticValues.customerFullCartIdList, cartRepo);
		
		int[] fullArr = GlobalStaticValues.customerFullCartIdList;
		int[] selectedArr = cusCart.getSelectedCartIdList(); 
		boolean[] checkedArr = GlobalStaticValues.customerCheckedCartList(GlobalStaticValues.customerFullSelectStatusList);
		int startInd = 0;
		
		for(int i = 0; i < fullArr.length; i++) {
			boolean isMatched = false;
			for(int j = startInd; j < selectedArr.length; j++) {
				if(fullArr[i] == selectedArr[j]) {
					checkedArr[i] = true;
					startInd++;
					isMatched = true;
				}
			}
			if(isMatched == false && checkedArr[i] == true) {
				checkedArr[i] = false;
			}
		}
		
		customerCart = cusCart;
		customerCart.setCheckedList(checkedArr);
		
		saveUpdatedCart(GlobalStaticValues.customerFullCartList, GlobalStaticValues.customerFullCartQuantityList, checkedArr);
    }
    
    
    public void deleteSelectedCart(int cartId, int customerId) {
    	cartRepo.deleteById(cartId);
    	
    	GlobalStaticValues.customerFullCartIdList = cartRepo.getFullCartIdListByCustomerId(customerId);
        GlobalStaticValues.customerFullSelectStatusList = cartRepo.getFullCartSelectStatusListByCustomerId(customerId);    
        GlobalStaticValues.customerFullCartQuantityList = cartRepo.getFullCartQuantityListByCustomerId(customerId);
        GlobalStaticValues.customerFullCartList = ValueRender.getCartListFromIdList(GlobalStaticValues.customerFullCartIdList, cartRepo);
    }
    
    
    
    @GetMapping("/cart-of-customer-account-id={id}")
    public String showCart(HttpSession session, Model model, @PathVariable("id") int id, HttpServletRequest request) {
    	GlobalStaticValues.currentCustomer = customerRepo.getCustomerByAccountId(id);
        GlobalStaticValues.customerFullCartIdList = cartRepo.getFullCartIdListByCustomerId(GlobalStaticValues.currentCustomer.getId());
        GlobalStaticValues.customerFullSelectStatusList = cartRepo.getFullCartSelectStatusListByCustomerId(GlobalStaticValues.currentCustomer.getId()); 
        GlobalStaticValues.customerFullCartQuantityList = cartRepo.getFullCartQuantityListByCustomerId(GlobalStaticValues.currentCustomer.getId());
        GlobalStaticValues.customerFullCartList = ValueRender.getCartListFromIdList(GlobalStaticValues.customerFullCartIdList, cartRepo);
        
        //init checked list with full of FALSE 
        boolean[] globalCheckedCartArr = GlobalStaticValues.customerCheckedCartList(GlobalStaticValues.customerFullSelectStatusList);
        
        customerCart.setCheckedList(globalCheckedCartArr);
        
        return getCartInfo(session, model, id, request, globalCheckedCartArr, customerCart);
    }
    
    
   @PostMapping("/cart-of-customer-account-id={id}")
    public String updateCart(HttpSession session, Model model, HttpServletRequest request, @RequestParam(value="action") String action, @ModelAttribute("customerCart") CustomerCart cusCart) {
    	boolean[] globalCheckedCartArr = null;
    	
    	if(action.equals("update")) {
    		updateCart(cusCart);
	    }
    	else {
    		deleteSelectedCart(Integer.parseInt(action), GlobalStaticValues.currentCustomer.getId());
    	}
    	
    	return getCartInfo(session, model, GlobalStaticValues.currentCustomer.getId(), request, globalCheckedCartArr, customerCart);
    }
}
