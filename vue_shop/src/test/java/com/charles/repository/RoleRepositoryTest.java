package com.charles.repository;

import com.charles.entity.Permission;
import com.charles.entity.Role;
import com.charles.service.RoleService;
import com.charles.util.PermissionUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootTest
public class RoleRepositoryTest {

    @Resource
    private RoleRepository roleRepository;
    @Resource
    private PermissionRepository permissionRepository;

    @Resource
    private JPAQueryFactory jpaQueryFactory;
    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private RoleService roleService;

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

    @Test
    public void test333() throws JsonProcessingException {
        List<Role> roleList = roleRepository.findAll(Sort.by(Sort.Direction.DESC, "createdTime"));
        roleList.forEach(role -> {
            List<Permission> permissions = role.getPermissions();
            List<Permission> list = new ArrayList<>();
            permissions.forEach(permission -> {
                if (permission.getLevel() == 1) {
                    list.add(permission);
                }
            });
            role.setPermissions(list);
        });

        System.out.println(objectMapper.writeValueAsString(roleList));
    }

    @Test
    public void test666() throws JsonProcessingException {
        Role role = roleRepository.getOne(20);
        List<Permission> permissions = role.getPermissions();
        for (int i = 0; i < permissions.size(); i++) {
            System.out.println(permissions.remove(permissions.get(i)));
            i--;
        }
        System.out.println(objectMapper.writeValueAsString(permissions));
    }

    @Test
    public void test888() throws JsonProcessingException {
        Role role = roleRepository.getOne(20);
        List<Permission> permissions = role.getPermissions();
//        System.out.println(objectMapper.writeValueAsString(permissions));
        permissions.forEach(permission -> {
            if(permission.getChildren().isEmpty()){
                System.out.println(permission.getChildren());
            }
        });
    }

    @Test
    public void test999() throws JsonProcessingException {
        Role role = roleRepository.getOne(13);
        Role r = PermissionUtil.getRole(role);
        System.out.println(objectMapper.writeValueAsString(r));

    }

}
