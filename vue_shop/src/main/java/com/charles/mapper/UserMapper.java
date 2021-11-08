package com.charles.mapper;

import com.charles.dto.UserDto;
import com.charles.entity.SysUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCES = Mappers.getMapper(UserMapper.class);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "username", target = "username"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "state",target = "state"),
            @Mapping(source = "phone",target = "phone"),
            @Mapping(source = "createdTime",target = "createdTime"),
            @Mapping(source = "role.roleName",target = "roleName"),
            @Mapping(source = "role.id",target = "roleId")
    })
    UserDto toUserDto(SysUser sysUser);

}
