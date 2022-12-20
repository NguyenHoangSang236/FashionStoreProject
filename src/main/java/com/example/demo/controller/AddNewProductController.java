package com.example.demo.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Catalog;
import com.example.demo.entity.dto.NewProductInfo;
import com.example.demo.respository.CatalogRepository;
import com.example.demo.service.ProductService;

@Controller
public class AddNewProductController {
	@Autowired
	CatalogRepository catalogRepo;
	
	@Autowired
	ProductService productService;
	
	NewProductInfo newProduct = new NewProductInfo();
	
    @GetMapping("/addproduct")
    public String loadAddProductForm(Model model) {
    	List<Catalog> cateList = catalogRepo.getAllCatalogs();
    	
    	model.addAttribute("newProduct", newProduct);
    	model.addAttribute("cateList", cateList);
    	
    	int[] quantityArr = {1,2,3,4};
    	String[] sizeArr = {"X", "M", "L", "XL"};
    	String[] cateArr = {"Shoes", "Jackets"};
    	
    	NewProductInfo test = new NewProductInfo(
    			"test", 
    			sizeArr, 
    			quantityArr, 
    			"Nike", 
    			"red", 
    			20, 
    			15, 
    			"asfasgaSGASgasgasg",
    			cateArr, 
    			new Date(), 
    			"http://t0.gstatic.com/licensed-image?q=tbn:ANd9GcRaHOjF0WYP8MwqkqHU1UD0j_56bTDXrsZnLxm_xGo4w06dskJFONOZqMG9HLLvQqTU4sqVJfbvGJ0aMqmjWG8", 
    			"http://t0.gstatic.com/licensed-image?q=tbn:ANd9GcRaHOjF0WYP8MwqkqHU1UD0j_56bTDXrsZnLxm_xGo4w06dskJFONOZqMG9HLLvQqTU4sqVJfbvGJ0aMqmjWG8", 
    			"http://t0.gstatic.com/licensed-image?q=tbn:ANd9GcRaHOjF0WYP8MwqkqHU1UD0j_56bTDXrsZnLxm_xGo4w06dskJFONOZqMG9HLLvQqTU4sqVJfbvGJ0aMqmjWG8",
    			"http://t0.gstatic.com/licensed-image?q=tbn:ANd9GcRaHOjF0WYP8MwqkqHU1UD0j_56bTDXrsZnLxm_xGo4w06dskJFONOZqMG9HLLvQqTU4sqVJfbvGJ0aMqmjWG8");
    	
    	productService.addNewProduct(test);
    	
        return "add-product";
    }
    
    
    @PostMapping("/addproduct")
    public String addProduct(Model model, @ModelAttribute("newProduct") NewProductInfo product) {
    	
    	
    	
    	System.out.println(product.getSizeList());
    	String[] sizeStrings = product.getSizeList();
    	
    	for (int element: product.getAvailableQuantityList()) {
            System.out.println(element);
        }
    	//model.addAttribute("newProduct", newProduct);
    	
        return "add-product";
    }
}
