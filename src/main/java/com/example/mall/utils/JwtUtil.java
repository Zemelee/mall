package com.example.mall.utils;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.UUID;

public class JwtUtil {
    private static long time = 1000 * 60 * 60 * 24;  //设置 JWT 的有效期，这里是2天
    public static String signature = "admin";
    public static String createToken(String username) {
        JwtBuilder jwtBuilder = Jwts.builder();
        String jwtToken = jwtBuilder
                .setHeaderParam("typ", "JWT")  //header 类型 (typ)
                .setHeaderParam("alg", "HS256")  //算法 (alg)
                .claim("username", username)  //payload
                .setExpiration(new Date(System.currentTimeMillis() + time))  //过期时间
                .setId(UUID.randomUUID().toString())  //随机种子
                .signWith(SignatureAlgorithm.HS256, signature)  //signature
                .compact();
        System.out.println(jwtToken);
        return jwtToken;
    }
}

