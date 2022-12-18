package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Catalog;
import com.example.demo.entity.dto.NewProductInfo;
import com.example.demo.respository.CatalogRepository;

@Controller
public class AddNewProductController {
	NewProductInfo newProduct = new NewProductInfo();
	
	@Autowired
	CatalogRepository catalogRepo;
	
    @GetMapping("/addproduct")
    public String loadAddProductForm(Model model) {
    	model.addAttribute("newProduct", newProduct);
    	List<Catalog> cateList = catalogRepo.getAllCatalogs();
    	model.addAttribute("cateList", cateList);
        return "add-product";
    }
    
    
    @PostMapping("/addproduct")
    public String addProduct(Model model, @RequestParam("newProduct") NewProductInfo product) {
    	
    	
    	model.addAttribute("newProduct", newProduct);
    	
        return "add-product";
    }
}
