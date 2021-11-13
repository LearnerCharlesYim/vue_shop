package com.charles.mapper;

import com.charles.dto.CategoryDto;
import com.charles.dto.LoginUserDto;
import com.charles.entity.Category;
import com.charles.entity.SysUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LoginUserMapper {
    LoginUserMapper INSTANCES = Mappers.getMapper(LoginUserMapper.class);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "username", target = "username"),
            @Mapping(source = "role.roleName", target = "roleName"),
            @Mapping(source = "phone",target = "phone"),
            @Mapping(source = "email",target = "email")
    })
    LoginUserDto toLoginUserDto(SysUser sysUser);

}
