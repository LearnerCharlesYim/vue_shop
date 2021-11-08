package com.charles.service.impl;

import com.charles.dto.RoleDto;
import com.charles.entity.Permission;
import com.charles.entity.QRole;
import com.charles.entity.Role;
import com.charles.mapper.RoleMapper;
import com.charles.repository.PermissionRepository;
import com.charles.repository.RoleRepository;
import com.charles.service.RoleService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleRepository roleRepository;

    @Resource
    private PermissionRepository permissionRepository;

    @Resource
    private JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Role> list() {
        return roleRepository.findAll(Sort.by(Sort.Direction.DESC,"createdTime"));
    }

    @Override
    public RoleDto findById(Integer id) {
        Role role = roleRepository.getOne(id);
        return RoleMapper.INSTANCES.toRoleDto(role);
    }

    @Override
    @Transactional
    @Modifying
    public RoleDto update(Role role) {
        QRole r = QRole.role;
        JPAUpdateClause update = jpaQueryFactory.update(r);
        if(!StringUtils.isEmpty(role.getRoleName())){
            update.set(r.roleName,role.getRoleName());
        }
        if(!StringUtils.isEmpty(role.getRoleDesc())){
            update.set(r.roleDesc,role.getRoleDesc());
        }
        update.where(r.id.eq(role.getId())).execute();
        Role one = roleRepository.getOne(role.getId());
        return RoleMapper.INSTANCES.toRoleDto(one);
    }

    @Override
    public void setPermission(Integer roleId, String rids) {
        Role role = roleRepository.getOne(roleId);
        if(StringUtils.isEmpty(rids)){
            role.setPermissions(null);
            roleRepository.save(role);
            return;
        }
        String[] strings = rids.split(",");
        List<Permission> permissions = new ArrayList<>();
        for (String s : strings) {
            Permission permission = permissionRepository.getOne(Integer.parseInt(s));
            permissions.add(permission);
        }
        role.setPermissions(permissions);
        roleRepository.save(role);
    }

    @Override
    public RoleDto save(Role role) {
        Role result = roleRepository.save(role);
        return RoleMapper.INSTANCES.toRoleDto(result);
    }

    @Override
    public void delete(Integer id) {
        roleRepository.deleteById(id);
    }
}
