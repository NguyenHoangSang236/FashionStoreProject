package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Product;
import com.example.demo.respository.ProductRepository;
import com.example.demo.util.ValueRender;


@Controller
public class DetailsController {
    @Autowired
    ProductRepository productRepo;
    
    Integer[] ratingStarArr = {1,2,3,4,5};
    
    
    @GetMapping("/shop-details_name={productName}")
    public String showDefaultProductDetails(Model model, @PathVariable("productName") String productName) {
        String realProductName = ValueRender.linkToString(productName);
        Product productDetail = productRepo.getDefaultProductDetailsByName(realProductName);
        
        List<String> sizeList = productRepo.getAllSizesOfProductByName(realProductName);
        List<String> colorList = productRepo.getAllColorsOfProductByName(realProductName);
        List<String> cateList = productRepo.getAllCatalogsByProductName(realProductName);

//        System.out.println(productName);
                
        model.addAttribute("productDetail", productDetail);
        model.addAttribute("ratingStarArr", ratingStarArr);
        model.addAttribute("sizeList", sizeList);
        model.addAttribute("colorList", colorList);
        model.addAttribute("cateList", cateList);

        
        return "shopdetails";
    }
    
    
    @GetMapping("/shop-details-by-color_name={productName}__color={color}")
    public String showProductDetails(Model model, @PathVariable("productName") String productName, @PathVariable("color") String color) {
        String realProductName = ValueRender.linkToString(productName);
        Product productDetail = productRepo.getProductByNameAndColor(realProductName, color);
        
        List<String> sizeList = productRepo.getAllSizesOfProductByName(realProductName);
        List<String> colorList = productRepo.getAllColorsOfProductByName(realProductName);
        List<String> cateList = productRepo.getAllCatalogsByProductName(realProductName);
        
        model.addAttribute("productDetail", productDetail);
        model.addAttribute("ratingStarArr", ratingStarArr);
        model.addAttribute("sizeList", sizeList);
        model.addAttribute("colorList", colorList);
        model.addAttribute("cateList", cateList);
        
        return "shopdetails";
    }
}
