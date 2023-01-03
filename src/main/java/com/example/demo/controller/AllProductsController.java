package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.demo.entity.Account;
import com.example.demo.entity.Catalog;
import com.example.demo.entity.Product;
import com.example.demo.entity.dto.ProductManagementDto;
import com.example.demo.respository.CatalogRepository;
import com.example.demo.respository.ProductRepository;
import com.example.demo.service.CatalogService;
import com.example.demo.service.ProductService;
import com.example.demo.util.GlobalStaticValues;
import com.example.demo.util.ValueRender;

@SessionAttributes("currentuser")
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

   ProductManagementDto productManagement = new ProductManagementDto();
   String announcement = "";
   Account currentAccount;
   
   
   public String renderToAllProducts(Model model, HttpSession session) {
	   currentAccount = (Account)session.getAttribute("currentuser");
	   System.out.println(currentAccount.getRole());
	   
	   if(currentAccount != null && currentAccount.getRole().equals("admin")) {
			List<Product> productsList = productRepo.getAllProductsWithColorsAndSizes();
	        List<Catalog> cateList = catalogRepo.getAllCatalogs();
	        
	        model.addAttribute("productsList", productsList);
	        model.addAttribute("cateList", cateList);
	        model.addAttribute("productManagement", productManagement);
			
	        return "products";
		}
		else {
			GlobalStaticValues.currentPage = "/allproduct";
			return "redirect:/loginpage";
		}
   }
   
   
    @GetMapping("/allproduct")
    public String allProduct(Model model, HttpSession session) {
        return renderToAllProducts(model, session);
    }
    
    
    @PostMapping("/allproduct")
    public String delete(Model model, HttpSession session, @ModelAttribute("selectedProduct") ProductManagementDto productMng, @RequestParam(value="action") String action) {     
    	if(action.equals("delete selected products")) {
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
		}
    	
        return renderToAllProducts(model, session);
    }
}
