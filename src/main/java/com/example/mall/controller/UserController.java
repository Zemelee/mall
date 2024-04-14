package com.example.mall.controller;

import com.example.mall.entity.ResponseData;
import com.example.mall.entity.User;
import com.example.mall.entity.UserFeedback;
import com.example.mall.entity.UserRegistrationRequest;
import com.example.mall.mapper.UserMapper;
import com.example.mall.response.LoginResponse;
import com.example.mall.service.UserService;
import com.example.mall.utils.JwtUtil;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;


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
    static class RechargeOrConsumptionRequest {
        int id;
        float amount;
    }

    @PostMapping("/user/recharge")
    public Float recharge(@RequestBody RechargeOrConsumptionRequest recharge) {
        return userService.recharge(recharge.getId(), recharge.getAmount());
    }

    @PostMapping("/user/consume")
    public ResponseData consumption(@RequestBody RechargeOrConsumptionRequest consumption) {
        ResponseData responseData = new ResponseData("pay fail", 0, false);
        int userid = consumption.getId();
        float balance = userService.getBalance(userid);
        if (consumption.getAmount() < balance) {
            responseData.setMsg("pay success");
            responseData.setNumCode(userService.consumption(consumption.getId(), consumption.getAmount()));
            responseData.setStatus(true);
        }
        return responseData;

    }

    @PostMapping("/user/feedback/post")
    public ResponseEntity<String> registerUser(@RequestBody UserFeedback userFeedback) {
        UserFeedback newFeedback = new UserFeedback();
        newFeedback.setUsername(userFeedback.getUsername());
        newFeedback.setFeedback(userFeedback.getFeedback());
        newFeedback.setEmail(userFeedback.getEmail());
        Date currentTime = new Date();
        newFeedback.setSubmitTime(currentTime);
        // 调用服务层完成反馈保存
        userService.addFeedback(newFeedback);
        return new ResponseEntity<>("feedback successfully", HttpStatus.OK);
    }

    @GetMapping("/user/feedback/get")
    public List<UserFeedback> getAllFeedback() {
        return userService.getFeedback();
    }

    @Getter
    @ToString
    static class SearchParam {
        private String keyword;
        private List<Integer> handleType;
    }

    @PostMapping("/user/feedback/search")
    public List<UserFeedback> searchFeedback(@RequestBody SearchParam searchParam) {
        System.out.println("searchParam:" + searchParam);
        return userService.searchFeedback(searchParam.getKeyword(), searchParam.getHandleType());
    }

    @PostMapping("/user/update/{user_id}")
    public ResponseEntity<String> updateUserInfo(@PathVariable int user_id,
                                                 @RequestBody User user) {
        boolean isUpdated = userService.updateUserInfo(user_id, user.getUsername(), user.getPassword(), user.getAddress(), user.getPhone());
        if (isUpdated) {
            return new ResponseEntity<>("ok", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("user/feedback/handle")
    public ResponseEntity<String> updateFeedback(@RequestBody List<Date> times) {
        List<Date> failedTimes = new ArrayList<>();
        for (Date time : times) {
            boolean res = userService.updateFeedback(time);
            if (!res) {
                failedTimes.add(time);
            }
        }
        if (!failedTimes.isEmpty()) {
            String message = "handle failed for Times: " + failedTimes;
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @Getter
    @ToString
    static class Click {
        private int uid;
        private int pid;
    }

    @PostMapping("/user/click")
    public boolean addClicks(@RequestBody Click click) {
        return userService.addClicks(click.getUid(), click.getPid());
    }

    @GetMapping("/user/get")
    public List<User> getAllUser() {
        return userService.getAllUser();
    }

    @GetMapping("/user/count")
    public Integer getUserCount() {
        return userMapper.getUserCount();
    }


}

