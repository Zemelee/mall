package com.example.mall.entity;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class User {
    private int user_id;
    private String username;
    private String password;
    private String address;
    private float balance;
    private float accumulated;
    private String phone;
    private String token;
}

