package com.cunjunwang.shanghai.bus.query.util;

import com.cunjunwang.shanghai.bus.query.model.dto.BusStationDTO;
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

        Document document = Jsoup.parse(responseHtml);
        Elements elements = document.body().getElementsByClass("station");

        List<BusStationDTO> busStationDTOList = new ArrayList<>();

        for(Element element : elements) {
            BusStationDTO busStationDTO = new BusStationDTO();
            String lineSequenceId = element.getElementsByClass("num").text();
            String stationName = element.getElementsByClass("name").text();
            busStationDTO.setLineSequenceId(lineSequenceId);
            busStationDTO.setStationName(stationName);
            busStationDTOList.add(busStationDTO);
        }

        return busStationDTOList;
    }
}
