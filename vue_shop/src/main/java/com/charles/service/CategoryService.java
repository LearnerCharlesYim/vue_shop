package com.charles.service;

import com.charles.dto.CategoryDto;
import com.charles.dto.CategoryListDto;
import com.charles.entity.Category;

import java.util.List;

public interface CategoryService {
    CategoryListDto findAllByPage(int pageNum,int pageSize);

    CategoryDto save(Category category);

    CategoryDto update(Category category);

    void delete(Integer id);

    CategoryDto findOne(int id);

    List<CategoryDto> getExcludeLevel3();

    List<CategoryDto> findAll();

}
