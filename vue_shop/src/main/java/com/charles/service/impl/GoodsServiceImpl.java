package com.charles.service.impl;

import com.charles.dto.GoodsDto;
import com.charles.dto.GoodsListDto;
import com.charles.entity.*;
import com.charles.mapper.GoodsMapper;
import com.charles.repository.CategoryRepository;
import com.charles.repository.GoodsRepository;
import com.charles.service.GoodsService;
import com.charles.service.ex.CategoryNotFoundException;
import com.charles.service.ex.GoodsNotFoundException;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Resource
    private GoodsRepository goodsRepository;

    @Resource
    private CategoryRepository categoryRepository;

    @Resource
    private JPAQueryFactory jpaQueryFactory;

    @Override
    public GoodsListDto findByConditionAndPage(Goods goods, int pageNum, int pageSize) {
        QGoods g = QGoods.goods;
        JPAQuery<Goods> query = jpaQueryFactory.select(g).from(g);
        if (!StringUtils.isEmpty(goods.getId())) {
            query.where(g.id.eq(goods.getId()));
        }
        if (!StringUtils.isEmpty(goods.getName())) {
            query.where(g.name.like("%" + goods.getName() + "%"));
        }
        QueryResults<Goods> results = query.orderBy(g.createdTime.desc())
                .offset((long) (pageNum - 1) * pageSize)
                .limit(pageSize)
                .fetchResults();
        long totalCount = results.getTotal();
        List<Goods> goodsList = results.getResults();
        List<GoodsDto> list = new ArrayList<>();
        for (Goods _goods : goodsList) {
            GoodsDto goodsDto = GoodsMapper.INSTANCES.toGoodsDto(_goods);
            list.add(goodsDto);
        }
        GoodsListDto goodsListDto = new GoodsListDto();
        goodsListDto.setGoods(list);
        goodsListDto.setPageNum(pageNum);
        goodsListDto.setPageSize(pageSize);
        goodsListDto.setTotalNum(totalCount);
        goodsListDto.setTotalPage(
                (int) (totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize + 1))
        );
        return goodsListDto;
    }

    @Override
    public Goods save(Goods goods,
                      List<Picture> pictureList,
                      List<GoodsAttrs> goodsAttrsList,
                      String categoryIds) {
        goods.setId(-1);
        if (StringUtils.isEmpty(categoryIds)) {
            throw new CategoryNotFoundException("商品没有选择种类");
        }
        String[] strings = categoryIds.split(",");
        List<Category> categories = new ArrayList<>();
        for (String s : strings) {
            Category category = categoryRepository.getOne(Integer.parseInt(s));
            categories.add(category);
        }

        goods.setCategories(categories);
        goods.setPictures(pictureList);
        goods.setGoodsAttrs(goodsAttrsList);
        return goodsRepository.save(goods);

    }

    @Override
    public void delete(Integer id) {
        goodsRepository.deleteById(id);
    }

    @Override
    public Goods findOneById(Integer id) {
        Optional<Goods> option = goodsRepository.findById(id);
        if (!option.isPresent()) {
            throw new GoodsNotFoundException("没有该商品");
        }
        return option.get();
    }

    @Transactional
    @Modifying
    @Override
    public Goods update(Goods goods,
                        List<Picture> pictureList,
                        List<GoodsAttrs> goodsAttrsList,
                        String categoryIds) {
        QGoods g = QGoods.goods;
        JPAUpdateClause update = jpaQueryFactory.update(g);
        update.set(g.name, goods.getName())
                .set(g.price, goods.getPrice())
                .set(g.num, goods.getNum())
                .set(g.weight, goods.getWeight());
        if (!StringUtils.isEmpty(goods.getIntroduction())) {
            update.set(g.introduction, goods.getIntroduction());
        }
        update.where(g.id.eq(goods.getId())).execute();
        Optional<Goods> optional = goodsRepository.findById(goods.getId());
        if (!optional.isPresent()) {
            throw new GoodsNotFoundException("没有该商品");
        }
        String[] strings = categoryIds.split(",");
        List<Category> categories = new ArrayList<>();
        for (String s : strings) {
            Category category = categoryRepository.getOne(Integer.parseInt(s));
            categories.add(category);
        }
        Goods result = optional.get();
        result.setGoodsAttrs(goodsAttrsList);
        result.setPictures(pictureList);
        result.setCategories(categories);
        return goodsRepository.save(result);
    }
}
