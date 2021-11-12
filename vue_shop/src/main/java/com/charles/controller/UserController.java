package com.charles.controller;

import com.charles.dto.UserDto;
import com.charles.dto.UserListDto;
import com.charles.entity.SysUser;
import com.charles.service.SysUserService;
import com.charles.util.JsonResult;
import com.charles.util.State;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("users")
public class UserController {

    @Resource
    private SysUserService sysUserService;

    @GetMapping("/list")
    public JsonResult<UserListDto> list(SysUser user,
                                        @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
                                        @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize) {

        UserListDto userListDto = sysUserService.findByConditionAndPage(user, pageNum, pageSize);
        return new JsonResult<>(State.OK, userListDto);
    }

    @PostMapping("/add")
    public JsonResult<UserDto> add(SysUser user) {
        UserDto userDto = sysUserService.save(user);
        return new JsonResult<>(State.CREATED, userDto);
    }

    @PutMapping("/{uid}/state/{type}")
    public JsonResult<UserDto> changeState(@PathVariable(value = "uid") Integer uid,
                                           @PathVariable(value = "type") Boolean type) {
        UserDto userDto = sysUserService.setState(uid, type);
        return new JsonResult<>(State.OK, userDto);
    }

    @GetMapping("/{id}")
    public JsonResult<UserDto> findOne(@PathVariable(value = "id") Integer id) {
        UserDto userDto = sysUserService.findOneById(id);
        return new JsonResult<>(State.OK, userDto);
    }

    @PutMapping("/{id}")
    public JsonResult<UserDto> update(@PathVariable(value = "id") Integer id, SysUser user) {
        System.out.println(user);
        UserDto userDto = sysUserService.update(user);
        return new JsonResult<>(State.OK, userDto);
    }

    @DeleteMapping("/{id}")
    public JsonResult<Void> delete(@PathVariable(value = "id") Integer id) {
        sysUserService.delete(id);
        return new JsonResult<>(State.OK);
    }

    @PutMapping("/{id}/role")
    public JsonResult<UserDto> setRole(@PathVariable(value = "id") Integer id, Integer roleId) {
        UserDto userDto = sysUserService.setRole(id, roleId);
        return new JsonResult<>(State.OK, userDto);
    }

}
