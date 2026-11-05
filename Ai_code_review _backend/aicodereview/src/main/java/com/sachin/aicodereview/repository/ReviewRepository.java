package com.sachin.aicodereview.repository;


import com.sachin.aicodereview.entity.CodeReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<CodeReview,Long>{

}
