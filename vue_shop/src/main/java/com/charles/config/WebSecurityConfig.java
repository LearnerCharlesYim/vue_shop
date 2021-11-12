package com.charles.config;

import com.charles.security.*;
import com.charles.util.jwt.JwtAuthenticationTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;


@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        // 设置默认的加密方式（强hash方式加密）
        return new BCryptPasswordEncoder();
    }

    @Resource
    private UserDetailsService userDetailsService;

    //匿名用户访问无权限资源时的异常
    @Resource
    private CustomizeAuthenticationEntryPoint authenticationEntryPoint;

    //登录成功处理逻辑
    @Resource
    private CustomizeAuthenticationSuccessHandler authenticationSuccessHandler;

    //登录失败处理逻辑
    @Resource
    private CustomizeAuthenticationFailureHandler authenticationFailureHandler;

    //登出成功处理逻辑
    @Resource
    private CustomizeLogoutSuccessHandler logoutSuccessHandler;

    //会话失效(账号被挤下线)处理逻辑
    @Resource
    private CustomizeSessionInformationExpiredStrategy customizeSessionInformationExpiredStrategy;

    //实现权限拦截
    @Resource
    private CustomizeSecurityMetadataSource customizeSecurityMetadataSource;

    //访问决策管理器
    @Resource
    private CustomizeAccessDecisionManager customizeAccessDecisionManager;

    // 自定义权限拦截器
    @Resource
    private CustomizeAbstractSecurityInterceptor customizeSecurityInterceptor;

    @Resource
    private CustomizeAccessDeniedHandler customizeAccessDeniedHandler;

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
        http.addFilterBefore(customizeSecurityInterceptor, FilterSecurityInterceptor.class);

        http.formLogin()
                .loginProcessingUrl("/login")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler);

        http.authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setAccessDecisionManager(customizeAccessDecisionManager);//访问决策管理器
                        o.setSecurityMetadataSource(customizeSecurityMetadataSource);//安全元数据源
                        return o;
                    }
                });
        http.authorizeRequests()
                .antMatchers("/login")
                .permitAll()
                .anyRequest()
                .authenticated()

                .and()
                .logout()
                .logoutSuccessHandler(logoutSuccessHandler)// 登出成功处理逻辑
                .and()
                .csrf().disable()
                //异常处理(权限拒绝、登录失效等)
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint) //匿名用户访问无权限资源时的异常处理
                .accessDeniedHandler(customizeAccessDeniedHandler)// 权限拒绝处理逻辑
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .maximumSessions(1) // 同一账号同时登录最大用户数
                .expiredSessionStrategy(customizeSessionInformationExpiredStrategy);//会话失效(账号被挤下线)处理逻辑

        http.headers().frameOptions().sameOrigin()
                .and().cors();
    }
}
