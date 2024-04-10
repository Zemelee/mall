package com.example.mall.service;

import com.example.mall.entity.History;
import com.example.mall.entity.HistoryResponse;
import com.example.mall.mapper.HistoryMapper;
import com.example.mall.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class HistoryService {
    @Autowired
    private HistoryMapper historyMapper;
    @Autowired
    private ProductMapper productMapper;

    public HistoryService(HistoryMapper historyMapper) {
        this.historyMapper = historyMapper;
    }

    public void ProductMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public List<History> getHistory(int userid) {
        return historyMapper.getHistory(userid);
    }

    public boolean addHistory(List<History> histories) {
        return histories.stream() // 将histories列表转换为Stream
                .allMatch(history -> { // 使用allMatch来检查每个history是否都添加成功
                    try {
                        System.out.println(history);
                        productMapper.addSalesAndSubInventory(history.getProduct_id(), history.getAttrid(), history.getQuantity());
                        return historyMapper.addHistory(history);
                    } catch (Exception e) {
                        // 在这里处理可能出现的异常
                        e.printStackTrace();
                        return false; // 如果有异常，返回false
                    }
                });
    }

    public List<HistoryResponse> getHistoryByPid(int pid) {
        return historyMapper.getHistoryByPid(pid);
    }

    public List<HistoryResponse> getAllHistory(int page, int size) {
        return historyMapper.getAllHistory((page - 1) * size, size);
    }

    public boolean delHistoryByTime(Date orderTime) {
        return historyMapper.delHistoryByTime(orderTime);
    }
}
