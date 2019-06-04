package com.adtech.rts.controller;

import com.adtech.rts.model.entity.Advice;
import com.adtech.rts.model.result.Result;
import com.adtech.rts.service.AdviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Api(value = "/advice", tags = "获得医嘱")
@RequestMapping("/advice")
@Controller
public class AdviceController {

    @Autowired
    private AdviceService adviceService;

    /**
     * 获得医嘱List
     * @return
     */
    @ApiOperation(value = "获得医嘱List")
    @GetMapping("/getList")
    @ResponseBody
    public Result<List<Advice>> getList(@ApiParam(name="regionCode",value="区域代码")String regionCode,
                                              @ApiParam(name="organizationCode",value="医院编码")String organizationCode,
                                              @ApiParam(name="hospitalizationSerialNo",value="住院号",required=true)String hospitalizationSerialNo){
        return  adviceService.getList(regionCode,organizationCode,hospitalizationSerialNo);
    }
}
