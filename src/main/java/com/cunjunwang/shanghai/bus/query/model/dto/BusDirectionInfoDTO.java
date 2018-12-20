package com.cunjunwang.shanghai.bus.query.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by CunjunWang on 2018-12-18.
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "BusDirectionInfoDTO", description = "封装公交单方向介绍信息")
public class BusDirectionInfoDTO implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "firstTime", name = "首班车时间", example = "05:30")
    private String firstTime;

    @ApiModelProperty(value = "lastTime", name = "首班车时间", example = "22:30")
    private String lastTime;

    @ApiModelProperty(value = "startStation", name = "起点站")
    private String startStation;

    @ApiModelProperty(value = "terminalStation", name = "终点站")
    private String terminalStation;

    @ApiModelProperty(value = "stopCount", name = "站点数", example = "5")
    private Integer stopCount;



}
