package com.example.mall.controller;

import com.example.mall.response.AiResponse;
import com.example.mall.service.QykService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public class QykController {
    @Autowired
    private QykService qykService;

    @RequestMapping(value = "/shut", method = RequestMethod.GET)
    public AiResponse reply(@RequestParam("msg") String msg) {
        System.out.println(msg);
        AiResponse response = new AiResponse(0, "");
        String str = qykService.reply(msg);
        response.setMsg(str);
        response.setStatus(1);
        return response;
    }
}
