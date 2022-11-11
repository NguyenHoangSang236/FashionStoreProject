package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Catalog;
import com.example.demo.entity.Product;
import com.example.demo.entity.dto.SelectedProductID;
import com.example.demo.respository.CatalogRepository;
import com.example.demo.respository.ProductRepository;
import com.example.demo.service.ProductService;

@Controller
public class AllProductsController {
   @Autowired
   ProductRepository productRepo;
    
   @Autowired
   CatalogRepository catalogRepo;
   
//   @Autowired 
//   ProductRemoveRepository productRemoveRepo;
   
   @Autowired
   ProductService productService;
   
   SelectedProductID selectedProductID = new SelectedProductID();
//   List<Product> selectedProductList;
   
    
    
    @GetMapping("/allproduct")
    public String AllProduct(Model model) {
        List<Product> productsList = productRepo.getAllProductsWithColorsAndSizes();
        List<Catalog> cateList = catalogRepo.getAllCatalogs();
        
        model.addAttribute("productsList", productsList);
        model.addAttribute("cateList", cateList);
        model.addAttribute("selectedProduct", selectedProductID);
                
//        Product testProduct = new Product("color", "size", "name", 10, 8, 10, 10, 0, 0, 0, 0, 0, 0, "brand", null, null, null, null, null, null, null, null, null, null);
//        productRepo.save(testProduct);
       
        
        
        return "products";
    }
    
    
    @PostMapping("/allproduct")
    public String deleteSelectedProducts(Model model, @ModelAttribute("selectedProduct") SelectedProductID productID) {     
        List<Product> productsList = productRepo.getAllProductsWithColorsAndSizes();
        List<Catalog> cateList = catalogRepo.getAllCatalogs();
        
        model.addAttribute("productsList", productsList);
        model.addAttribute("cateList", cateList);
        model.addAttribute("selectedProduct", selectedProductID);
        
        for(int i = 0; i < productID.getIdList().length; i++) {
            productService.deleteProduct(productID.getIdList()[i]);
        }
        
        return "products";
    }
}
