package com.cunjunwang.shanghai.transportation.service.dbservice;

import com.cunjunwang.shanghai.transportation.dao.BusStationMapper;
import com.cunjunwang.shanghai.transportation.model.po.BusStation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by CunjunWang on 2018-12-23.
 */
@Service
public class BusStationDBService {

    @Autowired
    private BusStationMapper busStationMapper;


    /**
     * 根据线路名查询
     * @param busStationName
     * @return
     */
    public BusStation selectByStationName(String busStationName) {
        if(busStationName == null) {
            return null;
        }
        return busStationMapper.selectByStationName(busStationName);
    }

    /**
     * 插入新条目
     * @param newBusStation
     * @return
     */
    public Long insertNewStation(BusStation newBusStation) {
        if(newBusStation == null) {
            return null;
        }
        busStationMapper.insertSelective(newBusStation);
        return newBusStation.getId();
    }


    /**
     * 根据站点名模糊查询
     * @param busStationLike
     * @return
     */
    public List<String> queryBusStationLike(String busStationLike) {
        if(busStationLike == null || StringUtils.isEmpty(busStationLike)) {
            return null;
        }
        return busStationMapper.queryBusStationLike(busStationLike);
    }
}
