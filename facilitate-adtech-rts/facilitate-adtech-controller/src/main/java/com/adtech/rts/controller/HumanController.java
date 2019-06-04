package com.adtech.rts.controller;

import com.adtech.rts.model.page.Page;
import com.adtech.rts.model.response.HumanResponse;
import com.adtech.rts.model.result.Result;
import com.adtech.rts.service.HumanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Api(value = "/human", tags = "获取患者信息")
@Controller
@RequestMapping("/human")
public class HumanController {


    @Autowired
    private HumanService humanService;


    @ApiOperation(value = "查看患者列表分页")
    @RequestMapping("/getHumanInfoPage")
    @ResponseBody
    public Result<Page> getHumanInfoPage(@ApiParam(name = "pageNum", value = "页码") Integer pageNum,
                                         @ApiParam(name = "regionCode", value = "区域代码") String regionCode,
                                         @ApiParam(name = "organizationCode", value = "医院编码") String organizationCode,
                                         @ApiParam(name = "pageSize", value = "每页条数") Integer pageSize,
                                         @ApiParam(name = "actionType", value = "就诊类型") String actionType,
                                         @ApiParam(name = "name", value = "患者名") String name,
                                         @ApiParam(name = "idCard", value = "身份证") String idCard,
                                         @ApiParam(name = "hospitalName", value = "医院名") String hospitalName) {
        return humanService.getInfoPage(pageNum, regionCode, organizationCode,pageSize,actionType,name,idCard,hospitalName);
    }
}
