package com.charles.util;

import com.charles.entity.Category;

import java.util.List;

public class CategoryUtil {
    public static List<Category> getCategory(List<Category> categories) {
        for (int i = 0; i < categories.size(); i++) {
            Category category = categories.get(i);
            List<Category> children = category.getChildren();
            if (children.isEmpty()) {

                if (category.getLevel() == 3) {
                    categories.remove(category);
                    i--;
                }
            } else {
                getCategory(children);
            }
        }
        return categories;
    }
}
