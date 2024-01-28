package com.example.mall.entity;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserFeedback {
    private String username;
    private String email;
    private String feedback;
    private Date submitTime;

    @Override
    public String toString() {
        return "UserFeedback{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", feedback='" + feedback + '\'' +
                ", submitTime=" + submitTime +
                '}';
    }

}
