package com.example.demo.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.entity.Account;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Product;
import com.example.demo.respository.CatalogRepository;
import com.example.demo.respository.ProductRepository;
import com.example.demo.service.ProductService;
import com.example.demo.util.ValueRender;

@Controller
public class EditProductController {
	
	@GetMapping("/allproduct")
	public String AllProduct(Model model) {
		return "products";
	}
	
	@GetMapping("/addproduct")
	public String addproduct(Model model) {
		return "add-product";
	}
	
	@GetMapping("/editproduct")
    public String editproduct(Model model ) {
		
        return "edit-product";
    }

}
