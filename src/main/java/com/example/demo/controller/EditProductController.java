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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.demo.entity.Account;
import com.example.demo.entity.Catalog;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Product;
import com.example.demo.entity.ProductManagement;
import com.example.demo.entity.dto.ProductInfo;
import com.example.demo.respository.CatalogRepository;
import com.example.demo.respository.ProductManagementRepository;
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
	
	@Autowired
	ProductManagementRepository productMngRepo;
	
	ProductInfo selectedProductInfo = new ProductInfo();
	Product selectedProduct = new Product();
	Account currentAccount;
	String[] catalogNameArr;
	Date importDate;
	
	
	public String renderEditProduct(Model model, HttpSession session, int selectedProductId) {
		selectedProduct = productRepo.getProductById(selectedProductId);
		currentAccount = (Account)session.getAttribute("currentuser");
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
		importDate = productMngRepo.getLastestProductManagementInfoByProductId(selectedProductId).getImportDate();
		System.out.println(importDate);

		model.addAttribute("cateCheckedArr", cateCheckedArr);
		model.addAttribute("selectedProduct", selectedProduct);
    	model.addAttribute("cateList", cateList);
    	model.addAttribute("importDate", importDate);
		
        return "edit-specific-product";
	}
	
	
	@GetMapping("/edit-product-id={id}")
    public String editSpecificProduct(Model model, HttpSession session, @PathVariable("id") int selectedProductId) {
		return renderEditProduct(model, session, selectedProductId);
    }
	
	
	@PostMapping("/edit-product-id={id}")
    public String editSpecificProductEvent(Model model, HttpSession session, 
    		@PathVariable("id") int selectedProductId, 
    		@ModelAttribute("selectedProduct") Product modelSelectedProduct,
    		@ModelAttribute("importDate") Date modelImportDate) {
		modelSelectedProduct.setId(selectedProductId);
		ProductManagement pm = productMngRepo.getLastestProductManagementInfoByProductId(selectedProductId);
		pm.setImportDate(modelImportDate);
		
		productRepo.save(modelSelectedProduct);
		productMngRepo.save(pm);
		
		return renderEditProduct(model, session, selectedProductId);
    }
	
	
	@GetMapping("/edit-product-name={name}__color={color}")
    public String editProduct(Model model, HttpSession session, @PathVariable("name") String selectedProductName,@PathVariable("color") String selectedProductColor ) {
//		Product product = productRepo.getProductById(selectedProductId);
//		selectedProduct = productService.getProductInfo(product, "specific product mode");
//		selectedProduct = productService.getProductInfo(product, "specific product mode");
//		
//		model.addAttribute("selectedProduct", selectedProduct);

        return "edit-general-product";
    }

	
//	@PostMapping("/edit-product-id={id}")
//    public String editSpecificProductEvent(Model model, HttpSession session, 
//    		@PathVariable("id") int selectedProductId, 
//    		@ModelAttribute("selectedProduct") Product modelSelectedProduct,
//    		@ModelAttribute("importDate") Date modelImportDate) {
//		System.out.println(modelSelectedProduct.getName());
//		System.out.println(modelSelectedProduct.getBrand());
//		System.out.println(modelSelectedProduct.getColor());
//		System.out.println(modelSelectedProduct.getPrice());
//		System.out.println(modelSelectedProduct.getOriginalPrice());
//		System.out.println(modelSelectedProduct.getDescription());
//		System.out.println(modelSelectedProduct.getAvailableQuantity());
//		System.out.println(modelSelectedProduct.getSize());
//		System.out.println(modelImportDate);
//		
//		String modelSelectedProductSize = modelSelectedProduct.getSize();
//		String modelSelectedProductColor = modelSelectedProduct.getColor();
//		String modelSelectedProductName = modelSelectedProduct.getName();
//		Product testProduct = productRepo.getProductDetailsByNameAndColorAndSize(modelSelectedProductName, modelSelectedProductColor, modelSelectedProductSize);
//		
//		if(testProduct != null) {
////			for(int i = 0; i < modelSelectedProduct.getCatalogs().size(); i++) {
////				System.out.println(modelSelectedProduct.getCatalogs().get(i).getName());
////			}
//			modelSelectedProduct.setId(selectedProductId);
//			ProductManagement pm = productMngRepo.getLastestProductManagementInfoByProductId(selectedProductId);
//			pm.setImportDate(modelImportDate);
//			
//			productRepo.save(modelSelectedProduct);
//			productMngRepo.save(pm);
//			System.out.println("saved !!");
//		}
//		
//		return renderEditProduct(model, session, selectedProductId);
//    }
}
