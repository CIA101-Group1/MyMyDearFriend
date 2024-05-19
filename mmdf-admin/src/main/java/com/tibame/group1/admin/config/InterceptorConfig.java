package com.tibame.group1.admin.config;

import com.tibame.group1.admin.interceptor.AdminCheckLoginInterceptor;
import com.tibame.group1.admin.interceptor.PermissionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired private AdminCheckLoginInterceptor adminCheckLoginInterceptor;

    @Autowired private PermissionInterceptor permissionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(adminCheckLoginInterceptor)
//                .addPathPatterns("/**")
//                .excludePathPatterns(
//                        "/home",
//                        "/static/**",
//                        "/assets/**",
//                        "/js/**",
//                        "/employee/login",
//                        "/api/employee/login");
        // registry.addInterceptor(permissionInterceptor).addPathPatterns("/**").excludePathPatterns("/home","/static/**");
    }
}
