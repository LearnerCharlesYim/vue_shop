package com.charles.service;

import com.charles.dto.CategoryParamDto;
import com.charles.entity.CategoryParam;

import java.util.List;

public interface CategoryParamService {
    CategoryParamDto save(CategoryParam categoryParam, Integer cid);

    void delete(Integer cid);

    List<CategoryParamDto> findByCondition(CategoryParam categoryParam,Integer cid);

    CategoryParamDto findOne(Integer paramId);

    CategoryParamDto update(CategoryParam categoryParam);
}
