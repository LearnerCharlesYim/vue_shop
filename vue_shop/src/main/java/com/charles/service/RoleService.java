package com.charles.service;

import com.charles.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAllByPage();

    void setPermission(Role role);

    void save(Role role);

    void delete(Integer id);



}
