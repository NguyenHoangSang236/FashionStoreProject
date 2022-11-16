package com.example.demo.service.serviceImpl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Comment;
import com.example.demo.entity.Product;
import com.example.demo.respository.CommentRepository;
import com.example.demo.respository.ProductRemoveRepository;
import com.example.demo.respository.ProductRepository;
import com.example.demo.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepo;
    
    @Autowired
    private ProductRemoveRepository productRemoveRepo;
    
    @Autowired
    private CommentRepository commentRepo;
    
    private List<Product> products;
    
    @Override
    public Page<Product> findPaginated(Pageable pageable) {
        products = productRepo.getAllProducts();
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startProduct = pageSize * currentPage;
        List<Product> list;
        
        if(products.size() < startProduct) {
            list = Collections.emptyList();
        }
        else {
            int toIndex = Math.min(startProduct + pageSize, products.size());
            list = products.subList(startProduct, toIndex);
        }
        
        return new PageImpl<Product>(list, PageRequest.of(currentPage, pageSize), products.size());
    }
    
    
    @Override
    public Page<Product> findByPriceFilter(Pageable pageable, double price1, double price2 ) {
        products = productRepo.getProductsUsingPriceFilter(price1, price2);
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startProduct = pageSize * currentPage;
        List<Product> list;
        
        if(products.size() < startProduct) {
            list = Collections.emptyList();
        }
        else {
            int toIndex = Math.min(startProduct + pageSize, products.size());
            list = products.subList(startProduct, toIndex);
        }
        
        return new PageImpl<Product>(list, PageRequest.of(currentPage, pageSize), products.size());
    }
    
    
    @Override
    public Page<Product> findByCate(Pageable pageable, String cate ) {
        products = productRepo.getProductsUsingCatalogName(cate);
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startProduct = pageSize * currentPage;
        List<Product> list;
        
        if(products.size() < startProduct) {
            list = Collections.emptyList();
        }
        else {
            int toIndex = Math.min(startProduct + pageSize, products.size());
            list = products.subList(startProduct, toIndex);
        }
        
        return new PageImpl<Product>(list, PageRequest.of(currentPage, pageSize), products.size());
    }
    
    
    @Override
    public Page<Product> findByBrand(Pageable pageable, String Brand ) {
        products = productRepo.getProductsByBrand(Brand);
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startProduct = pageSize * currentPage;
        List<Product> list;
        
        if(products.size() < startProduct) {
            list = Collections.emptyList();
        }
        else {
            int toIndex = Math.min(startProduct + pageSize, products.size());
            list = products.subList(startProduct, toIndex);
        }
        
        return new PageImpl<Product>(list, PageRequest.of(currentPage, pageSize), products.size());
    }
    
    
    @Override
    public Page<Product> searchProduct(Pageable pageable, String Name ) {
        products = productRepo.getProductsByName(Name);
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startProduct = pageSize * currentPage;
        List<Product> list;
        
        if(products.size() < startProduct) {
            list = Collections.emptyList();
        }
        else {
            int toIndex = Math.min(startProduct + pageSize, products.size());
            list = products.subList(startProduct, toIndex);
        }
        
        return new PageImpl<Product>(list, PageRequest.of(currentPage, pageSize), products.size());
    }


    @Override
    public void deleteProduct(int productId) {
        Optional<Product> optProduct = productRepo.findById(productId);
        Optional<Comment> optComment = commentRepo.getCommentByProductId(productId);
        String productName;
        
        if(optProduct.isPresent()) {
            productName = optProduct.get().getName();
            if(optComment.isPresent()) {
                productId = optComment.get().getProduct().getId();
                productRemoveRepo.deleteFromProductComments(productId);
            }
            
            productRemoveRepo.deleteProductFromCatalogWithProducts(productName);
            
            productRepo.deleteById(productId);
        }
    }
    
}
