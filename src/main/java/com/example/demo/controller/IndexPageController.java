package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.demo.entity.Account;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Product;
import com.example.demo.respository.CustomerRepository;
import com.example.demo.respository.ProductRepository;

@SessionAttributes("currentuser")
@Controller
public class IndexPageController {
    @Autowired
    ProductRepository productRepo;
    Product product = new Product();
    
    @Autowired
	CustomerRepository cusRepo;
    Customer customer = new Customer();
    
    Integer[] ratingStarArr = {1,2,3,4,5};
    
//    @ModelAttribute("currentuser")
//    public Account getUserObject() {
//        return new Account();
//
//    }
	@GetMapping("/home")
	public String showHomePage(HttpSession session, Model model) {
	    List<Product> top8estSelllerProducts = productRepo.get8BestSellerProducts();  
	    List<Product> top8NewArrivalProducts = productRepo.get8NewArrivalProducts();
	    List<Product> top8HotSaleProducts = productRepo.get8HotSaleProducts();
	    
	    Account Cuser = (Account)session.getAttribute("currentuser");
	    
	    if(Cuser != null) {
	    Customer Ccustomer = cusRepo.getCustomerByAccountId(Cuser.getId());
	    
	    model.addAttribute("curentcusImage",Ccustomer.getImage());
	    model.addAttribute("curentcusName",Ccustomer.getName());
	    
	    }
//	    Account Cuser = getUserObject();
//	     
//	    System.out.println(Cuser.getUserName());
	    
//	    if(Cuser.getUserName() == null) {
//	    model.addAttribute("currentuser", "login");
//	    }
//	    else {
//	    model.addAttribute("currentuser", Cuser.getUserName());
//	    }
	    model.addAttribute("ratingStarArr", ratingStarArr);
        model.addAttribute("top8BestSellers", top8estSelllerProducts);
        model.addAttribute("top8NewArrivals", top8NewArrivalProducts);
        model.addAttribute("top8HotSaleProducts", top8HotSaleProducts);

        
//        System.out.println(top8estSelllerProducts.get(0).getImage2());
//        System.out.println(ValueRender.formatDoubleNumber(top8estSelllerProducts.get(0).getPrice()));
	    
		return "index";
	}
	
//	@PostMapping("/home")
//	public String renderDataToHome(@ModelAttribute("Product") Product product) {
//	    top6estSelllerProducts = productRepo.get6BestSellerProducts();
//	    
//      return "index";
//	}
}
