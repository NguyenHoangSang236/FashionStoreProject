package com.example.demo.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Staff;

@Repository
public interface StaffRepository extends JpaRepository<Customer, Integer>{
    @Query(value = "select stf.* from staffs stf join login_accounts la on stf.account_id = la.id where user_name = :userName", nativeQuery = true)
    Staff getCurrentLoggedInStaff(@Param("userName") String userName);
    
    
}
