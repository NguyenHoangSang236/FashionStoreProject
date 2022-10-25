package com.example.demo.respository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Account;

@Repository
public class AccountInsertRepository {
    @PersistenceContext
    private EntityManager entityManager;
    
    @Transactional
    public void insertNewAccount(Account account) {
        this.entityManager.persist(account);
    }
}
