package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.Catalog;
import com.example.demo.entity.Product;
import com.example.demo.respository.CatalogRepository;
import com.example.demo.respository.ProductRepository;

@Controller
public class AllProductsController {
    @Autowired
    ProductRepository productRepo;
    
   @Autowired
   CatalogRepository catalogRepo;
    
    
    @GetMapping("/allproduct")
    public String AllProduct(Model model) {
        List<Product> productsList = productRepo.getAllProductsWithColorsAndSizes();
        List<Catalog> cateList = catalogRepo.getAllCatalogs();
        
        model.addAttribute("productsList", productsList);
        model.addAttribute("cateList", cateList);
        
        return "products";
    }
}
