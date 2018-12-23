package com.cunjunwang.shanghai.bus.query.dao;

import com.cunjunwang.shanghai.bus.query.model.po.BusStation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BusStationMapper {
    int insert(BusStation record);

    int insertSelective(BusStation record);

    BusStation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BusStation record);

    int updateByPrimaryKey(BusStation record);

    BusStation selectByStationName(@Param("busStationName") String busStationName);
}