package com.example.mall.service;

import com.example.mall.entity.Attribution;
import com.example.mall.entity.History;
import com.example.mall.entity.Product;
import com.example.mall.mapper.HistoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HistoryService {
    @Autowired
    private HistoryMapper historyMapper;

    public HistoryService(HistoryMapper historyMapper) {
        this.historyMapper = historyMapper;
    }

    public List<History> getHistory(int userid) {
        return historyMapper.getHistory(userid);
    }

    public boolean addHistory(List<History> histories) {
        return histories.stream() // 将histories列表转换为Stream
                .allMatch(history -> { // 使用allMatch来检查每个history是否都添加成功
                    try {
                        System.out.println(history);
                        return historyMapper.addHistory(history);
                    } catch (Exception e) {
                        // 在这里处理可能出现的异常
                        e.printStackTrace();
                        return false; // 如果有异常，返回false
                    }
                });
    }


}
