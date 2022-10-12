package com.example.demo.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	@Query(value = "select p from Product p")
	List<Product> getAllProducts();
	
	@Query(value = "select p from Product p where p.name like %:nameVal%")
	List<Product> getProductsByName(@Param("nameVal") String productName);
}
