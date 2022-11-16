package com.example.demo.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Cart;

@Repository
public interface CartRemoveRepository extends JpaRepository<Cart, Integer>{
    @Modifying
    @Transactional
    @Query(value = "delete from cart where id = :idVal", nativeQuery = true)
    void deleteProductFromCartById(@Param("idVal") int cartId);
}
