package com.example.mall.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Attribution {
    private int id;
    private int product_id;
    private String attrval;     // 规格，例如 "12GB 1TB 黑色"
    private int inventory;      // 库存
    private float more;      // 差额

}
