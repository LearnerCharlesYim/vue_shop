package com.charles.service;

import com.charles.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAllByPage();

    void save(Category category);

    void delete(Integer id);

}
