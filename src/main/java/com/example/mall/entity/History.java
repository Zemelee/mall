package com.example.mall.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class History {
    private int uerid;
    private int product_id;
    private String name;
    private String attrval;
    private int quantity;
    private float price;
    private Date roder_time;
}
