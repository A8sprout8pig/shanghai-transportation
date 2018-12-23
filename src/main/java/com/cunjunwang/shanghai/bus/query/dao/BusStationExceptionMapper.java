package com.cunjunwang.shanghai.bus.query.dao;

import com.cunjunwang.shanghai.bus.query.model.po.BusStationException;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusStationExceptionMapper {
    int insert(BusStationException record);

    int insertSelective(BusStationException record);

    BusStationException selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BusStationException record);

    int updateByPrimaryKey(BusStationException record);

    BusStationException selectByStationName(@Param("busStationName") String busStationName);

    List<BusStationException> selectAll();

}