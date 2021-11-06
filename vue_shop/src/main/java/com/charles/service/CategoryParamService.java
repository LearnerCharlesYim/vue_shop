package com.charles.service;

import com.charles.entity.CategoryParam;

import java.util.List;

public interface CategoryParamService {
    List<CategoryParam> findByCateId(Integer id);

    void save(CategoryParam categoryParam);

    void delete(Integer id);


}
