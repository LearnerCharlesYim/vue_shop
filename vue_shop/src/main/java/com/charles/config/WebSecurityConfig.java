package com.charles.config;

import com.charles.security.*;
import com.charles.util.jwt.JwtAuthenticationTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;


@Configuration
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        // 设置默认的加密方式（强hash方式加密）
        return new BCryptPasswordEncoder();
    }

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private CustomizeAuthenticationEntryPoint authenticationEntryPoint;

    @Resource
    private CustomizeAuthenticationSuccessHandler authenticationSuccessHandler;

    @Resource
    private CustomizeAuthenticationFailureHandler authenticationFailureHandler;

    @Resource
    private CustomizeLogoutSuccessHandler logoutSuccessHandler;

    @Resource
    private CustomizeSessionInformationExpiredStrategy customizeSessionInformationExpiredStrategy;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //配置认证方式等
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http相关的配置，包括登入登出、异常处理、会话管理等
        JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter = new JwtAuthenticationTokenFilter();
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        http.formLogin()
                .loginProcessingUrl("/login")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler);

        http.authorizeRequests()
                .antMatchers("/login")
                .permitAll()
                .antMatchers("/users").hasAuthority("用户管理")
                .anyRequest()
                .authenticated()

                .and()
                .logout()
                .logoutSuccessHandler(logoutSuccessHandler)
                .and()
                .csrf().disable()
                //异常处理(权限拒绝、登录失效等)
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint) //匿名用户访问无权限资源时的异常处理
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .maximumSessions(1)
                .expiredSessionStrategy(customizeSessionInformationExpiredStrategy);

        http.headers().frameOptions().sameOrigin()
                .and().cors();
    }
}
