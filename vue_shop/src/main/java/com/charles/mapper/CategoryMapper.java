package com.charles.mapper;

import com.charles.dto.CategoryDto;
import com.charles.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {
    CategoryMapper INSTANCES = Mappers.getMapper(CategoryMapper.class);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "parentId", target = "parentId"),
            @Mapping(source = "level",target = "level"),
            @Mapping(source = "children",target = "children"),
            @Mapping(source = "state",target = "state")
    })
    CategoryDto toCategoryDto(Category category);

}
