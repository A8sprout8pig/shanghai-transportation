package com.cunjunwang.shanghai.bus.query.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by CunjunWang on 2018-12-23.
 */
@Data
@ApiModel(value = "BusStationExceptionDTO",description = "传递公交站点存储异常信息")
public class BusStationExceptionDTO implements Serializable {

    private static final long serialVersionUID = -1;

    @ApiModelProperty(value = "公交站点名称", name = "busStationName")
    public String busStationName;
}
