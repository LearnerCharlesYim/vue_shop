package com.charles.mapper;

import com.charles.dto.RoleDto;
import com.charles.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleMapper {
    RoleMapper INSTANCES = Mappers.getMapper(RoleMapper.class);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "roleName", target = "roleName"),
            @Mapping(source = "roleDesc", target = "roleDesc"),
    })
    RoleDto toRoleDto(Role role);

}
