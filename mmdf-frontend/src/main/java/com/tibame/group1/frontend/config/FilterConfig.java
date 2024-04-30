package com.tibame.group1.frontend.config;

import com.tibame.group1.common.filter.CorsFilter;
import com.tibame.group1.frontend.ConfigProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 註冊Filter過濾器
 *
 * @author Jimmy Kang
 */
@Configuration
public class FilterConfig {

    @Autowired protected ConfigProperties config;

    /**
     * 註冊監聽器
     *
     * @return 註冊監聽器
     */
    @Bean(name = "corsFilterBean")
    public FilterRegistrationBean<OncePerRequestFilter> corsFilter() {
        FilterRegistrationBean<OncePerRequestFilter> bean = new FilterRegistrationBean<>();
        // 監聽器
        bean.setFilter(new CorsFilter(config.getCorsAllowOrigin()));
        // 監聽路由
        bean.addUrlPatterns("/*");
        // 監聽名稱
        bean.setName("corsFilter");
        // 監聽順序
        bean.setOrder(0);
        return bean;
    }
}
