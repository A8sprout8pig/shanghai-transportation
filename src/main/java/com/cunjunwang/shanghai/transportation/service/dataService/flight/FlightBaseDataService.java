package com.cunjunwang.shanghai.transportation.service.dataService.flight;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.cunjunwang.shanghai.transportation.constant.Constant;
import com.cunjunwang.shanghai.transportation.model.dto.flight.AirportBaseDTO;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by CunjunWang on 2019-05-03.
 */
@Service
public class FlightBaseDataService {

    private static final String resultPattern = "\\$\\$\\$(.*?)\\$\\$\\$";

    private static final Logger logger = LoggerFactory.getLogger(Constant.LOGGER);

    private static final String getCityListUrl = "http://www.shanghaiairport.com/ajax/flights/search.aspx?action=getCities&isInternal=%s&isArrival=%s&flightType=%s";

    private static final String getFlightDataUrl = "http://www.shanghaiairport.com/cn/flights.html";

    @Autowired
    private AntiCrawlHandler antiCrawlHandler;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 获取航班目的地城市列表
     *
     * @return
     */
    public String getCityListString(Boolean isInternal, Boolean isArrival, String flightType) {
        String url = String.format(getCityListUrl, isInternal, isArrival, flightType);
        String result = antiCrawlHandler.handleGetRequest(url);

        Pattern r = Pattern.compile(resultPattern);
        Matcher m = r.matcher(result);
        String data = "";
        if (m.find()) {
            data = m.group(1);
        }
        // logger.info("data: {}", data);
        return data;
    }

    /**
     * 构造机场基础数据列表
     *
     * @param
     * @return
     */
    public List<AirportBaseDTO> getAirportList(Boolean isInternal, Boolean isArrival, String flightType) {
        List<AirportBaseDTO> result = new ArrayList<>();
        try {
            logger.info("解析机场基础信息列表");
            String dataString = this.getCityListString(isInternal, isArrival, flightType);
            JSONArray dataArray = JSONArray.parseArray(dataString);
            for (int i = 0; i < dataArray.size(); i++) {
                JSONObject object = dataArray.getJSONObject(i);
                String objectString = object.toJSONString();
                AirportBaseDTO airportBaseDTO =
                        JSON.parseObject(objectString, new TypeReference<AirportBaseDTO>() {});
                result.add(airportBaseDTO);
            }
        } catch (Exception e) {
            logger.warn("解析机场基础信息失败", e);
        }
        return result;
    }

    /**
     * 获取航班目的地城市列表
     *
     * @return
     */
    public String getFlightListString() {
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
        WebDriver chromeDriver = new ChromeDriver();
        chromeDriver.get(getFlightDataUrl);
        WebElement data = chromeDriver.findElement(By.id("data"));
        List<WebElement> dataElements = data.findElements(By.tagName("tr"));
        for (WebElement element : dataElements) {
            List<WebElement> tdElements = element.findElements(By.tagName("td"));
            for (WebElement tdElement : tdElements) {
                logger.info(tdElement.getText());
            }
        }
        return null;
    }

    /*
    currentPage: 1
pageSize: 20
flightType: 1
direction: 1
airCities:
airCities2:
airCompanies:
timeDays: 0
timeSpan: 00:00-23:59
flightNum:
     */
}
