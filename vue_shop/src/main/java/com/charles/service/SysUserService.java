package com.charles.service;

import com.charles.entity.SysUser;

import java.util.List;

public interface SysUserService {
    List<SysUser> findByConditionAndPage(Object obj);

    void save(SysUser sysUser);

    void updateState(SysUser sysUser);

    void delete(Integer id);

    void setRole(SysUser sysUser);
}
