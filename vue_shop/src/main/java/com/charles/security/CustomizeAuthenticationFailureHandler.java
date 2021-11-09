package com.charles.security;

import com.charles.util.JsonResult;
import com.charles.util.State;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomizeAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        JsonResult<Void> result = new JsonResult<>();
        if (e instanceof BadCredentialsException) {
            result.setState(State.UNAUTHORIZED);
            result.setMessage("账号或密码错误");
        } else if (e instanceof DisabledException) {
            result.setState(State.UNAUTHORIZED);
            result.setMessage("账号不可用");
        } else if(e instanceof AuthenticationServiceException){
            result.setState(State.UNAUTHORIZED);
            result.setMessage(e.getMessage());
        } else {
            result.setState(State.BAD_REQUEST);
            result.setMessage(e.getMessage());
        }
        httpServletResponse.setContentType("text/json;charset=utf-8");
        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(result));
    }
}
