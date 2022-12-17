package com.example.demo.service.serviceImpl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Comment;
import com.example.demo.entity.Product;
import com.example.demo.respository.CartRemoveRepository;
import com.example.demo.respository.CommentRepository;
import com.example.demo.respository.ProductFilterRepository;
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
    private CartRemoveRepository cartRemoveRepo;
    
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
        Product product = productRepo.getProductById(productId);
        String productName = product.getName();
        
        productRemoveRepo.deleteFromProductComments(productId);
        cartRemoveRepo.deleteProductFromCartByProductId(productId);
        productRemoveRepo.deleteProductFromCatalogWithProducts(productName);
        
        productRepo.deleteById(productId);
    }


	@Override
	public Page<Product> findByFilters(Pageable pageable, String query) {
		ProductFilterRepository filterRepo = new ProductFilterRepository();
		
		List<Object[]> objList = filterRepo.getFilteredProducts(query);
		products = new ArrayList<Product>();
		Iterator<Object[]> iterator = objList.iterator();
		
		while(iterator.hasNext()) {
			Object[] line = (Object[]) iterator.next();
		    Product product = new Product();
		    
//		    for(int i = 0; i < line.length; i++) {
//		    	System.out.println(line[i].toString());
//		    }
		    
		    product.setId(((BigInteger)line[0]).intValue());
		    product.setName((String)line[1]);
		    product.setPrice((double)line[2]);
		    product.setOriginalPrice((double)line[3]);
		    product.setColor((String)line[4]);
		    product.setSize((String)line[5]);
		    product.setAvailableQuantity((int)line[6]);
		    product.setSoldQuantity(((BigInteger)line[7]).intValue());
		    product.setOneStarQuantity((int)line[8]);
		    product.setTwoStarQuantity((int)line[9]);
		    product.setThreeStarQuantity((int)line[10]);
		    product.setFourStarQuantity((int)line[11]);
		    product.setFiveStarQuantity((int)line[12]);
		    product.setBrand((String)line[13]);
		    product.setDiscount((double)line[14]);
		    product.setImage1((String)line[15]);
		    product.setImage2((String)line[16]);
		    product.setImage3((String)line[17]);
		    product.setImage4((String)line[18]);
		    product.setDescription((String)line[19]);
		    
		    products.add(product);
		}
		
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
	public void ratingProduct(int ratingPoint, String name, String color) {
		List<Product> productList = productRepo.getProductListByNameAndColor(name, color);
		
		for(int i = 0; i < productList.size(); i++) {
			switch (ratingPoint) {
				case 1: {
					int num = productList.get(i).getOneStarQuantity();
					productList.get(i).setOneStarQuantity(num + 1);
					break;
				}
				case 2: {
					int num = productList.get(i).getTwoStarQuantity();
					productList.get(i).setTwoStarQuantity(num + 1);
					break;
				}
				case 3: {
					int num = productList.get(i).getThreeStarQuantity();
					productList.get(i).setThreeStarQuantity(num + 1);
					break;
				}
				case 4: {
					int num = productList.get(i).getFourStarQuantity();
					productList.get(i).setFourStarQuantity(num + 1);
					break;
				}
				case 5: {
					int num = productList.get(i).getFiveStarQuantity();
					productList.get(i).setFiveStarQuantity(num + 1);
					break;
				}
	    	}
			productRepo.save(productList.get(i));
		}
	}

    
//    @SuppressWarnings("unchecked")
//	@Override
//    public List<Product> findByFilters(Pageable pageable, String[] catalogs, String brand, double price1, double price2) {
//		Page<Product> page = productFilterRepo.findAll(new Specification<Product>() {
//			@Override
//			public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//				List<Predicate> predicates = new ArrayList<>();
//				if(catalogs != null) {
//					for(int i = 0; i < catalogs.length; i++) {
//						predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("c.name"), catalogs[])));
//					}
//				}
//				
//                if(brand != null) {
//                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("brand"), brand)));
//                }
//                
//                if(price1 != 0) {
//                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("price1"), price1)));
//                }
//                
//                if(price2 != 0) {
//                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("price2"), price2)));
//                }
//                
//                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
//			}
//		}, pageable);
//		page.getTotalElements();        // get total elements
//        page.getTotalPages();           // get total pages
//        return page.getContent();  
//	};
}
