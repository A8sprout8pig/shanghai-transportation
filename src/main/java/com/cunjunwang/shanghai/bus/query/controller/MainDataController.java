package com.cunjunwang.shanghai.bus.query.controller;

import com.cunjunwang.shanghai.bus.query.entity.ResultData;
import com.cunjunwang.shanghai.bus.query.service.dataservice.MainDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by CunjunWang on 2018-12-21.
 */
@RestController
@RequestMapping("/mainData")
@Api(value = "初始化数据API, 根据上海发布提供的公交列表初始化公交基础信息")
public class MainDataController {

    @Autowired
    private MainDataService mainDataService;

    @RequestMapping(value = "/initSaveAllBusData", method = RequestMethod.POST)
    @ApiOperation(value = "初始化所有公交数据", notes = "初始化所有公交数据")
    public ResultData<Map<String, Boolean>> initSaveAllBusData() {
        return new ResultData<>(ResultData.SUCCESS, "", "初始化所有公交数据完成",
                mainDataService.initSaveAllBusData());
    }

    @RequestMapping(value = "/handlePersistenceException", method = RequestMethod.POST)
    @ApiOperation(value = "处理存储异常的公交数据", notes = "处理存储异常的公交数据")
    public ResultData<Map<String, Boolean>> handlePersistenceException() {
        return new ResultData<Map<String, Boolean>>(ResultData.SUCCESS, "", "处理存储异常的公交数据完成",
                mainDataService.handlePersistenceException());
    }

    @RequestMapping(value = "/initSaveAllBusStation", method = RequestMethod.POST)
    @ApiOperation(value = "提取所有公交站点数据并存储", notes = "提取所有公交站点数据并存储")
    public ResultData<Boolean> initSaveAllBusStation() {
        return new ResultData<>(ResultData.SUCCESS, "", "存储公交站点信息完成",
                mainDataService.initSaveAllBusStation());
    }

}