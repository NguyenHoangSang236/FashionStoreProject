package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.demo.entity.Account;
import com.example.demo.entity.Cart;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Product;
import com.example.demo.respository.CartRepository;
import com.example.demo.respository.CustomerRepository;
import com.example.demo.respository.ProductRepository;
import com.example.demo.util.GlobalStaticValues;

@SessionAttributes("currentuser")
@Controller
public class IndexPageController {
    @Autowired
    ProductRepository productRepo;
    Product product = new Product();
    
    @Autowired
	CustomerRepository cusRepo;
    
    @Autowired
    CartRepository cartRepo;
    
    Customer customer = new Customer();
    
    Integer[] ratingStarArr = {1,2,3,4,5};
    
    
    public void renderToHomePage(HttpSession session, Model model) {
    	List<Product> top8estSelllerProducts = productRepo.get8BestSellerProducts();  
	    List<Product> top8NewArrivalProducts = productRepo.get8NewArrivalProducts();
	    List<Product> top8HotSaleProducts = productRepo.get8HotSaleProducts();
	    
	    Account currentAccount = (Account)session.getAttribute("currentuser");
	    
	    if(currentAccount != null) {
	    Customer currentCustomer = cusRepo.getCustomerByAccountId(currentAccount.getId());
	    
	    model.addAttribute("curentcusImage",currentCustomer.convertByteImamgeToBase64String());
	    model.addAttribute("curentcusName",currentCustomer.getName());
	    
	    }

	    model.addAttribute("ratingStarArr", ratingStarArr);
        model.addAttribute("top8BestSellers", top8estSelllerProducts);
        model.addAttribute("top8NewArrivals", top8NewArrivalProducts);
        model.addAttribute("top8HotSaleProducts", top8HotSaleProducts);
	    
        GlobalStaticValues.currentPage = "/home";
    }
    
    
    public void addToCartOnClick(int productId, Customer customer) {
    	Product product = productRepo.getProductById(productId);
    	Cart avaiCart = cartRepo.getCartByProductIdAndCustomerId(product.getId(), GlobalStaticValues.currentCustomer.getId());
		
    	int cartId = cartRepo.getLastestCartId() + 1;
		int availableQuantity = productRepo.getAvailableQuantityById(product.getId());
		int cartQuantity = 1;
		
		//if this product has already been in the cart --> quantity = this product's quantity
		if(avaiCart != null) {
			cartQuantity += avaiCart.getQuantity();
			cartId = avaiCart.getId();
		}

		//if available quantity of product > selected product quantity --> save cart
		if(availableQuantity > avaiCart.getQuantity()) {
			avaiCart.setQuantity(cartQuantity);
			cartRepo.save(avaiCart);
			GlobalStaticValues.message = "Added 1 " + product.getColor() + " " + product.getName() + " with size " + product.getSize().toUpperCase() + " in your cart";
		} else {
			GlobalStaticValues.message = "Oops! We are having only " + String.valueOf(availableQuantity) + " available products";
			System.out.println(GlobalStaticValues.message);
		}
    }
    
    
	@GetMapping("/home")
	public String showHomePage(HttpSession session, Model model) {
	    renderToHomePage(session, model);
        
		return "index";
	}
	
	
	@PostMapping("/home")
	public String postHomePage(HttpSession session, Model model, @RequestParam(value = "action", required = false) String action) {
		if(action.contains("add to cart product id")) {
        	Account currentAcount = (Account)session.getAttribute("currentuser");
        	
        	if(currentAcount != null) {
        		int selectedId = Integer.parseInt(action.substring(action.indexOf("=") + 2));
        		Customer currentCustomer = cusRepo.getCustomerByAccountId(currentAcount.getId());
        		
        		addToCartOnClick(selectedId, currentCustomer);
        	}
        	else return "redirect:/loginpage";
        }
		
		renderToHomePage(session, model);
		
		return "index";
	}
	
}
