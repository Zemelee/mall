package com.example.mall.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

import static java.security.KeyRep.Type.SECRET;

@Getter
@Setter
public class User {
    private int user_id;
    private String username;
    private String password;
    private String address;
    private float balance;
    private String phone;
    private String token;


}
