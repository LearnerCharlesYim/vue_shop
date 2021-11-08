package com.charles.service;

import com.charles.dto.UserDto;
import com.charles.dto.UserListDto;
import com.charles.entity.SysUser;

public interface SysUserService {
    UserListDto findByConditionAndPage(SysUser sysUser, Integer pageNum, Integer pageSize);

    UserDto save(SysUser sysUser);

    UserDto update(SysUser sysUser);

    void delete(Integer id);

    UserDto setRole(Integer id,Integer roleId);

    UserDto setState(Integer id , Boolean type);

    UserDto findOneById(Integer id);
}
