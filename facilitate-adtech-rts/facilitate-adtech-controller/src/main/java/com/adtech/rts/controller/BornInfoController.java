package com.adtech.rts.controller;

import com.adtech.rts.model.page.Page;
import com.adtech.rts.model.response.birth.BornInfoResponse;
import com.adtech.rts.model.result.Result;
import com.adtech.rts.service.BornInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(value = "/birth", tags = "出生医学证明")
@Controller
@RequestMapping("/birth")
public class BornInfoController {


    @Autowired
    private BornInfoService bornInfoService;

    @ApiOperation(value = "出生医学证明列表分页")
    @GetMapping("/getInfoPage")
    @ResponseBody
    public Result<Page> getInfoPage(@ApiParam(name = "pageNum", value = "页码") Integer pageNum,
                                    @ApiParam(name = "regionCode", value = "区域代码") String regionCode,
                                    @ApiParam(name = "organizationCode", value = "医院编码") String organizationCode,
                                    @ApiParam(name = "idCard", value = "身份证号") String idCard,
                                    @ApiParam(name = "pageSize", value = "每页条数") Integer pageSize) {
        return bornInfoService.getInfoPage(pageNum, regionCode, organizationCode, idCard, pageSize);
    }


    @ApiOperation(value = "出生医学证明详情")
    @GetMapping("/getInfo")
    @ResponseBody
    public Result<BornInfoResponse> getInfo(@ApiParam(name = "pageNum", value = "Id") String _id,
                                            @ApiParam(name = "regionCode", value = "区域代码") String regionCode,
                                            @ApiParam(name = "organizationCode", value = "医院编码") String organizationCode,
                                            @ApiParam(name = "idCard", value = "身份证号") String idCard) {
        return bornInfoService.findInfo(regionCode, organizationCode, _id,idCard);
    }
}
