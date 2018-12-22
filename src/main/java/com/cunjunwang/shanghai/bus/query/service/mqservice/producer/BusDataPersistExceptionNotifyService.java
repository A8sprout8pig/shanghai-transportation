package com.cunjunwang.shanghai.bus.query.service.mqservice.producer;

import com.cunjunwang.shanghai.bus.query.model.dto.BusDataExceptionDTO;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * 公交数据存储异常时, 发送通知, 消息生产者
 * Created by CunjunWang on 2018-12-22.
 */
@Service
public class BusDataPersistExceptionNotifyService {

    private static final Logger logger = LoggerFactory.getLogger(BusDataPersistExceptionNotifyService.class);

    @Autowired
    @Qualifier("busActivemqTemplate")
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    @Qualifier("busDataPersistException")
    private ActiveMQQueue activeMQQueue;

    /**
     * 发送公交线路数据存储异常通知
     * @param busDataExceptionDTO
     */
    public void forward(BusDataExceptionDTO busDataExceptionDTO){
        logger.info("开始生产公交线路[{}]存储异常通知, 数据存入异常记录表", busDataExceptionDTO.toString());
        jmsMessagingTemplate.convertAndSend(activeMQQueue, busDataExceptionDTO);
    }

}
