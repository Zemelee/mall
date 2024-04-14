package com.example.mall.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Coupon {
    private int cid;
    private String code;
    private int discount;
    private Date created;
    private Date expire;
    private int status;
}
