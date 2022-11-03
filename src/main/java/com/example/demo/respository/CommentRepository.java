package com.example.demo.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>{
//    @Query(value = "select * from comments c join products p on c.product_")
//    List<Comment> getCommentByProductName();
}
