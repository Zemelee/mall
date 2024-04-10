package com.example.mall.mapper;

import com.example.mall.entity.User;
import com.example.mall.entity.UserFeedback;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;


//mapper层建立接口、定义数据库的操作动作,和mybatis相关
//interface抽象类，定义一组方法但不包含方法实现
@Mapper
public interface UserMapper {
    User userLogin(String name, String pwd);

    User getUserByName(String name);

    User getUserById(int id);

    void registerUser(User user);

    float getBalance(int id);

    int recharge(@Param("id") int id, @Param("amount") float amount);

    int consumption(@Param("id") int id, @Param("amount") float amount);

    void addFeedback(UserFeedback feedback);

    List<UserFeedback> getFeedback();

    List<UserFeedback> searchFeedback(String keyword, Integer type);

    boolean updateUserInfo(User user);

    boolean updateFeedback(Date submitTime);

    boolean addClicks(int uid, int pid);

    List<User> getAllUser();




}
