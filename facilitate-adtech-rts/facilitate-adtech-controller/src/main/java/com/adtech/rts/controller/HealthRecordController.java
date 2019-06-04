package com.adtech.rts.controller;

import com.adtech.rts.model.page.Page;
import com.adtech.rts.model.response.health.HealthRecordResponse;
import com.adtech.rts.model.result.Result;
import com.adtech.rts.service.HealthRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(value = "/health", tags = "建档信息")
@Controller
@RequestMapping("/health")
public class HealthRecordController {


    @Autowired
    private HealthRecordService healthRecordService;

    @ApiOperation(value = "建档信息列表分页")
    @GetMapping("/getInfoPage")
    @ResponseBody
    public Result<Page> getInfoPage(@ApiParam(name = "pageNum", value = "页码") Integer pageNum,
                                    @ApiParam(name = "regionCode", value = "区域代码") String regionCode,
                                    @ApiParam(name = "organizationCode", value = "医院编码") String organizationCode,
                                    @ApiParam(name = "idCard", value = "身份证号") String idCard,
                                    @ApiParam(name = "pageSize", value = "每页条数") Integer pageSize) {
        return healthRecordService.getInfoPage(pageNum, regionCode, organizationCode, idCard, pageSize);
    }


    @ApiOperation(value = "建档信息详情")
    @GetMapping("/getInfo")
    @ResponseBody
    public Result<HealthRecordResponse> getInfo(@ApiParam(name = "_id", value = "Id",required = true) String _id,
                                                @ApiParam(name = "regionCode", value = "区域代码") String regionCode,
                                                @ApiParam(name = "organizationCode", value = "医院编码") String organizationCode,
                                                @ApiParam(name = "idCard", value = "身份证号") String idCard) {
        return healthRecordService.findInfo(regionCode, organizationCode, _id, idCard);
    }
}
