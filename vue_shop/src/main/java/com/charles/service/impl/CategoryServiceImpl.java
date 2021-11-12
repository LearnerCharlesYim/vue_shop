package com.charles.service.impl;

import com.charles.dto.CategoryDto;
import com.charles.dto.CategoryListDto;
import com.charles.entity.Category;
import com.charles.entity.QCategory;
import com.charles.mapper.CategoryMapper;
import com.charles.repository.CategoryRepository;
import com.charles.service.CategoryService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryRepository categoryRepository;

    @Resource
    private JPAQueryFactory jpaQueryFactory;

    @Override
    public CategoryListDto findAllByPage(int pageNum, int pageSize) {
        int offset = (pageNum - 1) * pageSize;
        List<Category> categories = categoryRepository.findCategoriesByLevelAndPage(offset,pageSize);
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Category category : categories) {
            categoryDtos.add(CategoryMapper.INSTANCES.toCategoryDto(category));
        }
        CategoryListDto categoryListDto = new CategoryListDto();
        Integer count = categoryRepository.getCountByLevel();

        categoryListDto.setCategories(categoryDtos);
        categoryListDto.setPageNum(pageNum);
        categoryListDto.setPageSize(pageSize);
        categoryListDto.setTotalPage(
                count % pageSize == 0 ? count / pageSize : (count / pageSize + 1)
        );
        categoryListDto.setTotalNum(count.longValue());
        return categoryListDto;
    }

    @Override
    public CategoryDto save(Category category) {
        Category result = categoryRepository.save(category);
        return CategoryMapper.INSTANCES.toCategoryDto(result);
    }

    @Override
    @Transactional
    @Modifying
    public CategoryDto update(Category category) {
        QCategory c = QCategory.category;
        JPAUpdateClause update = jpaQueryFactory.update(c);
        update.set(c.name, category.getName()).where(c.id.eq(category.getId())).execute();
        Category result = categoryRepository.getOne(category.getId());
        return CategoryMapper.INSTANCES.toCategoryDto(result);
    }

    @Override
    public void delete(Integer id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryDto findOne(int id) {
        Category category = categoryRepository.getOne(id);
        return CategoryMapper.INSTANCES.toCategoryDto(category);
    }

}
