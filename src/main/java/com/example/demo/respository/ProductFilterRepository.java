package com.example.demo.respository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import com.example.demo.entity.Product;
import com.example.demo.util.GlobalStaticValues;

@Repository
public class ProductFilterRepository {
	@PersistenceContext
    private EntityManager entityManager = GlobalStaticValues.entityManager;
    
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getFilteredProducts(String query) {
        return this.entityManager.createNativeQuery(query).getResultList();
    }
}
