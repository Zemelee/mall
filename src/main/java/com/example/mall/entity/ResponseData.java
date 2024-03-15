package com.example.mall.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseData {
    private String msg;
    private float numCode;
    private boolean status;

    public ResponseData(String msg, float numCode, boolean status) {
        this.msg = msg;
        this.numCode = numCode;
        this.status = status;
    }
}
