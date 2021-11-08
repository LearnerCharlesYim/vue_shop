package com.charles.mapper;

import com.charles.dto.PermissionLDto;
import com.charles.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PermissionMapper {
    PermissionMapper INSTANCES = Mappers.getMapper(PermissionMapper.class);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "authName", target = "authName"),
            @Mapping(source = "level", target = "level"),
            @Mapping(source = "parentId", target = "parentId"),
            @Mapping(source = "path", target = "path"),
    })
    PermissionLDto toPermissionDto(Permission permission);

}
