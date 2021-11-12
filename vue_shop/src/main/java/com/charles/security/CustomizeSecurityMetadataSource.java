package com.charles.security;

import com.charles.dto.PermissionLDto;
import com.charles.service.PermissionService;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class CustomizeSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Resource
    private PermissionService permissionService;

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        HttpServletRequest request = ((FilterInvocation) o).getRequest();
        String requestMethod = request.getMethod();
        String requestURI = request.getRequestURI();
        String path = requestMethod + ":" + requestURI;
        System.out.println("=======================================================");
        // System.out.println("访问: "+path);
        List<PermissionLDto> AllPermissions = permissionService.getPermissionList();
        List<String> permissions = new ArrayList<>();
        AllPermissions.forEach(permissionLDto -> {
            if (antPathMatcher.match(permissionLDto.getPath(), path)) {
                permissions.add(permissionLDto.getAuthName());
            }
        });
        // System.out.println("所需权限: "+permissions);
        // System.out.println("=======================================================");
        if (!permissions.isEmpty()) {
            return SecurityConfig.createList(permissions.toArray(new String[0]));
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
