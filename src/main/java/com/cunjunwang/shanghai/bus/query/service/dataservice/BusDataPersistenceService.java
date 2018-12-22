package com.cunjunwang.shanghai.bus.query.service.dataservice;

import com.cunjunwang.shanghai.bus.query.constant.Constant;
import com.cunjunwang.shanghai.bus.query.constant.ErrConstant;
import com.cunjunwang.shanghai.bus.query.constant.ErrMsgConstant;
import com.cunjunwang.shanghai.bus.query.exception.ShanghaiBusException;
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

        if (lineNumber == null) {
            logger.error("线路参数为空");
            throw new ShanghaiBusException(ErrConstant.INVALID_PARAMETER, ErrMsgConstant.INVALID_PARAMETER_MSG);
        }

        logger.info("开始查询公交[{}]持久化信息", lineNumber);

        BusLine busLine = busLineDBService.selectExactByBusLineNumber(lineNumber);
        if (busLine != null) {
            BusLineDataVO busLineDataVO = new BusLineDataVO();
            BeanUtils.copyProperties(busLine, busLineDataVO);
            return busLineDataVO;
        } else {
            logger.warn("线路信息[{}]不存在", lineNumber);
            throw new ShanghaiBusException(ErrConstant.UNKONWN_BUS_LINE,
                    String.format(ErrMsgConstant.UNKONWN_BUS_LINE_MSG, lineNumber));
        }
    }

    /**
     * 持久化存储公交信息
     *
     * @param lineNumber
     * @return
     */
    public Boolean saveBusLineDataByLineNumber(String lineNumber) {

        if (lineNumber == null) {
            logger.error("线路参数为空");
            throw new ShanghaiBusException(ErrConstant.INVALID_PARAMETER, ErrMsgConstant.INVALID_PARAMETER_MSG);
        }

        logger.info("开始持久化存储线路[{}]信息", lineNumber);

        BusLine originBusLine = busLineDBService.selectExactByBusLineNumber(lineNumber);
        if (originBusLine != null) {
            logger.info("数据库中线路[{}]信息已存在", lineNumber);
            throw new ShanghaiBusException(ErrConstant.DUPLICATE_BUS_LINE_INFO_ERR,
                    String.format(ErrMsgConstant.DUPLICATE_BUS_LINE_INFO_ERR_MSG, lineNumber));
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
            logger.warn("线路信息[{}]不存在", lineNumber);
            throw new ShanghaiBusException(ErrConstant.UNKONWN_BUS_LINE,
                    String.format(ErrMsgConstant.UNKONWN_BUS_LINE_MSG, lineNumber));
        }

        try {
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
        } catch (Exception e) {
            logger.error("持久化存储线路[{}]信息失败", lineNumber, e);
            throw new ShanghaiBusException(ErrConstant.HTTP_REQUEST_ERR, ErrMsgConstant.HTTP_REQUEST_ERR_MSG);
        }

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
            throw new ShanghaiBusException(ErrConstant.INVALID_PARAMETER, ErrMsgConstant.INVALID_PARAMETER_MSG);
        }

        List<String> lineNumbers = batchSaveBusInfoDTO.getLineNumbers();
        if (lineNumbers == null || lineNumbers.isEmpty()) {
            logger.error("批量存储公交基础信息失败, 传入列表无效!");
            throw new ShanghaiBusException(ErrConstant.INVALID_PARAMETER, ErrMsgConstant.INVALID_PARAMETER_MSG);
        }

        logger.info("开始批量存储公交基础信息, 共[{}]条数据", lineNumbers.size());
        Map<String, Boolean> resultMap = new HashMap<>();

        // 日志打印用计数器
        Integer counter = 0;
        Integer successCounter = 0;
        Integer failCounter = 0;

        for (String lineNumber : lineNumbers) {
            logger.info("开始处理第[{}]条数据", counter);
            if(this.saveOrNotifyException(lineNumber)) {
                successCounter++;
            } else {
                failCounter++;
            }
            counter++;
        }

        logger.info("批量存储公交基础信息完成, 应处理[{}]条数据, 共处理[{}]条数据, 成功[{}]条, 失败[{}]条",
                lineNumbers.size(), counter, successCounter, failCounter);

        return resultMap;
    }


    /**
     * 存储线路数据或发送异常信息
     * @param busLineNumber
     * @return
     */
    public Boolean saveOrNotifyException(String busLineNumber) {
        logger.info("开始存储线路[{}]的信息", busLineNumber);
        // 存储线路数据
        try {
            // 若存储方法没有异常, 一定返回true
            return saveBusLineDataByLineNumber(busLineNumber);
        }
        // 若获取异常, 分情况判断
        catch (Exception e) {
            logger.warn("处理异常信息失败, 线路[{}]", busLineNumber);
            // 若异常是因为该线路已经存储过, 不重新添加记录
            if (e instanceof ShanghaiBusException && !String.format(ErrMsgConstant.DUPLICATE_BUS_LINE_INFO_ERR_MSG, busLineNumber)
                    .equals(((ShanghaiBusException) e).getErrMsg())) {
                // 封装参数
                BusDataExceptionDTO busDataExceptionDTO = new BusDataExceptionDTO();
                busDataExceptionDTO.setBusLineNumber(busLineNumber);
                busDataExceptionDTO.setExceptionReason(((ShanghaiBusException) e).getErrMsg());
                // 发送通知
                busDataPersistExceptionNotifyService.forward(busDataExceptionDTO);
            }
            return false;
        }
    }
}
