package com.cunjunwang.shanghai.transportation.dao;

import com.cunjunwang.shanghai.transportation.model.po.BusLine;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusLineMapper {
    int insert(BusLine record);

    int insertSelective(BusLine record);

    BusLine selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BusLine record);

    int updateByPrimaryKey(BusLine record);

    BusLine selectByBusLineNumber(@Param("busLineNumber") String busLineNumber);

    List<String> queryBusLineLike(@Param("busLineLike") String busLineLike);
}