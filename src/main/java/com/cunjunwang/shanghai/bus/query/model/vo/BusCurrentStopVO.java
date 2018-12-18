package com.cunjunwang.shanghai.bus.query.model.vo;

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
@ApiModel(value = "BusCurrentStopVO", description = "封装公交实时站点信息")
public class BusCurrentStopVO implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "code", name = "公交车路线", example = "147路")
    private String code;

    @ApiModelProperty(value = "license", name = "公交车牌号", example = "沪B-39236")
    private String license;

    @ApiModelProperty(value = "stopDis", name = "距离当前站点的站数", example = "5")
    private String stopDis;

    @ApiModelProperty(value = "distance", name = "距离当前站点的估计距离, 单位(米)", example = "2692")
    private String distance;

    @ApiModelProperty(value = "distance", name = "距离当前站点的估计时间, 单位(秒)", example = "597")
    private String time;
}
