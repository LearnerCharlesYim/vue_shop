package com.charles.dto;

import com.charles.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MenuMapper {
    MenuMapper INSTANCES = Mappers.getMapper(MenuMapper.class);

    @Mappings({
            @Mapping(source = "authName", target = "authName"),
            @Mapping(source = "path", target = "path"),
            @Mapping(source = "priority", target = "priority"),
            @Mapping(source = "children",target = "children")
    })
    MenuDto toMenuDto(Permission permission);

}
