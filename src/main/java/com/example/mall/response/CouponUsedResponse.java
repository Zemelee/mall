package com.example.mall.response;

import com.example.mall.entity.CouponUsed;
import lombok.Data;

import java.util.Date;

@Data
public class CouponUsedResponse {
    private int useId;
    private int cid;
    private String code;
    private int uid;
    private String uname;
    private int discount;
    private float discountNum;
    private Date orderTime;
}
