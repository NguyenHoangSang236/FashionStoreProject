package com.example.demo.controller;

import java.util.ArrayList;
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

import com.example.demo.entity.Comment;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Product;
import com.example.demo.entity.dto.ProductComment;
import com.example.demo.respository.CommentRepository;
import com.example.demo.respository.CustomerRepository;
import com.example.demo.respository.ProductRepository;
import com.example.demo.util.ValueRender;


@Controller
public class DetailsController {
    @Autowired
    ProductRepository productRepo;
    
    @Autowired 
    CommentRepository commentRepo;
    
    @Autowired
    CustomerRepository customerRepo;
    
    Integer[] ratingStarArr = {1,2,3,4,5};
    
    
    public void renderToProductDetails(Model model, String realProductName, Product productDetail) {
        List<Comment> comments = commentRepo.getCommentByProductName(realProductName);
        
        List<String> sizeList = productRepo.getAllSizesOfProductByName(realProductName);
        List<String> colorList = productRepo.getAllColorsOfProductByName(realProductName);
        List<String> cateList = productRepo.getAllCatalogsByProductName(realProductName);
        List<ProductComment> commentList = new ArrayList<ProductComment>();
        
        if(comments.size() > 0) {            
            for(int i = 0; i < comments.size(); i++) {
                Customer customer = customerRepo.getCustomerById(comments.get(i).getCustomer().getId());
                
                if(customer != null) {
                    String cusName = customer.getName();
                    String cusImgUrl = customer.getImage();
                    String content = comments.get(i).getContent();
                    
                    System.out.println(comments.get(i).getId() + " " + cusName + " " + content + " " + cusImgUrl + " " + comments.size());
                    
                    commentList.add(new ProductComment(cusName, content, cusImgUrl));
                }
                else {
                    System.out.println(comments.get(i).getProduct().getId());
                }
            }
        }
        
        model.addAttribute("productDetail", productDetail);
        model.addAttribute("ratingStarArr", ratingStarArr);
        model.addAttribute("sizeList", sizeList);
        model.addAttribute("colorList", colorList);
        model.addAttribute("cateList", cateList);
        model.addAttribute("commentList", commentList);
    }
    
    
    @GetMapping("/shop-details_name={productName}")
    public String showDefaultProductDetails(Model model, @PathVariable("productName") String productName) {
        String realProductName = ValueRender.linkToString(productName);
        Product productDetail = productRepo.getDefaultProductDetailsByName(realProductName);

        renderToProductDetails(model, realProductName, productDetail);

        return "shopdetails";
    }
    
    
    @GetMapping("/shop-details-by-color_name={productName}__color={color}")
    public String showProductDetails(Model model, @PathVariable("productName") String productName, @PathVariable("color") String color) {
        String realProductName = ValueRender.linkToString(productName);
        Product productDetail = productRepo.getProductByNameAndColor(realProductName, color);

        renderToProductDetails(model, realProductName, productDetail);
        
        return "shopdetails";
    }
}
