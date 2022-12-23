package com.example.demo.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.demo.entity.Account;
import com.example.demo.entity.Catalog;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Product;
import com.example.demo.entity.dto.ProductInfo;
import com.example.demo.respository.CatalogRepository;
import com.example.demo.respository.ProductRepository;
import com.example.demo.service.ProductService;
import com.example.demo.util.ValueRender;

@SessionAttributes("currentuser")
@Controller
public class EditProductController {
	@Autowired
	ProductRepository productRepo;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	CatalogRepository catalogRepo;
	
	ProductInfo selectedProductInfo = new ProductInfo();
	Product selectedProduct = new Product();
	Account currentAccount;
	String[] catalogNameArr;
	Date importDate;
	
	
	public String renderEditProduct(Model model, HttpSession session) {
		currentAccount = (Account)session.getAttribute("currentuser");
		
		return "";
	}
	
	
	@GetMapping("/edit-product-id={id}")
    public String editSpecificProduct(Model model, HttpSession session, @PathVariable("id") int selectedProductId) {
		selectedProduct = productRepo.getProductById(selectedProductId);
		List<Catalog> cateList = catalogRepo.getAllCatalogs();
		List<Catalog> productCateList = catalogRepo.getCatalogsByProductName(selectedProduct.getName());
		
		boolean[] cateCheckedArr = new boolean[cateList.size()];
		int count = 0;
		
		for(int i = 0; i < cateList.size(); i++) {
			if(count < productCateList.size()) {
				if(cateList.get(i).getName().equals(productCateList.get(count).getName())) {
					cateCheckedArr[i] = true;
					count++;
				}
			}
			else {
				cateCheckedArr[i] = false;
			}
		}
		importDate = selectedProduct.getProductManagements().get(selectedProduct.getProductManagements().size() - 1).getImportDate();
		System.out.println(importDate);

		model.addAttribute("cateCheckedArr", cateCheckedArr);
		model.addAttribute("selectedProduct", selectedProduct);
    	model.addAttribute("cateList", cateList);
    	model.addAttribute("importDate", importDate);
		
        return "edit-specific-product";
    }
	
	
	@PostMapping("/edit-product-id={id}")
    public String editSpecificProductEvent(Model model, HttpSession session, @PathVariable("id") int selectedProductId, @RequestAttribute("selectedProduct") Product modelSelectedProduct) {
		List<Catalog> cateList = catalogRepo.getAllCatalogs();

    	model.addAttribute("cateList", cateList);
		model.addAttribute("selectedProduct", selectedProduct);
		
        return "edit-specific-product";
    }
	
	
	@GetMapping("/edit-product-name={name}__color={color}")
    public String editProduct(Model model, HttpSession session, @PathVariable("id") int selectedProductId) {
//		Product product = productRepo.getProductById(selectedProductId);
//		selectedProduct = productService.getProductInfo(product, "specific product mode");
//		selectedProduct = productService.getProductInfo(product, "specific product mode");
//		
//		model.addAttribute("selectedProduct", selectedProduct);
		
        return "edit-product";
    }

}
