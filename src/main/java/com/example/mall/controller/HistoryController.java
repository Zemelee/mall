package com.example.mall.controller;


import com.example.mall.entity.History;
import com.example.mall.entity.HistoryResponse;
import com.example.mall.service.HistoryService;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/mall/history")
public class HistoryController {
    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping("/get/{userid}")
    public List<History> getHistory(@PathVariable int userid) {
        return historyService.getHistory(userid);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addHistory(@RequestBody List<History> histories) {
        if (historyService.addHistory(histories)) {
            return new ResponseEntity<>("ok", HttpStatus.OK);
        }
        return null;
    }

    @GetMapping("/get/product/{pid}")
    public List<HistoryResponse> getHistoryByPid(@PathVariable int pid) {
        return historyService.getHistoryByPid(pid);
    }

    @Getter
    @ToString
    static class PageInfo {
        int page;
        int size;
    }

    @PostMapping("/get")
    public List<HistoryResponse> getAllHistory(@RequestBody PageInfo pageInfo) {
        return historyService.getAllHistory(pageInfo.getPage(), pageInfo.getSize());
    }

    @PostMapping("/delete")
    public boolean delHistoryByTime(@RequestBody Date orderTime) {
        return historyService.delHistoryByTime(orderTime);
    }
}
