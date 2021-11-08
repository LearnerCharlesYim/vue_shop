package com.charles.service;

import com.charles.dto.GoodsListDto;
import com.charles.entity.Goods;
import com.charles.entity.GoodsAttrs;
import com.charles.entity.Picture;

import java.util.List;

public interface GoodsService {
    GoodsListDto findByConditionAndPage(Goods goods, int pageNum, int pageSize);

    Goods save(Goods goods,List<Picture> pictureList,List<GoodsAttrs> goodsAttrsList,String categoryIds);

    void delete(Integer id);

    Goods findOneById(Integer id);

    Goods update(Goods goods,List<Picture> pictureList,List<GoodsAttrs> goodsAttrsList,String categoryIds);
}
