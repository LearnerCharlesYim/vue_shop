package com.charles.service.impl;

import com.charles.dto.PermissionLDto;
import com.charles.dto.PermissionListDto;
import com.charles.entity.Permission;
import com.charles.mapper.PermissionMapper;
import com.charles.repository.PermissionRepository;
import com.charles.service.PermissionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Resource
    private PermissionRepository permissionRepository;

    @Override
    public PermissionListDto findAllByPage(Integer pageNum, Integer pageSize) {

        PageRequest pageRequest = PageRequest.of((pageNum - 1) * pageSize
                , pageSize, Sort.Direction.ASC
                , "priority");

        Page<Permission> page = permissionRepository.findAll((Specification<Permission>) (root, criteriaQuery, cb) -> {
            Predicate predicate = cb.conjunction();
            predicate.getExpressions().add(cb.equal(root.get("level"), 1));
            return predicate;
        }, pageRequest);

        List<Permission> permissions = page.getContent();
        PermissionListDto permissionListDto = new PermissionListDto();
        permissionListDto.setPageNum(pageNum);
        permissionListDto.setPageSize(pageSize);
        permissionListDto.setTotalPage(page.getTotalPages());
        permissionListDto.setPermissions(permissions);

        return permissionListDto;
    }

    @Override
    public List<Permission> getSideBarMenu() {
        List<Permission> permissions = permissionRepository.findByLevelOrderByPriorityAsc(1);
        for (Permission permission : permissions) {
            List<Permission> children = permission.getChildren();
            for (Permission child : children) {
                child.setChildren(null);
            }
        }
        return permissions;
    }

    @Override
    public List<PermissionLDto> getPermissionList() {
        List<Permission> permissions = permissionRepository.findByLevelOrderByCreatedTimeDesc(1);
        List<PermissionLDto> list = new ArrayList<>();
        for (Permission permission : permissions) {
            PermissionLDto permissionLDto = PermissionMapper.INSTANCES.toPermissionDto(permission);
            list.add(permissionLDto);
        }
        return list;
    }
}
