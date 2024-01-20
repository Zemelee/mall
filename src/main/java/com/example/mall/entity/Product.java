package com.example.mall.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Product {
    private int id;
    private String name;
    private float price;
    private int sales;
    private int category;  //种类
    private int state;  //上架状态
    private String description;
    private String picsrc;
    private List<Attribution> attributions;

}
