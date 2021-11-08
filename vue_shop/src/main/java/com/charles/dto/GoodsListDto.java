package com.charles.dto;

import java.util.List;

public class GoodsListDto extends PageInfo {
    private List<GoodsDto> goods;

    public List<GoodsDto> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsDto> goods) {
        this.goods = goods;
    }
}
