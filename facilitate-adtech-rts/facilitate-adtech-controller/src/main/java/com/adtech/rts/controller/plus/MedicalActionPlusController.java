package com.adtech.rts.controller.plus;

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

/**
 * 门诊处方信息
 */
@Api(value = "/medicalaction/plus", tags = "获取患者就诊活动详情信息")
@Controller
@RequestMapping("/medicalaction/plus")
public class MedicalActionPlusController {

    @Autowired
    private MedicalActionService medicalActionService;


    @ApiOperation(value = "plus获取患者就诊活动详情信息")
    @GetMapping("/getMedicalActionInfo")
    @ResponseBody
    public Result<Long> getMedicalActionInfo(
            @ApiParam(name = "rts_secret", value = "秘钥") String rts_secret,
            @ApiParam(name = "rts_token", value = "token") String rts_token){
        return medicalActionService.getMedicalActionCountByOr(rts_secret,rts_token);
    }

}
