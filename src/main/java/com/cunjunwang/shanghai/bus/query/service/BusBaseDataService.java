package com.cunjunwang.shanghai.bus.query.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cunjunwang.shanghai.bus.query.model.dto.*;
import com.cunjunwang.shanghai.bus.query.model.vo.BusCurrentStopVO;
import com.cunjunwang.shanghai.bus.query.util.HtmlParserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;


/**
 * Created by CunjunWang on 2018/12/17.
 */
@Service
public class BusBaseDataService {

    private static final Logger logger = LoggerFactory.getLogger(BusBaseDataService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HtmlParserUtil htmlParserUtil;

    @Value("${com.cunjunwang.shanghai.bus.query.getSidUrl}")
    private String getSidURL;

    @Value("${com.cunjunwang.shanghai.bus.query.getStationsUrl}")
    private String getStationsURL;

    @Value("${com.cunjunwang.shanghai.bus.query.getStopUrl}")
    private String getStopURL;

    /**
     * 获取公交线路SID
     * @param busLineNumberDTO
     * @return
     */
    public BusSidDTO getBusSID(BusLineNumberDTO busLineNumberDTO) {
        String idNum = busLineNumberDTO.getIdnum();
        logger.info("开始查询公交[{}]的SID", idNum);
        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(mediaType);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        // 封装参数
        String requestBody = JSON.toJSONString(busLineNumberDTO);
        logger.info("查询公交[{}]SID的请求参数[{}]", idNum, requestBody);
        HttpEntity entity = new HttpEntity(requestBody, headers);
        // 发送参数
        JSONObject restObject = restTemplate.postForObject(getSidURL, entity, JSONObject.class);
        logger.info("上海发布平台响应参数[{}]", restObject);
        // 封装出参
        if (restObject != null) {
            String mes = restObject.getString("mes");
            String sid = restObject.getString("sid");
            BusSidDTO busSidDTO = new BusSidDTO();
            busSidDTO.setMes(mes);
            busSidDTO.setSid(sid);
            logger.info("公交[{}]SID请求结果[{}]", idNum, busSidDTO.toString());
            return busSidDTO;
        } else {
            logger.warn("请求结果为空");
            return null;
        }
    }

    /**
     * 根据公交SID获取站点信息
     *
     * @param getBusStationsDTO
     * @return
     */
    public List<BusStationDTO> getBusStationsBySid(GetBusStationsDTO getBusStationsDTO) {
        String sid = getBusStationsDTO.getSid();
        String stopType = getBusStationsDTO.getStopType();
        logger.info("开始查询SID为[{}]的公交站点信息", sid);
        // 发送请求
        String fullUrl = String.format(getStationsURL, sid, stopType);
        logger.info("查询URL: {}", fullUrl);
        String responseHtml = restTemplate.getForObject(fullUrl, String.class);
        logger.info("上海发布平台响应参数[{}]", responseHtml);
        return htmlParserUtil.getStationList(responseHtml);
    }

    /**
     * 获得公交实时站点信息
     *
     * @param getBusStopDTO
     * @return
     */
    public BusCurrentStopVO getStop(GetBusStopDTO getBusStopDTO) {
        logger.info("开始获取公交实时站点信息, 请求参数[{}]", getBusStopDTO.toString());
        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        // 设置请求参数
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("stoptype", getBusStopDTO.getStopType());
        params.add("stopid", getBusStopDTO.getStopId());
        params.add("sid", getBusStopDTO.getSid());
        HttpEntity entity = new HttpEntity<>(params, headers);
        // 发送请求
        String response = restTemplate.postForObject(getStopURL, entity, String.class);
        logger.info("上海发布平台响应参数: {}", JSON.parse(response));
        // 封装出参
        BusCurrentStopVO busCurrentStopVO = new BusCurrentStopVO();
        try {
            JSONObject restObject = ((JSONArray) JSON.parse(response)).getJSONObject(0);
            busCurrentStopVO.setCode(restObject.getJSONObject("@attributes").getString("cod"));
            busCurrentStopVO.setStopDis(restObject.getString("stopdis"));
            busCurrentStopVO.setDistance(restObject.getString("distance"));
            busCurrentStopVO.setLicense(restObject.getString("terminal"));
            busCurrentStopVO.setTime(restObject.getString("time"));
        } catch (Exception e) {
            String error = JSON.parseObject(response).getString("error");
            if("-1".equals(error)) {
                busCurrentStopVO.setLicense("等待发车");
            }
        }
        return busCurrentStopVO;
    }
}
