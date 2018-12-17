package com.cunjunwang.shanghai.bus.query.controller;

import com.cunjunwang.shanghai.bus.query.entity.ResultData;
import com.cunjunwang.shanghai.bus.query.model.dto.BusLineNumberDTO;
import com.cunjunwang.shanghai.bus.query.model.dto.BusSidDTO;
import com.cunjunwang.shanghai.bus.query.model.dto.BusStationDTO;
import com.cunjunwang.shanghai.bus.query.service.BusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 基础数据查询接口
 * Created by CunjunWang on 2018/12/16.
 */
@RestController
@RequestMapping("/base")
@Api(value = "[CRUD API]基础数据API")
public class BaseDataController {

    @Autowired
    private BusService busService;

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @ApiOperation(value = "测试接口", notes = "测试接口")
    public ResultData<String> test(
            @ApiParam(name = "testParam", value = "测试参数", required = true)
            @RequestParam String testParam) {
        return new ResultData<>(ResultData.SUCCESS, "", "测试接口调用成功", testParam);
    }

    @RequestMapping(value = "/getBusSID", method = RequestMethod.POST)
    @ApiOperation(value = "获取公交线路SID", notes = "获取公交线路SID")
    public ResultData<BusSidDTO> getBusSID(
            @ApiParam(name = "busLineNumberDTO", value = "传递要查询的公交线路")
            @RequestBody BusLineNumberDTO busLineNumberDTO) {
        return new ResultData<>(ResultData.SUCCESS, "", "获取公交线路SID成功", busService.getBusSID(busLineNumberDTO));
    }

    @RequestMapping(value = "/getBusStationsBySid", method = RequestMethod.GET)
    @ApiOperation(value = "根据公交线路SID获取站点信息", notes = "根据公交线路SID获取站点信息")
    public ResultData<List<BusStationDTO>> getBusStationsBySid(String sid) {
        return new ResultData<>(ResultData.SUCCESS, "", "获取公交线路站点信息成功", busService.getBusStationsBySid(sid));
    }

}
