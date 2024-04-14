package com.example.mall.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;


@Getter
@Setter
@ToString
public class History {
    private int userid;
    private int product_id;
    private String name;
    private int attrid;
    private String attrval;
    private int quantity;
    private float price;
    private Date order_time;
    private int status;

    @Override
    public String toString() {
        return "History{" +
                "userid='" + userid + '\'' +
                ", product_id='" + product_id + '\'' +
                ", name='" + name + '\'' +
                ", attrid=" + attrid +
                ", attrval=" + attrval +
                ", quantity=" + quantity +
                ", price=" + price +
                ", order_time=" + order_time +
                '}';
    }
}
