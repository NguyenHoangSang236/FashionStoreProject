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
	Account findByUserNameAndPassword(@Param("userNameVal") String userName, @Param("passwordVal") String password);
	
	@Query("select a from Account a where a.userName = :userNameVal")
	Account findByUserName(@Param("userNameVal") String userName);
	
	@Query(value = "select * from login_accounts ", nativeQuery = true)
	List<Account> findAllAccount();
	
	@Query(value = "select la.* from login_accounts la join customers c on la.id = c.account_id where role = 'user'", nativeQuery = true)
	List<Account> getAllCustomerAccounts();
	
	@Query("select a from Account a where a.id = :idVal")
	Account findByUserID(@Param("idVal") int id);
}
