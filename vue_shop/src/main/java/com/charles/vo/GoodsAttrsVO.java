package com.charles.vo;

import com.charles.entity.GoodsAttrs;

import java.util.List;

public class GoodsAttrsVO {
    List<GoodsAttrs> attrs;

    public List<GoodsAttrs> getAttrs() {
        return attrs;
    }

    public void setAttrs(List<GoodsAttrs> attrs) {
        this.attrs = attrs;
    }

    @Override
    public String toString() {
        return "GoodsAttrsVO{" +
                "attrs=" + attrs +
                '}';
    }
}
