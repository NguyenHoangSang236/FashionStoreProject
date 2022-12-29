package com.example.demo.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpSession;

import org.aspectj.weaver.ast.And;
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
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Account;
import com.example.demo.entity.Cart;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Product;
import com.example.demo.entity.dto.FilterSelections;
import com.example.demo.respository.CartRepository;
import com.example.demo.respository.CatalogRepository;
import com.example.demo.respository.CustomerRepository;
import com.example.demo.respository.ProductRepository;
import com.example.demo.service.ProductService;
import com.example.demo.util.GlobalStaticValues;
import com.example.demo.util.ValueRender;

@SessionAttributes("currentuser")
@Controller
public class ShopController {
    @Autowired
    ProductRepository productRepo;

    @Autowired
    ProductService productService;
    
    @Autowired
    CatalogRepository catalogRepo;
    
    @Autowired
	CustomerRepository cusRepo;
    
    @Autowired
    CartRepository cartRepo;
    
    Customer customer = new Customer();
    
    Integer[] ratingStarArr = {1,2,3,4,5};
    int totalResult = 0;
    
    FilterSelections filterSelections = new FilterSelections();

    
    //create a query for binding filters
    String createQueryForFilters(String[] catalogs, String brand, double price1, double price2) {
    	String result = "select p.* \r\n"
    			+ "from products p join catalog_with_products cwp on p.name = cwp.product_name\r\n"
    			+ "                join catalog c on c.id = cwp.catalog_id\r\n"
    			+ "where ";
    	String dynamicConditions = "";
    	int catalogsLength;

    	if(catalogs == null) {
    		catalogsLength = 0;
    	}
    	else catalogsLength = catalogs.length;
    	
    	if(brand != null) {
    		dynamicConditions += ("and brand = '" + brand + "' ");
    	}
    			
    	for(int i = 0; i < catalogsLength; i++) {
    		dynamicConditions += ("and c.name = '" + catalogs[i] + "' ");
    	}
    	
    	if(price1 >= 0 && price2 > 0) {
    		dynamicConditions += ("and price >= " + price1 + " and price <= " + price2 );
    	}
    	
    	dynamicConditions = dynamicConditions.substring(4);
    	result += dynamicConditions + " group by p.name";
    	
    	return result;
    }
    
    
    public void addToCartOnClick(int productId, Customer customer) {
    	Product product = productRepo.getProductById(productId);
    	Cart avaiCart = cartRepo.getCartByProductIdAndCustomerId(product.getId(), GlobalStaticValues.currentCustomer.getId());
		
    	int cartId = cartRepo.getLastestCartId() + 1;
		int availableQuantity = productRepo.getAvailableQuantityById(product.getId());
		int cartQuantity = 1;
		
		//if this product has already been in the cart --> quantity = this product's quantity
		if(avaiCart != null) {
			cartQuantity += avaiCart.getQuantity();
			cartId = avaiCart.getId();
		}

		//if available quantity of product > selected product quantity --> save cart
		if(availableQuantity > avaiCart.getQuantity()) {
			avaiCart.setQuantity(cartQuantity);
			cartRepo.save(avaiCart);
			GlobalStaticValues.message = "Added 1 " + product.getColor() + " " + product.getName() + " with size " + product.getSize().toUpperCase() + " in your cart";
		} else {
			GlobalStaticValues.message = "Oops! We are having only " + String.valueOf(availableQuantity) + " available products";
			System.out.println(GlobalStaticValues.message);
		}
		
    }
    
    
    //render form's data 
    void renderToShop(HttpSession session, Model model, Page<Product> pageination, Optional<Integer> page, Optional<Integer> size, FilterSelections filterSelections) {
        //pagination
        Page<Product> productPage = pageination;
        
        totalResult = (int) productPage.getTotalElements();

        Account Cuser = (Account)session.getAttribute("currentuser");
	    
	    if(Cuser != null) {
		    Customer Ccustomer = cusRepo.getCustomerByAccountId(Cuser.getId());
		    
		    model.addAttribute("curentcusImage",Ccustomer.convertByteImamgeToBase64String());
		    model.addAttribute("curentcusName",Ccustomer.getName());
	    }
        
	    model.addAttribute("filterSelections", filterSelections);
        model.addAttribute("productPage", productPage);
        model.addAttribute("ratingStarArr", ratingStarArr);
        model.addAttribute("totalResult", totalResult);
        
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
    public String showShop(HttpSession session,Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(15);
        GlobalStaticValues.currentPage = "shopproduct";
        
        model.addAttribute("pName", new Product());
        
        Page<Product> productPage = productService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        renderToShop(session, model, productPage, page, size, filterSelections);
        
        return "shop";
    }
    
    
    @PostMapping("/shopproduct")
    public String showFilteredShop(HttpSession session,
    		Model model, 
    		@RequestParam("page") Optional<Integer> page, 
    		@RequestParam("size") Optional<Integer> size, 
    		@ModelAttribute("filterSelections") FilterSelections selectedFilters,
    		@RequestParam(value = "action", required = false) String action) {
    	int currentPage = page.orElse(1);
        int pageSize = size.orElse(15);
        
        model.addAttribute("pName", new Product());

        if(action.contains("add to cart product id")) {
        	Account currentAcount = (Account)session.getAttribute("currentuser");
        	
        	if(currentAcount != null) {
        		int selectedId = Integer.parseInt(action.substring(action.indexOf("=") + 2));
        		Customer currentCustomer = cusRepo.getCustomerByAccountId(currentAcount.getId());
        		
        		addToCartOnClick(selectedId, currentCustomer);
        		
        		Page<Product> productPage = productService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
        		
        		renderToShop(session, model, productPage, page, size, filterSelections);
        	}
        	else return "redirect:/loginpage";
        }
        else if(action.contains("filter")){
        	if(selectedFilters.getBrand() == null && selectedFilters.getCatalogs().length == 0 && selectedFilters.getPriceRange() == null ) {
        		filterSelections = new FilterSelections();
        		Page<Product> productPage = productService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
        		
        		renderToShop(session, model, productPage, page, size, filterSelections);
        	}
        	else {
        		filterSelections = selectedFilters;
        		filterSelections.setPriceRangeLimits();
        		
        		String filterQuery = createQueryForFilters(filterSelections.getCatalogs(), filterSelections.getBrand(), filterSelections.getPrice1(), filterSelections.getPrice2());
        		
        		Page<Product> productPage = productService.findByFilters(PageRequest.of(currentPage - 1, pageSize), filterQuery);
        		
        		renderToShop(session, model, productPage, page, size, filterSelections);
        	}
		}
    	
    	return "shop";
    }
    
    
    @GetMapping("/shopproductsearch")
    public String showShopbyName(HttpSession session, Model model, @ModelAttribute("pName") Product name, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size ) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(15);
        System.out.println(name.getBrand());
        
        Page<Product> productPage = productService.searchProduct(PageRequest.of(currentPage - 1, pageSize),name.getName());
        
        renderToShop(session,model, productPage, page, size, filterSelections);
        
        return "shop";
    }
    
    
}
