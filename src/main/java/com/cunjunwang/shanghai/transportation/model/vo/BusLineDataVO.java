package com.cunjunwang.shanghai.transportation.model.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by CunjunWang on 2018-12-21.
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "BusLineDataVO", description = "公交信息展示对象")
public class BusLineDataVO implements Serializable {

    private static final long serialVersionUID = -1;

    private Long id;

    private String busLine;

    private String upGoingStartStation;

    private String upGoingTerminalStation;

    private String upGoingFirstTime;

    private String upGoingLastTime;

    private Integer upGoingStationCount;

    private String downGoingStartStation;

    private String downGoingTerminalStation;

    private String downGoingFirstTime;

    private String downGoingLastTime;

    private Integer downGoingStationCount;

    private BigDecimal lineLength;

    private String busDescription;

    private String busCost;

    private String busComment;

    private Integer isDel;

    private Date createTime;

    private Date updateTime;

}
