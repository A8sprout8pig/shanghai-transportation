package com.cunjunwang.shanghai.bus.query.service.dataservice;

import com.cunjunwang.shanghai.bus.query.constant.Constant;
import com.cunjunwang.shanghai.bus.query.model.dto.*;
import com.cunjunwang.shanghai.bus.query.model.po.BusLine;
import com.cunjunwang.shanghai.bus.query.model.vo.BusDetailVO;
import com.cunjunwang.shanghai.bus.query.model.vo.BusLineDataVO;
import com.cunjunwang.shanghai.bus.query.service.dbservice.BusLineDBService;
import com.cunjunwang.shanghai.bus.query.service.mqservice.producer.BusDataPersistExceptionNotifyService;
import com.cunjunwang.shanghai.bus.query.service.queryservice.BusQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CunjunWang on 2018-12-21.
 */
@Service
public class BusDataPersistenceService {

    private static final Logger logger = LoggerFactory.getLogger(BusDataPersistenceService.class);

    @Autowired
    private BusLineDBService busLineDBService;

    @Autowired
    private BusQueryService busQueryService;

    @Autowired
    private BusBaseDataService busBaseDataService;

    @Autowired
    private BusDataPersistExceptionNotifyService busDataPersistExceptionNotifyService;

    public BusLineDataVO getBusLineDataByLineNumber(String lineNumber) {
        logger.info("开始查询公交[{}]持久化信息", lineNumber);
        if (lineNumber == null) {
            logger.warn("线路参数为空");
            return null;
        }

        BusLine busLine = busLineDBService.selectExactByBusLineNumber(lineNumber);
        if (busLine != null) {
            BusLineDataVO busLineDataVO = new BusLineDataVO();
            BeanUtils.copyProperties(busLine, busLineDataVO);
            return busLineDataVO;
        } else {
            logger.warn("线路信息[{}]不存在", lineNumber);
            return null;
        }
    }

    /**
     * 持久化存储公交信息
     *
     * @param lineNumber
     * @return
     */
    public Boolean saveBusLineDataByLineNumber(String lineNumber) {

        logger.info("开始持久化存储线路[{}]信息", lineNumber);

        BusLine originBusLine = busLineDBService.selectExactByBusLineNumber(lineNumber);
        if (originBusLine != null) {
            logger.info("线路[{}]信息已存在", lineNumber);
            return false;
        }

        // 获取SID
        BusLineNumberDTO busLineNumberDTO = new BusLineNumberDTO();
        busLineNumberDTO.setIdnum(lineNumber);
        BusSidDTO busSidDTO = busBaseDataService.getBusSID(busLineNumberDTO);
        String sid = busSidDTO.getSid();
        logger.info("获取线路[{}]的SID: {}", lineNumber, sid);

        // 判断线路是否存在
        // TODO: 想其他更合理的方式判断
        GetBusStationsDTO getUpGoingBusStationsDTO = new GetBusStationsDTO();
        getUpGoingBusStationsDTO.setSid(sid);
        getUpGoingBusStationsDTO.setStopType(Constant.UP_GOING);
        List<BusStationDTO> upGoingStations = busBaseDataService.getBusStationsBySid(getUpGoingBusStationsDTO);
        if (upGoingStations == null || upGoingStations.isEmpty()) {
            logger.error("线路[{}]不存在!", lineNumber);
            return false;
        }

        // 基础信息
        BusDetailVO busDetailVO = busQueryService.queryBusDetail(lineNumber);
        BusLine newBusLine = new BusLine();
        newBusLine.setBusLine(lineNumber);
        newBusLine.setCreateTime(new Date());
        newBusLine.setIsDel(Constant.NOT_DEL);
        logger.info("线路[{}]基础信息设置完毕", lineNumber);

        // 上行信息
        BusDirectionInfoDTO upGoingData = busDetailVO.getUpDirectionInfo();
        newBusLine.setUpGoingFirstTime(upGoingData.getFirstTime());
        newBusLine.setUpGoingLastTime(upGoingData.getLastTime());
        newBusLine.setUpGoingStartStation(upGoingData.getStartStation());
        newBusLine.setUpGoingTerminalStation(upGoingData.getTerminalStation());
        Integer upGoingStationCount = upGoingStations.size();
        newBusLine.setUpGoingStationCount(upGoingStationCount);
        logger.info("线路[{}]上行方向信息设置完毕", lineNumber);

        // 下行信息
        BusDirectionInfoDTO downGoingData = busDetailVO.getDownDirectionInfo();
        newBusLine.setDownGoingFirstTime(downGoingData.getFirstTime());
        newBusLine.setDownGoingLastTime(downGoingData.getLastTime());
        newBusLine.setDownGoingStartStation(downGoingData.getStartStation());
        newBusLine.setDownGoingTerminalStation(downGoingData.getTerminalStation());
        GetBusStationsDTO getDownGoingBusStationsDTO = new GetBusStationsDTO();
        getDownGoingBusStationsDTO.setSid(sid);
        getDownGoingBusStationsDTO.setStopType(Constant.DOWN_GOING);
        List<BusStationDTO> downGoingStations = busBaseDataService.getBusStationsBySid(getDownGoingBusStationsDTO);
        Integer downGoingStationCount = downGoingStations == null ? 0 : downGoingStations.size();
        newBusLine.setDownGoingStationCount(downGoingStationCount);
        logger.info("线路[{}]下行方向信息设置完毕", lineNumber);

        // 写入数据库
        busLineDBService.insertByPrimatyKeySelective(newBusLine);
        logger.info("线路[{}]信息写入数据库完毕", lineNumber);
        return true;
    }

    /**
     * 批量存储公交基础信息
     *
     * @param batchSaveBusInfoDTO
     * @return
     */
    public Map<String, Boolean> batchSaveBusLineDataByLineNumbers(BatchSaveBusInfoDTO batchSaveBusInfoDTO) {
        if (batchSaveBusInfoDTO == null) {
            logger.error("批量存储公交基础信息失败, 未传入有效数据!");
            return null;
        }

        List<String> lineNumbers = batchSaveBusInfoDTO.getLineNumbers();
        if (lineNumbers == null || lineNumbers.isEmpty()) {
            logger.error("批量存储公交基础信息失败, 传入列表无效!");
            return null;
        }

        logger.info("开始批量存储公交基础信息, 共[{}]条数据", lineNumbers.size());
        Map<String, Boolean> resultMap = new HashMap<>();

        // 日志打印用计数器
        Integer counter = 0;
        Integer successCounter = 0;
        Integer failCounter = 0;

        for (String lineNumber : lineNumbers) {
            try {
                logger.info("开始存储第[{}]条数据, 线路[{}]", counter, lineNumber);
                Boolean result = this.saveBusLineDataByLineNumber(lineNumber);
                resultMap.put(lineNumber, result);
                successCounter++;
            } catch (Exception e) {
                logger.error("存储第[{}]条数据异常, 线路[{}], 开始异常处理流程", counter, lineNumber);
                // 封装参数
                BusDataExceptionDTO busDataExceptionDTO = new BusDataExceptionDTO();
                busDataExceptionDTO.setBusLineNumber(lineNumber);
                busDataExceptionDTO.setExceptionReason(e.getMessage());
                // 发送通知
                busDataPersistExceptionNotifyService.forward(busDataExceptionDTO);
                failCounter++;
            }
            counter++;
        }

        logger.info("批量存储公交基础信息完成, 应处理[{}]条数据, 共处理[{}]条数据, 成功[{}]条, 失败[{}]条",
                lineNumbers.size(), counter, successCounter, failCounter);

        return resultMap;
    }
}
