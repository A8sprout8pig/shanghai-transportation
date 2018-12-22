package com.cunjunwang.shanghai.bus.query.service.queryservice;

import com.cunjunwang.shanghai.bus.query.constant.Constant;
import com.cunjunwang.shanghai.bus.query.constant.ErrConstant;
import com.cunjunwang.shanghai.bus.query.constant.ErrMsgConstant;
import com.cunjunwang.shanghai.bus.query.exception.ShanghaiBusException;
import com.cunjunwang.shanghai.bus.query.model.dto.*;
import com.cunjunwang.shanghai.bus.query.model.vo.BusCurrentStopVO;
import com.cunjunwang.shanghai.bus.query.model.vo.BusDetailVO;
import com.cunjunwang.shanghai.bus.query.service.dataservice.BusBaseDataService;
import com.cunjunwang.shanghai.bus.query.util.HtmlParserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
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

    /**
     * 查询公交实时到站信息
     *
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
     *
     * @param busLineNumber
     * @return
     */
    public BusDetailVO queryBusDetail(String busLineNumber) {
        // 根据路线号获得Sid
        String sid = this.getBusSidByLineNumber(busLineNumber);
        logger.info("获取sid: {}", sid);
        GetBusStationsDTO getBusStationsDTO = new GetBusStationsDTO();
        getBusStationsDTO.setSid(sid);
        getBusStationsDTO.setStopType(Constant.UP_GOING);
        busBaseDataService.getBusStationsBySid(getBusStationsDTO);
        // 发送请求
        String fullUrl = String.format(getStationsURL, sid, Constant.UP_GOING);
        String responseHtml = restTemplate.getForObject(fullUrl, String.class);

        if (responseHtml == null || StringUtils.isEmpty(responseHtml)) {
            BusDetailVO busDetailVO = htmlParserUtil.getBusIntroInfo(responseHtml);
            busDetailVO.setLineNum(busLineNumber);
            busDetailVO.setBusDirectionType(Constant.DOUBLE_DIRECTION);
            logger.info("响应参数: {}", busDetailVO.toString());
            return busDetailVO;
        } else {
            logger.error("网络请求错误");
            throw new ShanghaiBusException(ErrConstant.HTTP_REQUEST_ERR, ErrMsgConstant.HTTP_REQUEST_ERR_MSG);
        }

    }

    /**
     * [Helper]
     * 根据线路查询Sid
     *
     * @param busLineNumber
     * @return
     */
    private String getBusSidByLineNumber(String busLineNumber) {
        BusLineNumberDTO busLineNumberDTO = new BusLineNumberDTO();
        busLineNumberDTO.setIdnum(busLineNumber);
        BusSidDTO busSidDTO = busBaseDataService.getBusSID(busLineNumberDTO);
        return busSidDTO.getSid();
    }
}
