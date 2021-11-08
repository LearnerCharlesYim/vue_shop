package com.charles.controller;

import com.charles.dto.MenuDto;
import com.charles.dto.PermissionLDto;
import com.charles.mapper.MenuMapper;
import com.charles.dto.PermissionListDto;
import com.charles.entity.Permission;
import com.charles.service.PermissionService;
import com.charles.util.JsonResult;
import com.charles.util.State;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rights")
public class PermissionController {

    @Resource
    private PermissionService permissionService;

    @GetMapping("/tree")
    public JsonResult<PermissionListDto> tree(){
        PermissionListDto page = permissionService.findAllByPage(1, 10);
        return new JsonResult<>(State.OK,page);
    }

    @GetMapping("/list")
    public JsonResult<List<PermissionLDto>> list(){
        List<PermissionLDto> permissionList = permissionService.getPermissionList();
        return new JsonResult<>(State.OK,permissionList);
    }

    @GetMapping("/menus")
    public JsonResult<List<MenuDto>> getMenus(){
        List<Permission> menus = permissionService.getSideBarMenu();
        List<MenuDto> list = new ArrayList<>();

        for (Permission menu : menus) {
            MenuDto menuDto = MenuMapper.INSTANCES.toMenuDto(menu);
            list.add(menuDto);
        }

        return new JsonResult<>(State.OK,list);
    }
}
