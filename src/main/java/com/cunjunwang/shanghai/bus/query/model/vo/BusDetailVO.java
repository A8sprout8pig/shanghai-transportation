package com.cunjunwang.shanghai.bus.query.model.vo;

import com.cunjunwang.shanghai.bus.query.model.dto.BusDirectionInfoDTO;
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
@ApiModel(value = "BusDetailVO", description = "封装公交介绍信息")
public class BusDetailVO implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "lineNum", name = "公交车路线", example = "147路")
    private String lineNum;

    @ApiModelProperty(value = "busDirectionType", name = "公交车类型, 0-单向 1-环线", example = "1")
    private String busDirectionType;

    @ApiModelProperty(value = "busDescription", name = "公交车描述", example = "夜宵线")
    private String busDescription;

    @ApiModelProperty(value = "upDirectionInfo", name = "上行方向信息")
    private BusDirectionInfoDTO upDirectionInfo;

    @ApiModelProperty(value = "downDirectionInfo", name = "下行方向信息")
    private BusDirectionInfoDTO downDirectionInfo;

}
