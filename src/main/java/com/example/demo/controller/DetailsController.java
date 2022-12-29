package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.demo.entity.Account;
import com.example.demo.entity.Cart;
import com.example.demo.entity.Comment;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Product;
import com.example.demo.entity.dto.ShopDetails;
import com.example.demo.entity.dto.ProductComment;
import com.example.demo.respository.CartRepository;
import com.example.demo.respository.CommentRepository;
import com.example.demo.respository.CustomerRepository;
import com.example.demo.respository.ProductRepository;
import com.example.demo.service.ProductService;
import com.example.demo.util.GlobalStaticValues;
import com.example.demo.util.ValueRender;

@SessionAttributes("currentuser")
@Controller
public class DetailsController {
    @Autowired
    ProductRepository productRepo;
    
    @Autowired 
    CommentRepository commentRepo;
    
    @Autowired
    CustomerRepository customerRepo;
    
    @Autowired
    CartRepository cartRepo;
    
    @Autowired
    ProductService productService;
    
    Product currentProduct;
    ShopDetails shopDetails = new ShopDetails();
    ShopDetails ratingShopDetails = new ShopDetails();
    Comment newComment = new Comment();
    int defaultQuantity = 1;
    int[] voteStarsArr = {1,2,3,4,5};
    
    
    public void renderToProductDetails(HttpSession session, 
    		Model model, 
    		String realProductName, 
    		Product productDetail, 
    		HttpServletRequest request,
    		ShopDetails shopDetails) {
    	
        List<Comment> comments = commentRepo.getCommentsByProductNameAndColor(realProductName, productDetail.getColor());
        List<String> sizeList = productRepo.getAllSizesOfProductByNameAndColor(realProductName, productDetail.getColor());
        List<String> colorList = productRepo.getAllColorsOfProductByName(realProductName);
        List<String> cateList = productRepo.getAllCatalogsByProductName(realProductName);
        List<ProductComment> commentList = new ArrayList<ProductComment>();
        
        if(comments.size() > 0) {            
            for(int i = 0; i < comments.size(); i++) {
                Customer customer = customerRepo.getCustomerById(comments.get(i).getCustomer().getId());
                
                if(customer != null) {
                    String cusName = customer.getName();
                    String cusImgUrl = customer.convertByteImamgeToBase64String();
                    String content = comments.get(i).getContent();
                    
                    commentList.add(new ProductComment(cusName, content, cusImgUrl));
                }
            }
        }
        
        Account currentAcount = (Account)session.getAttribute("currentuser");
	    
	    if(currentAcount != null) {
		    Customer currentCustomer = customerRepo.getCustomerByAccountId(currentAcount.getId());
		    
	        newComment.setCustomer(currentCustomer);
	        newComment.setProduct(productDetail);
		    
		    model.addAttribute("curentcusImage",currentCustomer.convertByteImamgeToBase64String());
		    model.addAttribute("curentcusName",currentCustomer.getName());
		    model.addAttribute("userid", currentCustomer.getId());
	    }
    	
        model.addAttribute("defaultQuantity", defaultQuantity);
        model.addAttribute("voteStarsArr", voteStarsArr);
        model.addAttribute("productDetail", productDetail);
        model.addAttribute("sizeList", sizeList);
        model.addAttribute("colorList", colorList);
        model.addAttribute("cateList", cateList);
        model.addAttribute("commentList", commentList);
        model.addAttribute("shopDetails", shopDetails);
        model.addAttribute("ratingShopDetails", ratingShopDetails);
        model.addAttribute("newComment", newComment);
    }
    
    
    @GetMapping("/shop-details-by-color_name={productName}__color={color}")
    public String showProductDetails(HttpSession session,Model model, 
    		@PathVariable("productName") String productName,
    		@PathVariable("color") String color, 
    		HttpServletRequest request) {
    	
    	String realProductName = ValueRender.linkToString(productName);
        currentProduct = productRepo.getProductByNameAndColor(realProductName, color);
        GlobalStaticValues.currentPage = "/shop-details-by-color_name=" + productName + "__color=" + color;
        
        renderToProductDetails(session, model, realProductName, currentProduct, request, shopDetails);
        
        return "shopdetails";
    }
    
    
    @PostMapping("/shop-details-by-color_name={productName}__color={color}")
    public String addToCartFromColorDetailPage(
    		@RequestParam(value="action", required = false) String action, 
    		HttpSession session, Model model , 
    		@PathVariable("color") String color,
    		@PathVariable("productName") String productName, 
    		HttpServletRequest request, 
    		@ModelAttribute("productDetail") Product productDetail, 
    		@ModelAttribute("shopDetails") ShopDetails modelShopDetails, 
    		@ModelAttribute("newComment") Comment modelNewComment) {
    	
    	String realProductName = ValueRender.linkToString(productName);
    	
    	if(action == null) {
        	int starNumber = modelShopDetails.getVotedStars();
        	productService.ratingProduct(starNumber, realProductName, color);
        	currentProduct = productRepo.getProductByNameAndColor(realProductName, color);
        }
    	else if(action.equals("logged in - add to cart")) {
        	if(modelShopDetails.getProductSize() == null) {
        		currentProduct = productRepo.getProductByNameAndColor(realProductName, color);
            	GlobalStaticValues.message = "Please choose a size first !!";
            	String message = GlobalStaticValues.message;
	        	model.addAttribute("message", message);
            }
        	else {
        		int id = cartRepo.getLastestCartId() + 1;
        		int quantity = 0;
        		
        		Account currentAcount = (Account)session.getAttribute("currentuser");
        		Product product = productRepo.getProductDetailsByNameAndColorAndSize(realProductName, color, modelShopDetails.getProductSize());
        		int availableQuantity = productRepo.getAvailableQuantityById(product.getId());
        		
        		Cart tmpCart = cartRepo.getCartByProductIdAndCustomerId(product.getId(), GlobalStaticValues.currentCustomer.getId());
        		
        		//if this product has already been in the cart --> quantity = this product's quantity
        		if(tmpCart != null) {
        			quantity = tmpCart.getQuantity();
        			id = tmpCart.getId();
        		}
        		
        		//if available quantity of product > selected product quantity --> save cart
        		if(availableQuantity > quantity + modelShopDetails.getQuantity()) {
        			Customer customer = customerRepo.getCustomerByAccountId(currentAcount.getId());
        			Cart newCart = new Cart(id, quantity + modelShopDetails.getQuantity(), 0, 0, customer, product);
        			cartRepo.save(newCart);
        			GlobalStaticValues.message = "Thank you for buying";
			    	String message = GlobalStaticValues.message;
		        	model.addAttribute("message", message);
        		} else {
        			GlobalStaticValues.message = "Oops! We are having only " + String.valueOf(availableQuantity) + " available products";
        			String message = GlobalStaticValues.message;
		        	model.addAttribute("message", message);
        		}
        	}
        }
        else if(action.equals("post new comment")) {
        	String newCommentContent = modelNewComment.getContent();
        	newComment.setContent(newCommentContent);
        	newComment.setCommentDate(new Date());
        	newComment.setId(commentRepo.getLastestCommentId() + 1);
        	
        	commentRepo.save(newComment);
        }
        else if(action.equals("need to login")) {
        	return "redirect:/loginpage";
        }
        
        renderToProductDetails(session, model, realProductName, currentProduct, request, shopDetails);
    	
    	return "shopdetails";
    }
}
