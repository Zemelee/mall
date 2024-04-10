package com.example.mall.mapper;

import com.example.mall.entity.History;
import com.example.mall.entity.HistoryResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;
@Mapper
public interface HistoryMapper {
    List<History> getHistory(int userid);

    boolean addHistory(History histories);

    List<HistoryResponse> getHistoryByPid(int pid);

    List<HistoryResponse> getAllHistory(int page,int size);
    boolean delHistoryByTime(Date orderTime);
}
