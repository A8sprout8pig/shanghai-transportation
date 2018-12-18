package com.cunjunwang.shanghai.bus.query.service;

import com.cunjunwang.shanghai.bus.query.model.dto.BusLineNumberDTO;
import com.cunjunwang.shanghai.bus.query.model.dto.BusSidDTO;
import com.cunjunwang.shanghai.bus.query.model.dto.GetBusCurrentStopDTO;
import com.cunjunwang.shanghai.bus.query.model.dto.GetBusStopDTO;
import com.cunjunwang.shanghai.bus.query.model.vo.BusCurrentStopVO;
import com.cunjunwang.shanghai.bus.query.model.vo.BusDetailVO;
import com.cunjunwang.shanghai.bus.query.util.HtmlParserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by CunjunWang on 2018-12-18.
 */
@Service
public class BusQueryService {

    private static final Logger logger = LoggerFactory.getLogger(BusQueryService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HtmlParserUtil htmlParserUtil;

    @Autowired
    private BusBaseDataService busBaseDataService;

    @Value("${com.cunjunwang.shanghai.bus.query.getStationsUrl}")
    private String getStationsURL;

    private static String busLineIdNum = "%s路";

    /**
     * 查询公交实时到站信息
     * @param getBusCurrentStopDTO
     * @return
     */
    public BusCurrentStopVO queryBusCurrentStopInfo(GetBusCurrentStopDTO getBusCurrentStopDTO) {

        // 根据路线号获得Sid
        String busLineNumber = getBusCurrentStopDTO.getLineNumber();
        String sid = this.getBusSidByLineNumber(busLineNumber);
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

    /**
     * 查询公交介绍信息
     * @param busLineNumber
     * @return
     */
    public BusDetailVO queryBusDetail(String busLineNumber) {
        // 根据路线号获得Sid
        String sid = this.getBusSidByLineNumber(busLineNumber);
        logger.info("获取sid: {}", sid);
        busBaseDataService.getBusStationsBySid(sid);
        // 发送请求
        String fullUrl = String.format(getStationsURL, sid);
        String responseHtml = restTemplate.getForObject(fullUrl, String.class);
        BusDetailVO busDetailVO = htmlParserUtil.getBusIntroInfo(responseHtml);
        busDetailVO.setLineNum(busLineNumber);
        busDetailVO.setBusDirectionType("test");
        logger.info("响应参数: {}", busDetailVO.toString());
        return busDetailVO;
    }

    /**
     * [Helper]
     * 根据线路查询Sid
     * @param busLineNumber
     * @return
     */
    private String getBusSidByLineNumber(String busLineNumber) {
        BusLineNumberDTO busLineNumberDTO = new BusLineNumberDTO();
        busLineNumberDTO.setIdnum(String.format(busLineIdNum, busLineNumber));
        BusSidDTO busSidDTO = busBaseDataService.getBusSID(busLineNumberDTO);
        return busSidDTO.getSid();
    }
}
