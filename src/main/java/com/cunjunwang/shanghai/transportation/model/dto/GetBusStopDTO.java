package com.cunjunwang.shanghai.transportation.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by CunjunWang on 2018/12/17.
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "GetBusStopDTO",description = "获取公交实时信息接口传参DTO")
public class GetBusStopDTO implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "stopType", name = "0-上行, 1-下行")
    private String stopType;

    @ApiModelProperty(value = "stopId", name = "公交站点序号")
    private String stopId;

    @ApiModelProperty(value = "sid", name = "公交线路SID")
    private String sid;

}
