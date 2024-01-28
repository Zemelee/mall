package com.example.mall.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationRequest {
    private String username;
    private String password;
    private String address;
    private String phone;
}
