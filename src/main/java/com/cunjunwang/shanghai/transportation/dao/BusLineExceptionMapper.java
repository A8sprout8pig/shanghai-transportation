package com.cunjunwang.shanghai.transportation.dao;

import com.cunjunwang.shanghai.transportation.model.po.BusLineException;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusLineExceptionMapper {
    int insert(BusLineException record);

    int insertSelective(BusLineException record);

    BusLineException selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BusLineException record);

    int updateByPrimaryKey(BusLineException record);

    BusLineException selectByBusLineNumber(@Param("busLineNumber") String busLineNumber);

    List<BusLineException> selectAll();

    List<String> selectAllValidLineNumber();
}