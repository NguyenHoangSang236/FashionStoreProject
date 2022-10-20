package com.example.demo.respository.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>{
    @Query(value = "select cus.* from customers cus join login_accounts la on cus.account_id = la.id where user_name = :userName", nativeQuery = true)
    Customer getCurrentLoggedInCustomer(@Param("userName") String userName);
    
    
}
