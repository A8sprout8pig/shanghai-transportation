package com.cunjunwang.shanghai.bus.query.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by CunjunWang on 2018-12-22.
 */

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "BusLineDataExceptionDTO", description = "传递公交存储异常信息DTO")
public class BusLineDataExceptionDTO implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(name = "busLineNumber", value = "公交线路")
    private String busLineNumber;

    @ApiModelProperty(name = "exceptionReason", value = "错误原因")
    private String exceptionReason;

}
