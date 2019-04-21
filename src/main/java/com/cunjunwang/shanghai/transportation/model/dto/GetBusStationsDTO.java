package com.cunjunwang.shanghai.transportation.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by CunjunWang on 2018-12-20.
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "GetBusStationsDTO",description = "获取公交站点列表接口传参DTO")
public class GetBusStationsDTO implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "stopType", name = "0-上行, 1-下行")
    private String stopType;

    @ApiModelProperty(value = "sid", name = "公交线路SID")
    private String sid;


}
