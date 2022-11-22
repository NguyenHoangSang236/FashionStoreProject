package com.example.demo.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer>{
    @Query(value = "select * from cart where customer_id = :idVal and buying_status = 0", nativeQuery = true)
    List<Cart> getCurrentCartByCustomerId(@Param("idVal") int id);
    
    
    @Query(value = "select * from cart where id = :idVal", nativeQuery = true)
    Cart getCartById(@Param("idVal") int id);
    
    
    @Query(value = "select id from cart where customer_id = :idVal and buying_status = 0", nativeQuery = true)
    int[] getFullCartIdListByCustomerId(@Param("idVal") int id);
}
