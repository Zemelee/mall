package com.example.mall.mapper;

import com.example.mall.entity.History;

import java.util.List;

public interface HistoryMapper {
    List<History> getHistory(int userid);

    boolean addHistory(List<History> histories);
}
