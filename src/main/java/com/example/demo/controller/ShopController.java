package com.example.demo.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Product;
import com.example.demo.respository.CatalogRepository;
import com.example.demo.respository.ProductRepository;
import com.example.demo.service.ProductService;
import com.example.demo.util.ValueRender;

@Controller
public class ShopController {
    @Autowired
    ProductRepository productRepo;

    @Autowired
    ProductService productService;
    
    @Autowired
    CatalogRepository catalogRepo;
    
    Integer[] ratingStarArr = {1,2,3,4,5};

    
    //render form's data 
    //purpose: avoid repeating codes
    void renderToShop(Model model, Page<Product> pageination, Optional<Integer> page, Optional<Integer> size) {
        //pagination
        Page<Product> productPage = pageination;
                        
        model.addAttribute("productPage", productPage);
        model.addAttribute("ratingStarArr", ratingStarArr);
        
        int totalPages = productPage.getTotalPages();
        if(totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        
        
        //branding
        List<String> brandingList = productRepo.getAllProductBrands();
        model.addAttribute("brandingList", brandingList);
        
        //categories
        List<String> categoriesList = catalogRepo.getAllCatalogsName();
        model.addAttribute("categoriesList", categoriesList);
    }
    
    
    @GetMapping("/shopproduct")
    public String showShop(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(12);
        
        Page<Product> productPage = productService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        renderToShop(model, productPage, page, size);
        
        return "shop";
    }
    

    @GetMapping("/shopproduct{price1}and{price2}")
    public String showShopbyPrice(Model model, @PathVariable("price1") double price1, @PathVariable("price2") double price2, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size ) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(12);
        
        Page<Product> productPage = productService.findByPriceFilter(PageRequest.of(currentPage - 1, pageSize),price1,price2);
        
        renderToShop(model, productPage, page, size);
        
        return "shop";
    }
    
    
    @GetMapping("/shopproductcate={cate}")
    public String showShopbyCate(Model model, @PathVariable("cate") String cate, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size ) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(12);
        
        Page<Product> productPage = productService.findByCate(PageRequest.of(currentPage - 1, pageSize),cate);
        
        renderToShop(model, productPage, page, size);
        
        return "shop";
    }
    
    
    @GetMapping("/shopproductbrand={brand}")
    public String showShopbyBrand(Model model, @PathVariable("brand") String brand, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size ) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(12);
        
        Page<Product> productPage = productService.findByBrand(PageRequest.of(currentPage - 1, pageSize),brand);
        
        renderToShop(model, productPage, page, size);
        
        return "shop";
    }
    
}
