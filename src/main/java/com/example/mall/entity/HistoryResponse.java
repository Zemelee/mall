package com.example.mall.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class HistoryResponse {

    private int user_id;
    private int product_id;
    private String name;
    private String attrval;
    private int quantity;
    private float price;
    private Date order_time;
    private int status;
}
