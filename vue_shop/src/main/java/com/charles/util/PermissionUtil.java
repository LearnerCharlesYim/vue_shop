package com.charles.util;

import com.charles.entity.Permission;
import com.charles.entity.Role;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取角色权限工具类
 */
public class PermissionUtil {
    /**
     * @param role 获取某个角色所有的一级权限
     * @return 返回一级权限集合
     */
    public static List<Permission> getLevelOnePermissions(Role role) {
        List<Permission> permissions = role.getPermissions();
        for (int i = 0; i < permissions.size(); i++) {
            Permission permission = permissions.get(i);
            if (permission.getLevel() != 1) {
                permissions.remove(permission);
                i--;
            }
        }
        return permissions;
    }

    /**
     * @param permissions 去重，将一级权限下角色不具有的权限删除
     * @param ids         该角色权限id集合
     */
    public static void distinct(List<Permission> permissions, List<Integer> ids) {
        for (int i = 0; i < permissions.size(); i++) {
            Permission permission = permissions.get(i);
            if (!ids.contains(permission.getId())) {
                permissions.remove(permission);
                i--;
            } else {
                if (permission.getChildren().isEmpty()) {
                    Integer id = permission.getId();
                    if (!ids.contains(id)) {
                        permissions.remove(permission);
                        i--;
                    }
                } else {
                    //递归，直到permission无children
                    distinct(permission.getChildren(), ids);
                }
            }
        }
    }

    /**
     *
     * @param  role 角色对象
     * @return 返回角色所具有权限id数组
     */
    public static List<Integer> getPermissionIds(Role role) {
        List<Integer> list = new ArrayList<>();
        role.getPermissions().forEach(permission -> {
            Integer id = permission.getId();
            list.add(id);
        });
        return list;
    }

    /**
     * 封装角色权限
     * @param role 角色对象
     * @return 返回角色对象
     */
    public static Role getRole(Role role) {
        List<Integer> ids = getPermissionIds(role);
        List<Permission> levelOnePermissions = getLevelOnePermissions(role);
        distinct(levelOnePermissions, ids);
        role.setPermissions(levelOnePermissions);
        return role;
    }
}
