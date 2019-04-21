package com.cunjunwang.shanghai.transportation.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by CunjunWang on 2018/12/16.
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "BusSidDTO",description = "获取并传递公交SID")
public class BusSidDTO implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "字段含义不明", name = "mes")
    private String mes;

    @ApiModelProperty(value = "公交唯一标识符", name = "sid")
    private String sid;
}