package com.tibame.group1.common.filter;

import com.tibame.group1.common.utils.StringUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 開放跨域過濾器
 *
 * @author Jimmy Kang
 */
public class CorsFilter extends OncePerRequestFilter {

    private final String corsAllowOrigin;

    public CorsFilter() {
        this.corsAllowOrigin = "*";
    }

    public CorsFilter(String corsAllowOrigin) {
        this.corsAllowOrigin = corsAllowOrigin;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        // 設定編碼格式
        request.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        // 設定開方跨域網域
        if (StringUtils.isEmpty(corsAllowOrigin) || corsAllowOrigin.equals("*")) {
            // 如果沒設定跨域或者設定為全允許時直接回傳Request中的Origin
            String origin = request.getHeader("Origin");
            if (StringUtils.isEmpty(origin)) {
                response.addHeader("Access-Control-Allow-Origin", "*");
            } else {
                response.addHeader("Access-Control-Allow-Origin", origin);
            }
        } else {
            response.addHeader("Access-Control-Allow-Origin", corsAllowOrigin);
        }
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader(
                "Access-Control-Allow-Headers",
                "Content-Type, Content-Length, Content-Disposition, Authorization, WebRoute, Set-Cookie");
        response.setHeader("Access-Control-Max-Age", "300");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        filterChain.doFilter(request, response);
    }
}
