package com.cunjunwang.shanghai.bus.query.service.mqservice.consumer;

import com.cunjunwang.shanghai.bus.query.model.dto.BusDataExceptionDTO;
import com.cunjunwang.shanghai.bus.query.model.po.BusLineException;
import com.cunjunwang.shanghai.bus.query.service.dbservice.BusLinePersistExceptionDBService;
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

    @JmsListener(destination = "${com.cunjunwang.activeMQ.shanghai.bus.query.busDataPersistException:bus.data.persist.exception}", containerFactory = "busMQFactory")
    public void busDataPersistExceptionReceiver(ObjectMessage message, Session session) throws Exception {
        try {
            logger.info("接受公交线路存储异常通知，开始记录异常信息");
            if (!(message.getObject() instanceof BusDataExceptionDTO)) {
                logger.warn("消息数据类型不匹配");
                return;
            }
            BusDataExceptionDTO busDataExceptionDTO = (BusDataExceptionDTO) message.getObject();
            String busLineNumber = busDataExceptionDTO.getBusLineNumber();
            BusLineException busLineException = new BusLineException();
            busLineException.setBusLine(busLineNumber);
            busLineException.setExceptionReason(busDataExceptionDTO.getExceptionReason());
            busLinePersistExceptionDBService.insertNewEntry(busLineException);

            message.acknowledge();
        } catch (Exception e) {
            logger.error("消费公交存储异常信息出错", e);
            session.recover();
        }
    }

}
