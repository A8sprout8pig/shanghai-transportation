package com.cunjunwang.shanghai.bus.query.model.dto;

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
@ApiModel(value = "GetBusStationDTO",description = "获取公交站点信息DTO")
public class GetBusStationDTO implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "sid", name = "公交线路SID")
    private String sid;

}
