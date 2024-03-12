package com.example.mall.service;

import com.example.mall.entity.Attribution;
import com.example.mall.entity.History;
import com.example.mall.entity.Product;
import com.example.mall.mapper.HistoryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HistoryService {

    private final HistoryMapper historyMapper;

    public HistoryService(HistoryMapper historyMapper) {
        this.historyMapper = historyMapper;
    }

    public List<History> getHistory(int userid) {
        return historyMapper.getHistory(userid);
    }

    public boolean addHistory(List<History> histories) {
        return historyMapper.addHistory(histories);
    }


}
