package com.cunjunwang.shanghai.bus.query.service.dbservice;

import com.cunjunwang.shanghai.bus.query.dao.BusStationExceptionMapper;
import com.cunjunwang.shanghai.bus.query.model.po.BusStationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by CunjunWang on 2018-12-23.
 */
@Service
public class BusStationPersistExceptionDBService {


    @Autowired
    private BusStationExceptionMapper busStationExceptionMapper;

    /**
     * 插入新异常信息条目
     *
     * @param busStationException
     * @return
     */
    public Long insertNewEntry(BusStationException busStationException) {
        if (busStationException == null) {
            return null;
        }
        busStationExceptionMapper.insertSelective(busStationException);
        return busStationException.getId();
    }

    /**
     * 根据站点名选择异常信息
     *
     * @param busStationName
     * @return
     */
    public BusStationException selectByStationName(String busStationName) {
        if (busStationName == null) {
            return null;
        }
        return busStationExceptionMapper.selectByStationName(busStationName);
    }

    /**
     * 查询所有待处理的异常信息条目
     *
     * @return
     */
    public List<BusStationException> selectAllUnhandled() {
        return busStationExceptionMapper.selectAll();
    }

    /**
     * 根据主键更新异常记录信息
     *
     * @param busStationException
     * @return
     */
    public Boolean updateByPrimaryKey(BusStationException busStationException) {
        if (busStationException == null) {
            return false;
        }
        return busStationExceptionMapper.updateByPrimaryKeySelective(busStationException) == 1;
    }

}
