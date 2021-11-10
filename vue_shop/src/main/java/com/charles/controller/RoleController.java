package com.charles.controller;

import com.charles.dto.RoleDto;
import com.charles.entity.Permission;
import com.charles.entity.Role;
import com.charles.service.RoleService;
import com.charles.util.JsonResult;
import com.charles.util.State;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @RequestMapping("/list")
    public JsonResult<List<Role>> list(){
        List<Role> roles = roleService.list();
        return new JsonResult<>(State.OK,roles);
    }

    @PostMapping("/add")
    public JsonResult<RoleDto> add(Role role){
        RoleDto roleDto = roleService.save(role);
        return new JsonResult<>(State.CREATED,roleDto);
    }

    @GetMapping("/{id}")
    public JsonResult<RoleDto> add(@PathVariable(value = "id") Integer id){
        RoleDto roleDto = roleService.findById(id);
        return new JsonResult<>(State.OK,roleDto);
    }

    @PutMapping("/{id}")
    public JsonResult<RoleDto> update(@PathVariable(value = "id") Integer id, Role role){
        RoleDto roleDto = roleService.update(role);
        return new JsonResult<>(State.OK,roleDto);
    }

    @DeleteMapping("/{id}")
    public JsonResult<Void> delete(@PathVariable(value = "id") Integer id){
        roleService.delete(id);
        return new JsonResult<>(State.OK);
    }

    @PostMapping("/{roleId}/rights")
    public JsonResult<Void> setPermission(@PathVariable(value = "roleId") Integer roleId,String rids){
        roleService.setPermission(roleId,rids);
        return new JsonResult<>(State.OK);
    }

    @DeleteMapping("/{roleId}/rights/{rightId}")
    public JsonResult<List<Permission>> deletePermission(@PathVariable(value = "roleId") Integer roleId,
                                                         @PathVariable(value = "rightId") Integer rightId){
        Role role = roleService.deletePermissionById(roleId, rightId);
        return new JsonResult<>(State.OK,role.getPermissions());
    }

}
