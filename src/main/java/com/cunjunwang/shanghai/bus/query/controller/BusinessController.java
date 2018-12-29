package com.cunjunwang.shanghai.bus.query.controller;

/**
 * Created by CunjunWang on 2018-12-18.
 */

import com.cunjunwang.shanghai.bus.query.entity.ResultData;
import com.cunjunwang.shanghai.bus.query.model.dto.GetBusCurrentStopDTO;
import com.cunjunwang.shanghai.bus.query.model.vo.BusCurrentStopVO;
import com.cunjunwang.shanghai.bus.query.model.vo.BusDetailVO;
import com.cunjunwang.shanghai.bus.query.service.queryservice.BusQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 业务数据查询接口
 * Created by CunjunWang on 2018/12/16.
 */
@RestController
@RequestMapping("/query")
@Api(value = "查询公交实时到站信息")
public class BusinessController {

    @Autowired
    private BusQueryService busQueryService;

    @RequestMapping(value = "/queryBusCurrentStopInfo", method = RequestMethod.POST)
    @ApiOperation(value = "查询公交实时到站信息", notes = "查询公交实时到站信息")
    public ResultData<BusCurrentStopVO> queryBusCurrentStopInfo(
            @ApiParam(name = "getBusCurrentStopDTO", value = "查询公交实时到站信息传参DTO", required = true)
            @RequestBody GetBusCurrentStopDTO getBusCurrentStopDTO) {
        return new ResultData<>(ResultData.SUCCESS, "", "查询公交实时到站信息成功",
                busQueryService.queryBusCurrentStopInfo(getBusCurrentStopDTO));
    }

    @RequestMapping(value = "/queryBusDetail", method = RequestMethod.POST)
    @ApiOperation(value = "查询公交介绍信息", notes = "查询公交介绍信息")
    public ResultData<BusDetailVO> queryBusDetail(String busLineNumber) {
        return new ResultData<>(ResultData.SUCCESS, "", "查询公交介绍信息成功",
                busQueryService.queryBusDetail(busLineNumber));
    }

    @RequestMapping(value = "/queryBusStationName", method = RequestMethod.POST)
    @ApiOperation(value = "查询公交上下行所有站点名", notes = "查询公交上下行所有站点名")
    public ResultData<List<String>> queryBusStationName(String busLineNumber) {
        return new ResultData<>(ResultData.SUCCESS, "", "查询公交所有站点名完成",
                busQueryService.getLineStationList(busLineNumber));
    }

    @RequestMapping(value = "/queryBusStationLike", method = RequestMethod.POST)
    @ApiOperation(value = "模糊查询公交站点", notes = "模糊查询公交站点")
    public ResultData<List<String>> queryBusStationLike(String busStationLike) {
        return new ResultData<>(ResultData.SUCCESS, "", "模糊查询公交站点完成",
                busQueryService.queryBusStationLike(busStationLike));
    }

    @RequestMapping(value = "/queryBusLineLike", method = RequestMethod.POST)
    @ApiOperation(value = "模糊查询公交线路", notes = "模糊查询公交线路")
    public ResultData<List<String>> queryBusLineLike(String busLineLike) {
        return new ResultData<>(ResultData.SUCCESS, "", "模糊查询公交线路完成",
                busQueryService.queryBusLineLike(busLineLike));
    }


}
