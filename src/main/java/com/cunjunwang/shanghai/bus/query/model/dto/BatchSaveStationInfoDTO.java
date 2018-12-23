package com.cunjunwang.shanghai.bus.query.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * Created by CunjunWang on 2018-12-23.
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "BatchSaveStationInfoDTO", description = "批量存储公交站点信息DTO")
public class BatchSaveStationInfoDTO implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "validBusLineNumberList", name = "线路列表")
    private List<String> validBusLineNumberList;
}
