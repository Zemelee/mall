package com.example.mall.response;

import com.example.mall.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private boolean success;
    private String message;
    private User user; // 可选，如果需要返回用户信息的话

}

