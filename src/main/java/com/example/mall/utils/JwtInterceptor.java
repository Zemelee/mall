package com.example.mall.utils;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

import static com.example.mall.utils.JwtUtil.signature;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 拦截器逻辑
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write(JSON.toJSONString("No Login"));
            return false;
        }
        String token = authHeader.substring(1, authHeader.length() - 1);
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(signature)
                    .parseClaimsJws(token)
                    .getBody();  //获取claims内容,包含了用户名等信息
            // 获取用户名
            String username = (String) claims.get("username");
            Date exp = claims.getExpiration();// 获取token过期时间
            Date now = new Date();
            // 判断是否过期
            if (now.after(exp)) {
                // token已过期,返回错误响应或者抛出异常
                throw new ExpiredJwtException(null, null, "Token expired");
            }
        } catch (ExpiredJwtException e) {
            // 处理过期的令牌
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write(JSON.toJSONString("Token is expired"));
            return false;
        } catch (Exception e) {
            // 其他解析失败的情况
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write(JSON.toJSONString("Token is invalid"));
            return false;
        }
        return true;
    }
}



