package com.charles.dto;

import com.charles.entity.Permission;

import java.util.List;

public class PermissionListDto  extends PageInfo{

    private List<Permission> permissions;

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
