package com.charles.service;

import com.charles.dto.PermissionListDto;
import com.charles.entity.Permission;

import java.util.List;

public interface PermissionService {
    PermissionListDto findAllByPage(Integer pageNum, Integer pageSize);

    List<Permission> getSideBarMenu();
}
