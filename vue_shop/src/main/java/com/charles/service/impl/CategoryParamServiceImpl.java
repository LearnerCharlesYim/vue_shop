package com.charles.service.impl;

import com.charles.dto.CategoryParamDto;
import com.charles.entity.Category;
import com.charles.entity.CategoryParam;
import com.charles.entity.QCategoryParam;
import com.charles.mapper.CategoryParamMapper;
import com.charles.repository.CategoryParamRepository;
import com.charles.repository.CategoryRepository;
import com.charles.service.CategoryParamService;
import com.charles.service.ex.ParamNotFoundException;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryParamServiceImpl implements CategoryParamService {

    @Resource
    private CategoryParamRepository categoryParamRepository;

    @Resource
    private CategoryRepository categoryRepository;

    @Resource
    private JPAQueryFactory jpaQueryFactory;

    @Override
    public CategoryParamDto save(CategoryParam categoryParam, Integer cid) {
        Category category = categoryRepository.getOne(cid);
        categoryParam.setId(-1);
        categoryParam.setCate(category);
        CategoryParam param = categoryParamRepository.save(categoryParam);

        return CategoryParamMapper.INSTANCES.toCategoryParamDto(param);
    }

    @Override
    public void delete(Integer cid) {
        categoryParamRepository.deleteById(cid);
    }

    @Override
    public List<CategoryParamDto> findByCondition(CategoryParam categoryParam, Integer cid) {
        QCategoryParam p = QCategoryParam.categoryParam;
        JPAQuery<CategoryParam> query = jpaQueryFactory.select(p).from(p);
        if (!StringUtils.isEmpty(categoryParam.getIsStatic()) && categoryParam.getIsStatic() != null) {
            query.where(p.isStatic.eq(categoryParam.getIsStatic()));
        }
        if (cid != null) {
            query.where(p.cate.id.eq(cid));
        }
        List<CategoryParam> categoryParams = query.fetch();
        List<CategoryParamDto> categoryParamDtos = new ArrayList<>();
        for (CategoryParam item : categoryParams) {
            categoryParamDtos.add(CategoryParamMapper.INSTANCES.toCategoryParamDto(item));
        }
        return categoryParamDtos;
    }

    @Override
    public CategoryParamDto findOne(Integer paramId) {
        try {
            CategoryParam param = categoryParamRepository.getOne(paramId);
            return CategoryParamMapper.INSTANCES.toCategoryParamDto(param);
        } catch (Exception e) {
            throw new ParamNotFoundException("参数未找到");
        }
    }

    @Transactional
    @Modifying
    @Override
    public CategoryParamDto update(CategoryParam categoryParam) {
        QCategoryParam p = QCategoryParam.categoryParam;
        JPAUpdateClause update = jpaQueryFactory.update(p);
        if (!StringUtils.isEmpty(categoryParam.getParam())) {
            update.set(p.param, categoryParam.getParam());
        }
        if(!StringUtils.isEmpty(categoryParam.getTag())){
            update.set(p.tag,categoryParam.getTag());
        }
        update.where(p.id.eq(categoryParam.getId())).execute();
        CategoryParam param = categoryParamRepository.getOne(categoryParam.getId());
        return CategoryParamMapper.INSTANCES.toCategoryParamDto(param);
    }
}
