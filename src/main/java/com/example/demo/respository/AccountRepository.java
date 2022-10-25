package com.example.demo.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
	@Query("select a from Account a where a.userName = :userNameVal and a.password = :passwordVal")
	List<Account> findByUserNameAndPassword(@Param("userNameVal") String userName, @Param("passwordVal") String password);
}
