package com.cunjunwang.shanghai.bus.query.config;


import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsMessagingTemplate;

import javax.jms.Session;

/**
 * Created by CunjunWang on 2018-12-22.
 */
@Configuration
public class ActiveMQConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(ActiveMQConfiguration.class);

    private static final String MQ_NAME = "上海公交信息查询默认MQ";

    @Value("${spring.jms.listener.concurrency}")
    String mqListenerConcurrency;

    /**************************************************   公交信息队列   ******************************************************/

    /**
     * 公交基础信息存储异常
     */
    public static String busLinePersistException;

    /**
     * 公交站点信息存储异常
     */
    public static String busStationPersistException;

    @Value("${com.cunjunwang.activeMQ.shanghai.bus.query.busLinePersistException:bus.line.persist.exception}")
    public void setBusLinePersistException(String busLinePersistException) {
        this.busLinePersistException = busLinePersistException;
    }

    @Value("${com.cunjunwang.activeMQ.shanghai.bus.query.busStationPersistException:bus.station.persist.exception}")
    public void setBusStationPersistException(String busStationPersistException) {
        this.busStationPersistException = busStationPersistException;
    }

    /**
     * 配置 ActiveMq默认连接池
     *
     * @param brokerUrl
     * @param username
     * @param password
     * @param isTrustAll
     * @return
     */
    @Bean(name = "busMQConnectionFactory")
    @Primary
    public ActiveMQConnectionFactory busMQConnectionFactory(
            @Value("${spring.activemq.broker.url}") String brokerUrl,
            @Value("${spring.activemq.user}") String username,
            @Value("${spring.activemq.password}") String password,
            @Value("${spring.activemq.packages.trust-all}") Boolean isTrustAll,
            @Value("${spring.activemq.pool.enabled}") Boolean poolEnabled,
            @Value("${spring.activemq.pool.max-connections}") Integer poolMaxConnections,
            @Value("${spring.activemq.pool.idle-timeout}") Long poolIdleTimeout) {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        /** 基本配置 */
        factory.setBrokerURL(brokerUrl);
        factory.setUserName(username);
        factory.setPassword(password);
        factory.setTrustAllPackages(isTrustAll);
        /** 连接池配置 */
        factory.setStatsEnabled(poolEnabled);
        factory.setMaxThreadPoolSize(poolMaxConnections);
        factory.setOptimizeAcknowledgeTimeOut(poolIdleTimeout);
        logger.info("------实例化[{}]ActiveMq连接池配置-------", MQ_NAME);
        return factory;
    }

    @Bean(name = "busActivemqTemplate")
    public JmsMessagingTemplate busActivemqTemplate(
            @Qualifier("busMQConnectionFactory") ActiveMQConnectionFactory connectionFactory) {
        logger.info("------实例化[{}]ActiveMQ Template-------", MQ_NAME);
        JmsMessagingTemplate template = new JmsMessagingTemplate(connectionFactory);
        return template;
    }

    /**
     * 公交数据持久化存储异常
     **/
    @Bean(name = "busLinePersistException")
    public ActiveMQQueue busLinePersistException() {
        logger.info("初始化activeMQ队列[{}], 队列名称[{}]", "公交线路持久化存储异常", busLinePersistException);
        return new ActiveMQQueue(busLinePersistException);
    }

    /**
     * 站点数据持久化存储异常
     **/
    @Bean(name = "busStationPersistException")
    public ActiveMQQueue busStationPersistException() {
        logger.info("初始化activeMQ队列[{}], 队列名称[{}]", "公交站点持久化存储异常", busStationPersistException);
        return new ActiveMQQueue(busStationPersistException);
    }

    @Bean(name = "busMQFactory")
    public JmsListenerContainerFactory busMQFactory(
            @Qualifier("busMQConnectionFactory") ActiveMQConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrency(mqListenerConcurrency);
        factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
        factory.setCacheLevel(1);
        factory.setCacheLevelName("CACHE_CONNECTION");
        return factory;
    }

}
