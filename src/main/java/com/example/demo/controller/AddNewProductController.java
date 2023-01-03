package com.example.demo.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.demo.entity.Account;
import com.example.demo.entity.Catalog;
import com.example.demo.entity.dto.ProductInfo;
import com.example.demo.respository.CatalogRepository;
import com.example.demo.service.ProductService;
import com.example.demo.util.GlobalStaticValues;

@SessionAttributes("currentuser")
@Controller
public class AddNewProductController {
	@Autowired
	CatalogRepository catalogRepo;
	
	@Autowired
	ProductService productService;
	
	ProductInfo newProduct = new ProductInfo();
	Account currentAccount;
	
	
	public String renderToAddNewProduct(Model model, HttpSession session) {
		currentAccount = (Account)session.getAttribute("currentuser");
		
		if(currentAccount != null && currentAccount.getRole().equals("admin")) {
	    	List<Catalog> cateList = catalogRepo.getAllCatalogs();

	    	model.addAttribute("cateList", cateList);
			model.addAttribute("newProduct", newProduct);
			
			return "add-product";
		}
		else {
			GlobalStaticValues.currentPage = "/addproduct";
			return "redirect:/loginpage";
		}
	}
	
	
    @GetMapping("/addproduct")
    public String loadAddProductForm(Model model, HttpSession session) {
    	return renderToAddNewProduct(model, session);
    }
    
    
    @PostMapping("/addproduct")
    public String addProduct(Model model, HttpSession session, @ModelAttribute("newProduct") ProductInfo product) {
    	Date currentDate = new Date();
    	
    	//selling price < org price --> message
    	if(product.getSellingPrice() < product.getOriginalPrice()) {
    		GlobalStaticValues.message = "Original price must be lower than Selling price !!";
    		return "redirect:/addproduct";
    	}
    	//import date is later than current date --> message
    	else if (product.getImportDate().compareTo(currentDate) > 0) {
    		GlobalStaticValues.message = "This product has not been imported yet !!";
    		return "redirect:/addproduct";
		}
    	//still have others
    	else {
    		productService.addNewProduct(product);
    		
    		model.addAttribute("newProduct", newProduct);
    		
    		return "add-product";
		}
    }
}
