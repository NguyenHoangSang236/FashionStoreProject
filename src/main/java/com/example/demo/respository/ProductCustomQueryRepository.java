package com.example.demo.respository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Product;
import com.example.demo.util.GlobalStaticValues;

@Repository
public class ProductCustomQueryRepository {
	@PersistenceContext
    private EntityManager entityManager = GlobalStaticValues.entityManager;
    
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getFilteredProducts(String query) {
        return this.entityManager.createNativeQuery(query).getResultList();
    }
	
	
	@Transactional
	public void insertCatalogWithProducts(int catalogId, String productName) {
	    entityManager.createNativeQuery("INSERT INTO catalog_with_products (catalog_id, product_name) VALUES (?,?)")
	      .setParameter(1, catalogId)
	      .setParameter(2, productName)
	      .executeUpdate();
	}
}
