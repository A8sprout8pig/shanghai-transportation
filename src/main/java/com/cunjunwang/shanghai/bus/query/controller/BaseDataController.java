package com.cunjunwang.shanghai.bus.query.controller;

import com.cunjunwang.shanghai.bus.query.entity.ResultData;
import com.cunjunwang.shanghai.bus.query.model.dto.BusLineNumberDTO;
import com.cunjunwang.shanghai.bus.query.model.dto.BusSidDTO;
import com.cunjunwang.shanghai.bus.query.model.dto.BusStationDTO;
import com.cunjunwang.shanghai.bus.query.model.dto.GetBusStopDTO;
import com.cunjunwang.shanghai.bus.query.model.vo.BusCurrentStopVO;
import com.cunjunwang.shanghai.bus.query.service.BusBaseDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 基础数据查询接口
 * Created by CunjunWang on 2018/12/16.
 */
@RestController
@RequestMapping("/base")
@Api(value = "基础数据API, 封装上海发布接口, 三次调用得到公交实时信息")
public class BaseDataController {

    @Autowired
    private BusBaseDataService busBaseDataService;

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
            @ApiParam(name = "busLineNumberDTO", value = "传递要查询的公交线路", required = true)
            @RequestBody BusLineNumberDTO busLineNumberDTO) {
        return new ResultData<>(ResultData.SUCCESS, "", "获取公交线路SID成功", busBaseDataService.getBusSID(busLineNumberDTO));
    }

    @RequestMapping(value = "/getBusStationsBySid", method = RequestMethod.GET)
    @ApiOperation(value = "根据公交线路SID获取站点信息", notes = "根据公交线路SID获取站点信息")
    public ResultData<Map<String, BusStationDTO>> getBusStationsBySid(String sid) {
        return new ResultData<>(ResultData.SUCCESS, "", "获取公交线路站点信息成功", busBaseDataService.getBusStationsBySid(sid));
    }

    @RequestMapping(value = "/getStop", method = RequestMethod.POST)
    @ApiOperation(value = "获取公交实时站点信息", notes = "获取公交实时站点信息")
    public ResultData<BusCurrentStopVO> getStop(
            @ApiParam(name = "getBusStopDTO", value = "获取公交实时站点信息", required = true)
            @RequestBody GetBusStopDTO getBusStopDTO) {
        return new ResultData<>(ResultData.SUCCESS, "", "获取公交实时站点信息成功", busBaseDataService.getStop(getBusStopDTO));
    }

}
