package com.example.demo.service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Product;
import com.example.demo.entity.dto.ProductInfo;
import com.example.demo.respository.ProductRepository;

@Service
public interface ProductService {  
    Page<Product> findPaginated(Pageable pageable);
    Page<Product> findByFilters(Pageable pageable, String query);
    Page<Product> searchProduct(Pageable pageable, String Name );
    void deleteProduct(int productId);
    void ratingProduct(int ratingPoint, String name, String color);
    void addNewProduct(ProductInfo newProductInfo);
    void editGeneralProduct(ProductInfo initProductInfo, ProductInfo modelProductInfo);
    ProductInfo getProductInfo(String productName, String color);
    Product getProduct(int id, ProductInfo productInfo);
}
