package com.cunjunwang.shanghai.bus.query.config;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CunjunWang on 2018-12-18.
 */
public class BusHttpMessageConverter extends MappingJackson2HttpMessageConverter {

    public BusHttpMessageConverter() {
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.TEXT_HTML);
        setSupportedMediaTypes(mediaTypes);
    }

}
