package com.charles.mapper;

import com.charles.dto.CategoryParamDto;
import com.charles.entity.CategoryParam;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryParamMapper {
    CategoryParamMapper INSTANCES = Mappers.getMapper(CategoryParamMapper.class);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "param", target = "name"),
            @Mapping(source = "isStatic", target = "isStatic"),
            @Mapping(source = "cate.id",target = "categoryId"),
            @Mapping(source = "tag",target = "tag")
    })
    CategoryParamDto toCategoryParamDto(CategoryParam categoryParam);

}
