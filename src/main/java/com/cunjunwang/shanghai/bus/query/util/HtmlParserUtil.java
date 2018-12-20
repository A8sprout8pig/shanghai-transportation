package com.cunjunwang.shanghai.bus.query.util;

import com.cunjunwang.shanghai.bus.query.model.dto.BusDirectionInfoDTO;
import com.cunjunwang.shanghai.bus.query.model.dto.BusStationDTO;
import com.cunjunwang.shanghai.bus.query.model.vo.BusDetailVO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CunjunWang on 2018-12-17.
 */
@Component
public class HtmlParserUtil {

    private static final Logger logger = LoggerFactory.getLogger(HtmlParserUtil.class);

    /**
     * 解析HTML, 获取站点信息
     * @param responseHtml
     * @return
     */
    public List<BusStationDTO> getStationList(String responseHtml) {

        logger.info("开始解析html, 获取公交站点信息");
        Document document = Jsoup.parse(responseHtml);
        Elements elements = document.body().getElementsByClass("station");

        List<BusStationDTO> busStationList = new ArrayList<>();
        for(Element element : elements) {
            BusStationDTO busStationDTO = new BusStationDTO();
            String lineSequenceId = element.getElementsByClass("num").text();
            String stationName = element.getElementsByClass("name").text();
            busStationDTO.setLineSequenceId(lineSequenceId);
            busStationDTO.setStationName(stationName);
            busStationList.add(busStationDTO);
        }

        logger.info("获取公交站点信息, 该线路共{}站", elements.size());

        return busStationList;
    }

    /**
     * 解析HTML, 获取公交介绍信息
     * @param responseHtml
     * @return
     */
    public BusDetailVO getBusIntroInfo(String responseHtml) {
        logger.info("开始解析html, 获取公交基础信息");
        Element body = Jsoup.parse(responseHtml).body();
        Elements elements = body.getElementsByClass("upgoing");
        Element upGoing = elements.get(0);
        Element downGoing = elements.get(1);
        // 构造上行方向信息
        BusDirectionInfoDTO upGoingInfo = this.buildBusDirectionInfo(upGoing);
        // 构造下行方向信息
        BusDirectionInfoDTO downGoingInfo = this.buildBusDirectionInfo(downGoing);
        // 构造返回结果
        BusDetailVO busDetailVO = new BusDetailVO();
        busDetailVO.setUpDirectionInfo(upGoingInfo);
        busDetailVO.setDownDirectionInfo(downGoingInfo);
        return busDetailVO;
    }

    /**
     * 构造公交单向基础信息
     * @param directionElement
     * @return
     */
    private BusDirectionInfoDTO buildBusDirectionInfo(Element directionElement) {
        BusDirectionInfoDTO directionInfoDTO = new BusDirectionInfoDTO();
        directionInfoDTO.setFirstTime(directionElement.getElementsByClass("s").text());
        directionInfoDTO.setLastTime(directionElement.getElementsByClass("m").text());
        directionInfoDTO.setStartStation(directionElement.getElementsByTag("span").get(0).text());
        directionInfoDTO.setTerminalStation(directionElement.getElementsByTag("span").get(1).text());
        return directionInfoDTO;
    }
}
