package com.adtech.rts.controller;

import com.adtech.rts.model.result.Result;
import com.adtech.rts.service.OutpatientDepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(value = "/out-dep", tags = "当天就诊人次统计")
@RequestMapping("/out-dep")
@Controller
public class OutpatientDepartmentController {

    @Autowired
    private OutpatientDepartmentService outpatientDepartmentService;

    @ApiOperation(value = "当天就诊人次统计")
    @GetMapping("/getOutpatientDepartmentPopulation")
    @ResponseBody
    public Result getOutpatientDepartmentPopulation(@ApiParam(name="hours",value="小时数")Integer hours,
                                                    @ApiParam(name="regionCode",value="区域代码")String regionCode,
                                                    @ApiParam(name="organizationCode",value="医院编码")String organizationCode){
        return outpatientDepartmentService.getOutpatientDepartmentPopulation(hours,regionCode,organizationCode);
    }
}
