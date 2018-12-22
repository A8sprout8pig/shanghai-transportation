package com.cunjunwang.shanghai.bus.query.dao;

import com.cunjunwang.shanghai.bus.query.model.po.BusLine;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BusLineMapper {
    int insert(BusLine record);

    int insertSelective(BusLine record);

    BusLine selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BusLine record);

    int updateByPrimaryKey(BusLine record);

    BusLine selectByBusLineNumber(@Param("busLineNumber") String busLineNumber);
}