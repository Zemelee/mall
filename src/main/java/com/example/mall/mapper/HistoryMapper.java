package com.example.mall.mapper;

import com.example.mall.entity.History;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface HistoryMapper {
    List<History> getHistory(int userid);

    boolean addHistory(History histories);
}
