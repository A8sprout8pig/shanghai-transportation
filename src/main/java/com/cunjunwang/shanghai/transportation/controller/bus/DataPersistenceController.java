package com.cunjunwang.shanghai.transportation.controller.bus;

import com.cunjunwang.shanghai.transportation.entity.ResultData;
import com.cunjunwang.shanghai.transportation.model.dto.BatchSaveBusInfoDTO;
import com.cunjunwang.shanghai.transportation.model.vo.BusLineDataVO;
import com.cunjunwang.shanghai.transportation.service.dataService.BusDataPersistenceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by CunjunWang on 2018-12-20.
 */
@RestController
@RequestMapping("/database")
@Api(value = "[基础数据]数据持久化API", tags = "[基础数据]数据持久化API")
public class DataPersistenceController {

    @Autowired
    private BusDataPersistenceService busDataPersistenceService;

    @RequestMapping(value = "/getBusLineDataByLineNumber", method = RequestMethod.GET)
    public ResultData<BusLineDataVO> getBusLineDataByLineNumber(String lineNumber) {
        return new ResultData<>(ResultData.SUCCESS, "", "获取公交线路信息成功",
                busDataPersistenceService.getBusLineDataByLineNumber(lineNumber));
    }

    @RequestMapping(value = "/saveBusLineDataByLineNumber", method = RequestMethod.POST)
    public ResultData<Boolean> saveBusLineDataByLineNumber(String lineNumber) {
        return new ResultData<>(ResultData.SUCCESS, "", "持久化存储公交线路信息成功",
                busDataPersistenceService.saveBusLineDataByLineNumber(lineNumber));
    }

    @RequestMapping(value = "/batchSaveBusLineDataByLineNumbers", method = RequestMethod.POST)
    public ResultData<Map<String, Boolean>> batchaveBusLineDataByLineNumbers(
            @ApiParam(name = "batchSaveBusInfoDTO", value = "传递要批量存储的公交线路", required = true)
            @RequestBody BatchSaveBusInfoDTO batchSaveBusInfoDTO) {
        return new ResultData<>(ResultData.SUCCESS, "", "批量持久化存储公交线路信息成功",
                busDataPersistenceService.batchSaveBusLineDataByLineNumbers(batchSaveBusInfoDTO));
    }

}
