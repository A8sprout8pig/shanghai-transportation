package com.cunjunwang.shanghai.transportation.controller.flight;

import com.cunjunwang.shanghai.transportation.entity.ResultData;
import com.cunjunwang.shanghai.transportation.service.dataService.flight.FlightBaseDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public ResultData<String> getCityList() {
        return new ResultData<>(ResultData.SUCCESS, "", "获取城市列表成功",
                flightBaseDataService.getCityList());
    }

}
