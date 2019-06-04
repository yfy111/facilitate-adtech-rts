package com.adtech.rts.controller;

import com.adtech.rts.model.response.medicalDataBase.AllDayDataHoursResponse;
import com.adtech.rts.model.result.Result;
import com.adtech.rts.service.AllDayDataHoursService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(value = "/all-day", tags = "当天昨天实时数据对比")
@RequestMapping("/all-day")
@Controller
public class AllDayDataHoursController {

    @Autowired
    private AllDayDataHoursService allDayDataHoursService;

    /**
     * 获取24小时数据
     * @return
     */
    @ApiOperation(value = "当天昨天实时数据对比")
    @GetMapping("/getAllDayData")
    @ResponseBody
    public Result<AllDayDataHoursResponse> getAllDayData(@ApiParam(name="regionCode",value="区域代码")String regionCode,
                                                         @ApiParam(name="organizationCode",value="医院编码")String organizationCode){
        return  allDayDataHoursService.getAllDayData1(regionCode,organizationCode);
    }
}
