package com.cunjunwang.shanghai.bus.query.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by CunjunWang on 2018/12/16.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "BusLineNumberDTO", description = "获取并传递要查询的公交线路")
public class BusLineNumberDTO implements Serializable {

    public static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "要查询的公交线路名，需要加\"路\"字", name = "idnum")
    private String idnum;

}
