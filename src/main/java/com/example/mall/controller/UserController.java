package com.example.mall.controller;

import com.example.mall.entity.User;
import com.example.mall.entity.UserRegistrationRequest;
import com.example.mall.mapper.UserMapper;
import com.example.mall.response.LoginResponse;
import com.example.mall.service.UserService;
import com.example.mall.utils.JwtUtil;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

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


    @PostMapping("/user/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationRequest registrationRequest) {
        System.out.println("username:" + registrationRequest.getUsername());
        if (userMapper.getUserByName(registrationRequest.getUsername()) != null) {
            return new ResponseEntity<>("User " + registrationRequest.getUsername() + " has existed", HttpStatus.CONFLICT);
        }
        User newUser = new User();
        newUser.setUsername(registrationRequest.getUsername());
        newUser.setPassword(registrationRequest.getPassword());
        newUser.setAddress(registrationRequest.getAddress());
        newUser.setPhone(registrationRequest.getPhone());
        // 调用服务层完成用户注册
        userService.registerUser(newUser);
        return new ResponseEntity<>("User registered successfully:", HttpStatus.CREATED);
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

