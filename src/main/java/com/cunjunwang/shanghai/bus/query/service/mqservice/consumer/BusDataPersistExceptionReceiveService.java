package com.cunjunwang.shanghai.bus.query.service.mqservice.consumer;

import com.cunjunwang.shanghai.bus.query.model.dto.BusLineDataExceptionDTO;
import com.cunjunwang.shanghai.bus.query.model.dto.BusStationExceptionDTO;
import com.cunjunwang.shanghai.bus.query.model.po.BusLineException;
import com.cunjunwang.shanghai.bus.query.model.po.BusStationException;
import com.cunjunwang.shanghai.bus.query.service.dbservice.BusLinePersistExceptionDBService;
import com.cunjunwang.shanghai.bus.query.service.dbservice.BusStationPersistExceptionDBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.jms.ObjectMessage;
import javax.jms.Session;

/**
 * 公交线路存储异常消息消费者
 * Created by CunjunWang on 2018-12-22.
 */

@Service
public class BusDataPersistExceptionReceiveService {

    private static final Logger logger = LoggerFactory.getLogger(BusDataPersistExceptionReceiveService.class);

    @Autowired
    private BusLinePersistExceptionDBService busLinePersistExceptionDBService;

    @Autowired
    private BusStationPersistExceptionDBService busStationPersistExceptionDBService;

    @JmsListener(destination = "${com.cunjunwang.activeMQ.shanghai.bus.query.busLinePersistException:bus.line.persist.exception}", containerFactory = "busMQFactory")
    public void busLineDataExceptionReceiver(ObjectMessage message, Session session) throws Exception {
        try {
            logger.info("接受公交线路存储异常通知，开始记录异常信息");
            if (!(message.getObject() instanceof BusLineDataExceptionDTO)) {
                logger.warn("消息数据类型不匹配");
                return;
            }
            BusLineDataExceptionDTO busLineDataExceptionDTO = (BusLineDataExceptionDTO) message.getObject();
            String busLineNumber = busLineDataExceptionDTO.getBusLineNumber();
            BusLineException busLineException = new BusLineException();
            busLineException.setBusLine(busLineNumber);
            busLineException.setExceptionReason(busLineDataExceptionDTO.getExceptionReason());
            busLinePersistExceptionDBService.insertNewEntry(busLineException);

            message.acknowledge();
        } catch (Exception e) {
            logger.error("消费公交线路存储异常信息出错", e);
            session.recover();
        }
    }

    @JmsListener(destination = "${com.cunjunwang.activeMQ.shanghai.bus.query.busLinePersistException:bus.data.persist.exception}", containerFactory = "busMQFactory")
    public void busStationDataExceptionReceiver(ObjectMessage message, Session session) throws Exception {
        try {
            logger.info("接受公交站点存储异常通知，开始记录异常信息");
            if (!(message.getObject() instanceof BusStationExceptionDTO)) {
                logger.warn("消息数据类型不匹配");
                return;
            }

            BusStationExceptionDTO busStationExceptionDTO = (BusStationExceptionDTO) message.getObject();
            String busStationName = busStationExceptionDTO.getBusStationName();
            BusStationException busStationException = new BusStationException();
            busStationException.setBusStationName(busStationName);
            busStationPersistExceptionDBService.insertNewEntry(busStationException);

            message.acknowledge();
        } catch (Exception e) {
            logger.error("消费公交站点存储异常信息出错", e);
            session.recover();
        }
    }

}
