package com.cunjunwang.shanghai.bus.query.dao;

import com.cunjunwang.shanghai.bus.query.model.po.BusLineException;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BusLineExceptionMapper {
    int insert(BusLineException record);

    int insertSelective(BusLineException record);

    BusLineException selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BusLineException record);

    int updateByPrimaryKey(BusLineException record);

    BusLineException selectByBusLineNumber(@Param("busLineNumber") String busLineNumber);
}