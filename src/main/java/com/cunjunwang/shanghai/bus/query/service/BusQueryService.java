package com.cunjunwang.shanghai.bus.query.service;

import com.cunjunwang.shanghai.bus.query.model.dto.BusLineNumberDTO;
import com.cunjunwang.shanghai.bus.query.model.dto.BusSidDTO;
import com.cunjunwang.shanghai.bus.query.model.dto.GetBusCurrentStopDTO;
import com.cunjunwang.shanghai.bus.query.model.dto.GetBusStopDTO;
import com.cunjunwang.shanghai.bus.query.model.vo.BusCurrentStopVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by CunjunWang on 2018-12-18.
 */
@Service
public class BusQueryService {

    private static final Logger logger = LoggerFactory.getLogger(BusQueryService.class);

    @Autowired
    private BusBaseDataService busBaseDataService;

    private static String busLineIdNum = "%s路";

    /**
     * 查询公交实时到站信息
     * @param getBusCurrentStopDTO
     * @return
     */
    public BusCurrentStopVO queryBusCurrentStopInfo(GetBusCurrentStopDTO getBusCurrentStopDTO) {

        // 根据路线号获得Sid
        String busLineNumber = getBusCurrentStopDTO.getLineNumber();
        BusLineNumberDTO busLineNumberDTO = new BusLineNumberDTO();
        busLineNumberDTO.setIdnum(String.format(busLineIdNum, busLineNumber));
        BusSidDTO busSidDTO = busBaseDataService.getBusSID(busLineNumberDTO);
        String sid = busSidDTO.getSid();
        logger.info("获取sid: {}", sid);
        // 封装参数
        GetBusStopDTO getBusStopDTO = new GetBusStopDTO();
        getBusStopDTO.setSid(sid);
        getBusStopDTO.setStopId(getBusCurrentStopDTO.getStopSequenceId());
        getBusStopDTO.setStopType(getBusCurrentStopDTO.getDirection());
        logger.info("请求实时公交信息参数: {}", getBusStopDTO.toString());

        BusCurrentStopVO busCurrentStopVO = busBaseDataService.getStop(getBusStopDTO);
        logger.info("请求结果: {}", busCurrentStopVO.toString());
        return busCurrentStopVO;
    }
}
