package com.cunjunwang.shanghai.transportation.service.dataService.flight;

import com.cunjunwang.shanghai.transportation.constant.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by CunjunWang on 2019-05-03.
 */
@Service
public class FlightBaseDataService {

    private static final Logger logger = LoggerFactory.getLogger(Constant.LOGGER);

    private static final String getCityListUrl = "http://www.shanghaiairport.com/ajax/flights/search.aspx?action=getCities&isInternal=false&isArrival=false&flightType=1";

    @Autowired
    private AntiCrawlHandler antiCrawlHandler;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 获取航班目的地城市列表
     *
     * @return
     */
    public String getCityList() {
        antiCrawlHandler.handleRequest(getCityListUrl);
        // String result = restTemplate.exchange(getCityListUrl, HttpMethod.GET, null, String.class).getBody();
        // logger.info("result = {}", result);
        return null;
    }
}
