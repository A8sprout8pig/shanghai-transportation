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
@ApiModel(value = "BusStationDTO",description = "公交站点信息SID")
public class BusStationDTO implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "站点在线路中顺序Id", name = "lineSequenceId")
    private String lineSequenceId;

    @ApiModelProperty(value = "站点名", name = "stationName")
    private String stationName;

}
