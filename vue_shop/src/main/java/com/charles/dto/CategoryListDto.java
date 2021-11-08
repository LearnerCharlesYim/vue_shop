package com.charles.dto;

import java.util.List;

public class CategoryListDto extends PageInfo{

    List<CategoryDto> categories;

    public List<CategoryDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDto> categories) {
        this.categories = categories;
    }
}
