package com.cunjunwang.shanghai.transportation.model.dto.flight;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 根据上海机场API, 不使用camel case
 * Created by CunjunWang on 2019-05-04.
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "机场基础数据对象")
public class AirportBaseDTO implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "机场编号", name = "Code")
    private String Code;

    @ApiModelProperty(value = "机场名称", name = "Name")
    private String Name;

    @ApiModelProperty(value = "机场英文名", name = "EnglishName")
    private String EnglishName;

    @ApiModelProperty(value = "机场日文名", name = "JPName")
    private String JPName;

    @ApiModelProperty(value = "机场韩文名", name = "KOName")
    private String KOName;

}
