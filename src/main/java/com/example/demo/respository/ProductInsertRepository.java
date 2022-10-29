package com.example.demo.respository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Product;

public class ProductInsertRepository {
    @PersistenceContext
    private EntityManager entityManager;
    
    @Transactional
    public void insertNewProduct(Product product) {
        this.entityManager.persist(product);
    }
}
