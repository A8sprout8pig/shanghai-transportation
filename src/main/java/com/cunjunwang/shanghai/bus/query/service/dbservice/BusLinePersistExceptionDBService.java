package com.cunjunwang.shanghai.bus.query.service.dbservice;

import com.cunjunwang.shanghai.bus.query.dao.BusLineExceptionMapper;
import com.cunjunwang.shanghai.bus.query.model.po.BusLineException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by CunjunWang on 2018-12-22.
 */
@Service
public class BusLinePersistExceptionDBService {

    @Autowired
    private BusLineExceptionMapper busLineExceptionMapper;


    /**
     * 插入新异常信息条目
     * @param busLineException
     * @return
     */
    public Long insertNewEntry(BusLineException busLineException) {
        if(busLineException == null) {
            return null;
        }
        busLineExceptionMapper.insertSelective(busLineException);
        return busLineException.getId();
    }

    /**
     * 根据线路选择异常信息
     * @param busLineNumber
     * @return
     */
    public BusLineException selectByBusLine(String busLineNumber) {
        if(busLineNumber == null) {
            return null;
        }
        return busLineExceptionMapper.selectByBusLineNumber(busLineNumber);
    }
}
