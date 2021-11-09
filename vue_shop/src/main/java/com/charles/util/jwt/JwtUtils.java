package com.charles.util.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

public class JwtUtils {
    public static String generateToken(Authentication authentication) {
        return Jwts.builder()
                .claim("authentication", authentication)
                .setSubject("subject")
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000))
                .signWith(SignatureAlgorithm.HS512,"charles")
                .compact();
    }

    public static void tokenParse(String token) throws JsonProcessingException {
        Claims claims = Jwts.parser()
                .setSigningKey("charles")
                .parseClaimsJws(token)
                .getBody();
        Date claimExpiration = claims.getExpiration();
        Date now = new Date();
        if(now.getTime()>claimExpiration.getTime()){
            throw new AuthenticationServiceException("凭证已过期，请重新登录");
        }
        Object target = claims.get("authentication");
        if(target == null){
            throw new AuthenticationServiceException("凭证无效，请登录");
        }
        LinkedHashMap<String,Object> authenticationMap = (LinkedHashMap<String, Object>) target;
        String username = (String) authenticationMap.get("name");
        ArrayList<LinkedHashMap<String,String>> authenticationList =
                (ArrayList<LinkedHashMap<String, String>>) authenticationMap.get("authorities");
        String[] authenticationStr = new String[authenticationList.size()];
        for(int i =0;i<authenticationList.size();i++){
            authenticationStr[i] = authenticationList.get(i).get("authority");
        }
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(authenticationStr);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(username,null,authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
