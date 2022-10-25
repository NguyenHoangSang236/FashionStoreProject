package com.example.demo.service.serviceImpl;

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
import com.example.demo.respository.ProductRepository;
import com.example.demo.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;
    
    private List<Product> products;
    
    @Override
    public Page<Product> findPaginated(Pageable pageable) {
        products = productRepository.getAllProducts();
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
}
