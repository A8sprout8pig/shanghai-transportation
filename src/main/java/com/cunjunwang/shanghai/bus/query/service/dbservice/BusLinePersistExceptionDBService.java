package com.cunjunwang.shanghai.bus.query.service.dbservice;

import com.cunjunwang.shanghai.bus.query.dao.BusLineExceptionMapper;
import com.cunjunwang.shanghai.bus.query.model.po.BusLineException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /**
     * 查询所有待处理的异常信息条目
     * @return
     */
    public List<BusLineException> selectAllUnhandled() {
        return busLineExceptionMapper.selectAll();
    }

    /**
     * 根据主键更新异常记录信息
     * @param busLineException
     * @return
     */
    public Boolean updateByPrimaryKey(BusLineException busLineException) {
        if(busLineException == null) {
            return false;
        }
        return busLineExceptionMapper.updateByPrimaryKeySelective(busLineException) == 1;
    }
}
