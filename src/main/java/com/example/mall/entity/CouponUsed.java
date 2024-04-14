package com.example.mall.entity;

import lombok.Data;

import java.util.Date;


@Data
public class CouponUsed {

    private int id;
    private int cid;
    private int uid;
    private Date orderTime;
    private float discountNum;
}
