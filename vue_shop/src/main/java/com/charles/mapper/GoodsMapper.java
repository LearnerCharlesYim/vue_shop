package com.charles.mapper;

import com.charles.dto.GoodsDto;
import com.charles.entity.Goods;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GoodsMapper {
    GoodsMapper INSTANCES = Mappers.getMapper(GoodsMapper.class);

    @Mappings({
            @Mapping(source = "id", target = "goodsId"),
            @Mapping(source = "name", target = "goodsName"),
            @Mapping(source = "price", target = "goodsPrice"),
            @Mapping(source = "num",target = "goodsNumber"),
            @Mapping(source = "weight",target = "goodsWeight"),
            @Mapping(source = "state",target = "goodsState"),
            @Mapping(source = "createdTime",target = "createdTime"),
            @Mapping(source = "modifiedTime",target = "modifiedTime")
    })
    GoodsDto toGoodsDto(Goods goods);

}
