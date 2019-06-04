package com.adtech.rts.controller;

/**
 * 就诊记录列表
 */

import com.adtech.rts.model.response.prescription.PrescriptionDetailResponse;
import com.adtech.rts.model.result.Result;
import com.adtech.rts.service.PrescriptionDetailService;
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
@Api(value = "/prescription-detail", tags = "获取患者处方详情")
@Controller
@RequestMapping("/prescription-detail")
public class PrescriptionDetailController {


    @Autowired
    private PrescriptionDetailService detailService;

    @ApiOperation(value = "获取患者处方详情")
    @GetMapping("/getPrescriptionDetailPage")
    @ResponseBody
    public Result<List<PrescriptionDetailResponse>> getPrescriptionPage(@ApiParam(name="otherIdentities",value="该记录ID",required=true)String otherIdentities,
                                                                        @ApiParam(name="regionCode",value="区域代码")String regionCode){
        return detailService.findByParams(otherIdentities,regionCode);
    }
}
