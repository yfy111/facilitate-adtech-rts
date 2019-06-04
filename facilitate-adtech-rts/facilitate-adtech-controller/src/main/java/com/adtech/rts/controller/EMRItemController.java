package com.adtech.rts.controller;

import com.adtech.rts.model.response.emr.EMRItemResponse;
import com.adtech.rts.model.result.Result;
import com.adtech.rts.service.EMRItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Api(value = "/emr", tags = "获取住院信息")
@Controller
@RequestMapping("/emr")
public class EMRItemController {


    @Autowired
    private EMRItemService emrItemService;


    @ApiOperation(value = "获取住院信息全量列表")
    @GetMapping("/getList")
    @ResponseBody
    public Result<List<EMRItemResponse>> getList(@ApiParam(name="joinTo",value="该人对应的otherIdentities",required=true)String joinTo,
                                                          @ApiParam(name = "serialNo", value = "就诊号", required = true) String serialNo,
                                                          @ApiParam(name="regionCode",value="区域代码",required=true)String regionCode,
                                                          @ApiParam(name="organizationCode",value="医院编码",required=true)String organizationCode) {
        return emrItemService.getList(joinTo,serialNo,regionCode, organizationCode);
    }
}
