package com.charles.security;

import com.charles.dto.LoginUserDto;
import com.charles.entity.SysUser;
import com.charles.mapper.LoginUserMapper;
import com.charles.service.SysUserService;
import com.charles.util.JsonResult;
import com.charles.util.State;
import com.charles.util.jwt.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomizeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SysUser sysUser = sysUserService.findByUsername(userDetails.getUsername());
        //此处还可以进行一些处理，比如登录成功之后可能需要返回给前台当前用户有哪些菜单权限，
        //进而前台动态的控制菜单的显示等，具体根据自己的业务需求进行扩展
        //生成Jwt令牌
        String token = JwtUtils.generateToken(authentication);
        JsonResult<LoginUserDto> result = new JsonResult<>();

        LoginUserDto loginUserDto = LoginUserMapper.INSTANCES.toLoginUserDto(sysUser);
        loginUserDto.setToken(token);
        result.setState(State.OK);
        result.setMessage("登录成功");
        result.setData(loginUserDto);
        httpServletResponse.setContentType("text/json;charset=utf-8");
        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(result));

    }
}
