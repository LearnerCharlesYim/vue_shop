package com.charles.service;

import com.charles.entity.Goods;

import java.util.List;

public interface GoodsService {
    List<Goods> findByConditionAndPage(Object obj);

    void save(Goods goods);

    void delete(Integer id);
}
