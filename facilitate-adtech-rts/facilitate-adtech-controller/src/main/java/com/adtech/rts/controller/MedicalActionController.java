package com.adtech.rts.controller;

import com.adtech.rts.model.response.medicalAction.MedicalActionResponse;
import com.adtech.rts.model.result.Result;
import com.adtech.rts.service.MedicalActionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 门诊处方信息
 */
@Api(value = "/medicalaction", tags = "获取患者就诊活动详情信息")
@Controller
@RequestMapping("/medicalaction")
public class MedicalActionController {

    @Autowired
    private MedicalActionService medicalActionService;

    @ApiOperation(value = "获取患者就诊活动详情信息")
    @GetMapping("/getMedicalActionInfo")
    @ResponseBody
    public Result<MedicalActionResponse> getMedicalActionInfo(
            @ApiParam(name = "otherIdentities", value = "关联ID", required = true) String otherIdentities,
            @ApiParam(name = "serialNo", value = "就诊号") String serialNo,
            @ApiParam(name = "regionCode", value = "区域代码") String regionCode,
            @ApiParam(name = "organizationCode", value = "医院编码") String organizationCode) {
        return medicalActionService.getMedicalActionInfo(otherIdentities,serialNo, regionCode, organizationCode);
    }


    @ApiOperation(value = "获取患者就诊活动全量list")
    @GetMapping("/getMedicalActionList")
    @ResponseBody
    public Result<List<MedicalActionResponse>> getMedicalActionList(
            @ApiParam(name = "idCard", value = "身份证号", required = true) String idCard) {
        return medicalActionService.getMedicalActionList(idCard);
    }

}
