package com.charles.service;

import com.charles.dto.RoleDto;
import com.charles.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> list();

    RoleDto findById(Integer id);

    RoleDto update(Role role);

    void setPermission(Integer rodeId, String rids);

    RoleDto save(Role role);

    void delete(Integer id);

}
