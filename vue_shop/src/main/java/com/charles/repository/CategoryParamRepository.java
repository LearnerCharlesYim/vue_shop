package com.charles.repository;

import com.charles.entity.CategoryParam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryParamRepository extends JpaRepository<CategoryParam,Integer>{
    List<CategoryParam> findByIsStaticOrderByCreatedTimeDesc(Boolean isStatic);

}
