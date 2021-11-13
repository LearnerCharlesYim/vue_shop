package com.charles.repository;

import com.charles.dto.CategoryDto;
import com.charles.entity.Category;
import com.charles.mapper.CategoryMapper;
import com.charles.util.CategoryUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class CategoryRepositoryTest {

    @Resource
    private CategoryRepository categoryRepository;

    @Resource
    private ObjectMapper objectMapper;

    @Test
    public void test() throws JsonProcessingException {
        List<Category> categories = categoryRepository.findByLevel(1);
        List<Category> categoryList = CategoryUtil.getCategory(categories);
        List<CategoryDto> cate = new ArrayList<>();
        categoryList.forEach(category -> {
            CategoryDto categoryDto = CategoryMapper.INSTANCES.toCategoryDto(category);
            cate.add(categoryDto);
        });

        System.out.println(objectMapper.writeValueAsString(cate));
    }
}
