package com.charles.dto;

import com.charles.entity.Permission;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class PermissionListDto {
    private Integer pageNum;
    private Integer pageSize;
    private List<Permission> permissions;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
