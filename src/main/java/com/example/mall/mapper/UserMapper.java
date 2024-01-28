package com.example.mall.mapper;

import com.example.mall.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


//mapper层建立接口、定义数据库的操作动作,和mybatis相关
//interface抽象类，定义一组方法但不包含方法实现
@Mapper
public interface UserMapper {
    User userLogin(String name, String pwd);

    User getUserByName(String name);

    User getUserById(int id);

    void registerUser(User user);
    float recharge(@Param("id") int id, @Param("amount") float amount);
}
