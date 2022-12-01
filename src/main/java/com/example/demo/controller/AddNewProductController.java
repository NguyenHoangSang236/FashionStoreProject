package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.dto.NewProductInfo;

@Controller
public class AddNewProductController {
	NewProductInfo newProduct = new NewProductInfo();
	
	
    @GetMapping("/addproduct")
    public String loadAddProductForm(Model model) {
    	model.addAttribute("newProduct", newProduct);
    	
        return "add-product";
    }
    
    
    @PostMapping("/addproduct")
    public String addProduct(Model model, @RequestParam("newProduct") NewProductInfo product) {
    	
    	
    	model.addAttribute("newProduct", newProduct);
    	
        return "add-product";
    }
}
