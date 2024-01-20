package com.example.mall.service;

import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.stereotype.Service;

@Service
public class QykService {
    public String reply(String content) {
        String body = HttpRequest.get("http://api.qingyunke.com/api.php?key=free&appid=0&msg=" + URLUtil.encode(content))
                .execute().charset("utf-8").body();
        JSONObject jsonObject = JSONUtil.parseObj(body);
        if (jsonObject.getStr("result").equals("0")) {
            return jsonObject.getStr("content");
        } else {
            System.out.println(jsonObject);
            return "未找到相关内容";
        }
    }


}
