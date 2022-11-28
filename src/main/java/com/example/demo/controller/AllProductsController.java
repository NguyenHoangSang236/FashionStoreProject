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
import com.example.demo.entity.dto.ProductManagement;
import com.example.demo.respository.CatalogRepository;
import com.example.demo.respository.ProductRepository;
import com.example.demo.service.CatalogService;
import com.example.demo.service.ProductService;
import com.example.demo.util.ValueRender;

@Controller
public class AllProductsController {
   @Autowired
   ProductRepository productRepo;
    
   @Autowired
   CatalogRepository catalogRepo;
   
   @Autowired
   CatalogService catalogService;
   
   @Autowired
   ProductService productService;

   ProductManagement productManagement = new ProductManagement();
   
    
    @GetMapping("/allproduct")
    public String AllProduct(Model model) {
        List<Product> productsList = productRepo.getAllProductsWithColorsAndSizes();
        List<Catalog> cateList = catalogRepo.getAllCatalogs();
        
        model.addAttribute("productsList", productsList);
        model.addAttribute("cateList", cateList);
        model.addAttribute("productManagement", productManagement);
                
        return "products";
    }
    
    
    @PostMapping("/allproduct")
    public String deleteSelectedProducts(Model model, @ModelAttribute("selectedProduct") ProductManagement productMng, @RequestParam(value="action") String action) {     
        if(action.equals("delete selected products")) {
        	List<Product> productsList = productRepo.getAllProductsWithColorsAndSizes();
            List<Catalog> cateList = catalogRepo.getAllCatalogs();
            
            model.addAttribute("productsList", productsList);
            model.addAttribute("cateList", cateList);
            model.addAttribute("productManagement", productManagement);
            
            for(int i = 0; i < productMng.getIdList().length; i++) {
                productService.deleteProduct(productMng.getIdList()[i]);
            }
        }
        else if(action.contains("delete catalog")) {
        	String catalogName = ValueRender.getSubstring(action, 15, action.length());
        	
        	catalogService.deleteCatalog(catalogName);
        }
        else if (action.equals("add a new catalog")) {
        	String newCatalogNameString = productMng.getNewCatalogName();
        	
        	if(catalogRepo.getCatalogByName(newCatalogNameString) == null) {
        		catalogRepo.save(new Catalog(newCatalogNameString));
        	}
        	
        	List<Product> productsList = productRepo.getAllProductsWithColorsAndSizes();
            List<Catalog> cateList = catalogRepo.getAllCatalogs();
            
            model.addAttribute("productsList", productsList);
            model.addAttribute("cateList", cateList);
            model.addAttribute("productManagement", productManagement);
        	
		}
        
        return "products";
    }
}
