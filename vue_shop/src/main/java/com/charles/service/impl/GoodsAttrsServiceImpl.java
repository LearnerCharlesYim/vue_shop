package com.charles.service.impl;

import com.charles.entity.GoodsAttrs;
import com.charles.repository.GoodsAttrsRepository;
import com.charles.service.GoodsAttrsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class GoodsAttrsServiceImpl implements GoodsAttrsService {

    @Resource
    private GoodsAttrsRepository goodsAttrsRepository;

    @Override
    public GoodsAttrs save(GoodsAttrs goodsAttrs) {
        return goodsAttrsRepository.save(goodsAttrs);
    }

    @Override
    public GoodsAttrs update(GoodsAttrs attr) {
        if(attr.getAttrId() == null){
            return goodsAttrsRepository.save(attr);
        }
        return attr;
    }
}
