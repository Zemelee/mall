package com.example.mall.controller;

import com.example.mall.entity.User;
import com.example.mall.response.LoginResponse;
import com.example.mall.service.UserService;
import com.example.mall.utils.JwtUtil;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/user/login")
    public LoginResponse userLogin(@RequestBody User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        LoginResponse response = new LoginResponse();
        boolean isValidUser = userService.validateUser(username, password);
        if (isValidUser) {
            User resUser = userService.userLogin(username, password);
            resUser.setToken(JwtUtil.createToken(username));
            response.setSuccess(true);
            response.setMessage("登陆成功");
            response.setUser(resUser);
        } else {
            response.setSuccess(false);
            response.setMessage("用户名或密码错误"); // 设置提示信息
        }
        return response;
    }

    @GetMapping("/user/id={id}")
    public User userinfo(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @Getter
    static
    class RechargeRequest {
        int id;
        float amount;
    }

    @PostMapping("/user/recharge")
    public Float recharge(@RequestBody RechargeRequest recharge) {
        return userService.recharge(recharge.getId(), recharge.getAmount());
    }


}

