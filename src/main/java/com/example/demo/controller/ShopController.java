package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Product;
import com.example.demo.respository.Product.ProductRepository;

@Controller
public class ShopController {
    @Autowired
    ProductRepository productRepo;
  
    
    @GetMapping("/shopproduct")
    public String showShop(Model model) {
        List<Product> productsList = productRepo.getAllProducts();
        
        model.addAttribute("productsList", productsList);
        
        return "shop";
    }
}
