package com.cunjunwang.shanghai.bus.query.service.dbservice;

import com.cunjunwang.shanghai.bus.query.dao.BusLineMapper;
import com.cunjunwang.shanghai.bus.query.model.po.BusLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by CunjunWang on 2018-12-21.
 */
@Service
public class BusLineDBService {

    @Autowired
    private BusLineMapper busLineMapper;

    /**
     * 根据主键查询
     * @param id 
     * @return
     */
    public BusLine selectByPrimaryKey(Long id) {
        if (id == null) {
            return null;
        }
        return busLineMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据线路精确查询
     * @param busLineNumber 
     * @return
     */
    public BusLine selectExactByBusLineNumber(String busLineNumber) {
        if(busLineNumber == null) {
            return null;
        }
        return busLineMapper.selectByBusLineNumber(busLineNumber);
    }


    public Long insertByPrimatyKeySelective(BusLine newBusLine) {
        if(newBusLine == null) {
            return null;
        }
        busLineMapper.insertSelective(newBusLine);
        return newBusLine.getId();
    }

    public List<String> queryBusLineLike(String busLineLike) {
        if(busLineLike == null || StringUtils.isEmpty(busLineLike)) {
            return null;
        }
        return busLineMapper.queryBusLineLike(busLineLike);
    }
}
