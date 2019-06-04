package com.adtech.rts.controller;

/**
 * 就诊记录列表
 */

import com.adtech.rts.model.page.Page;
import com.adtech.rts.model.response.prescription.PrescriptionResponse;
import com.adtech.rts.model.result.Result;
import com.adtech.rts.service.PrescriptionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 门诊处方信息
 */
@Api(value = "/prescription", tags = "获取患者就诊记录列表分页")
@Controller
@RequestMapping("/prescription")
public class PrescriptionController {


    @Autowired
    private PrescriptionService prescriptionService;

    @ApiOperation(value = "获取患者处方列表分页")
    @GetMapping("/getPrescriptionPage")
    @ResponseBody
    public Result<Page> getPrescriptionPage(@ApiParam(name="otherIdentities",value="该记录ID",required=true)String otherIdentities,
                                            @ApiParam(name="regionCode",value="区域代码",required=true)String regionCode,
                                            @ApiParam(name="serialNo",value="就诊号",required=true)String serialNo,
                                            @ApiParam(name="organizationCode",value="医院编码")String organizationCode,
                                            @ApiParam(name="pageNum",value="页码")Integer pageNum,
                                            @ApiParam(name="pageSize",value="每页条数")Integer pageSize){
        return prescriptionService.getPrescriptionPage(otherIdentities,regionCode,serialNo,organizationCode,pageNum,pageSize);
    }

    @ApiOperation(value = "获取患者处方全量列表")
    @GetMapping("/getPrescriptionList")
    @ResponseBody
    public Result<PrescriptionResponse> getPrescriptionList(@ApiParam(name="otherIdentities",value="该记录ID",required=true)String otherIdentities,
                                                            @ApiParam(name="regionCode",value="区域代码",required=true)String regionCode,
                                                            @ApiParam(name="serialNo",value="就诊号",required=true)String serialNo,
                                                            @ApiParam(name="organizationCode",value="医院编码")String organizationCode){
        return prescriptionService.getPrescriptionList(otherIdentities,regionCode,serialNo,organizationCode);
    }
}
