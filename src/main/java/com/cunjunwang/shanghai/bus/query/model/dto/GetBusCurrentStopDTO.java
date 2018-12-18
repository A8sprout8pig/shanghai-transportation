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
@ApiModel(value = "GetBusCurrentStopDTO",description = "查询公交实时到站信息传参DTO")
public class GetBusCurrentStopDTO implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "lineNumber", name = "线路", example = "147")
    private String lineNumber;

    @ApiModelProperty(value = "direction", name = "方向, 0-上行, 1-下行", example = "0")
    private String direction;

    @ApiModelProperty(value = "stopSequenceId", name = "该线路该方向的站点序号", example = "1")
    private String stopSequenceId;

}
