package com.cunjunwang.shanghai.bus.query.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cunjunwang.shanghai.bus.query.model.dto.BusLineNumberDTO;
import com.cunjunwang.shanghai.bus.query.model.dto.BusSidDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


/**
 * Created by CunjunWang on 2018/12/17.
 */
@Service
public class BusService {

    private static final Logger logger = LoggerFactory.getLogger(BusService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${com.cunjunwang.shanghai.bus.query.getSidUrl}")
    private String getSidURL;

    @Value("${com.cunjunwang.shanghai.bus.query.getStationsUrl}")
    private String getStationsURL;

    public BusSidDTO getBusSID(BusLineNumberDTO busLineNumberDTO) {

        String idNum = busLineNumberDTO.getIdnum();
        logger.info("开始查询公交[{}]的实时信息", idNum);
        // 1设置请求头
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(mediaType);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        // 2封装参数
        String requestBody = JSON.toJSONString(busLineNumberDTO);
        logger.info("查询公交[{}]的实时信息请求参数[{}]", idNum, requestBody);
        HttpEntity entity = new HttpEntity(requestBody, headers);
        // 3发送参数
        JSONObject restObject = restTemplate.postForObject(getSidURL, entity, JSONObject.class);
        logger.info("上海发布平台响应参数[{}]", restObject);

        if (restObject != null) {
            String mes = restObject.getString("mes");
            String sid = restObject.getString("sid");
            BusSidDTO busSidDTO = new BusSidDTO();
            busSidDTO.setMes(mes);
            busSidDTO.setSid(sid);
            logger.info("[{}]实时信息请求结果[{}]", idNum, busSidDTO.toString());
            return busSidDTO;
        } else {
            logger.warn("请求结果为空");
            return null;
        }
    }

}
