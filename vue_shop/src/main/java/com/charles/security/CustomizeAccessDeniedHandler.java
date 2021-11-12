package com.charles.security;

import com.charles.util.JsonResult;
import com.charles.util.State;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomizeAccessDeniedHandler implements AccessDeniedHandler {

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        JsonResult<Void> result = new JsonResult<>();
        if(e != null){
            result.setMessage(e.getMessage());
            result.setState(State.FORBIDDEN);
            httpServletResponse.setContentType("text/json;charset=utf-8");
            httpServletResponse.getWriter().write(objectMapper.writeValueAsString(result));
        }
    }
}
