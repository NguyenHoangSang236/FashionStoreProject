package com.example.demo.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> { 
	@Query(value = "select * from products p group by p.name", nativeQuery = true)
	List<Product> getAllProducts();
	
	
	@Query(value = "select p from Product p where p.name like %:nameVal%")
	List<Product> getProductsByName(@Param("nameVal") String productName);
	
	
//	@Query(value = "select * from products where brand = :brandVal")
//	List<Product> getProductsByBrand(@Param("brandVal") String brandName);
	
	
	@Query(value = "select * "
    	         + "from products p "
    	         + "order by p.sold_quantity "
    	         + "desc limit 8", nativeQuery = true)
	List<Product> get8BestSellerProducts();
	
	
	@Query(value = "select p.* "
    	         + "from products p join catalog_with_products cwp on p.name = cwp.product_name "
    	         + "                join catalog c on c.id = cwp.catalog_id "
    	         + "where c.name = :catalogName", nativeQuery = true)
	List<Product> getProductsUsingCatalogName(@Param("catalogName") String catalogName);
	
	
	@Query(value = "select * from products where price >= :moneyVal1 and price <= :moneyVal2", nativeQuery = true)
	List<Product> getProductsUsingPriceFilter(@Param("moneyVal1") double price1, @Param("moneyVal2") double price2);
	
	
}
