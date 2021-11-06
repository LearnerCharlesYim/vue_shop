package com.charles.repository;

import com.charles.entity.Permission;
import com.charles.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@SpringBootTest
public class RoleRepositoryTest {

    @Resource
    private RoleRepository roleRepository;
    @Resource
    private PermissionRepository permissionRepository;

    @Test
    public void saveTest() {
        Role role = new Role();
//        List<Permission> permissions = permissionRepository.findByLevel(1);
        Permission permission = permissionRepository.getOne(2);
        role.setId(-1);
        role.setRoleName("用户管理员");
        role.setRoleDesc("管理用户增删改查以及角色授权");
//        role.setPermissions(permissions);
        role.setPermissions(Collections.singletonList(permission));
        roleRepository.save(role);

    }


}
