package com.cunjunwang.shanghai.transportation.controller.flight;

import com.cunjunwang.shanghai.transportation.entity.ResultData;
import com.cunjunwang.shanghai.transportation.model.dto.flight.AirportBaseDTO;
import com.cunjunwang.shanghai.transportation.service.dataService.flight.FlightBaseDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by CunjunWang on 2019-05-03.
 */
@RestController
@RequestMapping("/base")
@Api(value = "[基础数据]获取航班基础信息", tags = "[基础数据]获取航班基础信息")
public class FlightBaseDataController {

    @Autowired
    private FlightBaseDataService flightBaseDataService;

    @RequestMapping(value = "/getCityList", method = RequestMethod.GET)
    @ApiOperation(value = "获取城市列表", notes = "获取城市列表")
    public ResultData<List<AirportBaseDTO>> getCityList(
            @ApiParam(name = "isInternal", value = "是否国内航班", required = true)
            @RequestParam(required = true) Boolean isInternal,
            @ApiParam(name = "isArrival", value = "是否到达航班", required = true)
            @RequestParam(required = true) Boolean isArrival,
            @ApiParam(name = "flightType", value = "航班类型", required = true)
            @RequestParam(required = true) String flightType) {
        return new ResultData<>(ResultData.SUCCESS, "", "获取城市列表成功",
                flightBaseDataService.getAirportList(isInternal, isArrival, flightType));
    }

    @RequestMapping(value = "/getFlightList", method = RequestMethod.POST)
    @ApiOperation(value = "获取航班列表", notes = "获取航班列表")
    public ResultData<String> getFlightList() {
        return new ResultData<>(ResultData.SUCCESS, "", "获取航班列表成功",
                flightBaseDataService.getFlightListString());
    }

}
