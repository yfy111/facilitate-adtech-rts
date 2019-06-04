package com.adtech.rts.controller.plus;

import com.adtech.rts.model.entity.Human;
import com.adtech.rts.model.response.HumanResponse;
import com.adtech.rts.model.response.bpi.BasicPopulationInformationShowData;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Api(value = "/human/plus", tags = "plus获取患者信息")
@Controller
@RequestMapping("/human/plus")
public class HumanPlusController {


    @Autowired
    private HumanService humanService;


    @ApiOperation(value = "plus根据身份证查找患者信息")
    @GetMapping("/getHuman")
    @ResponseBody
    public Result<Human> getHumanInfoPage(@ApiParam(name = "IDCardNo", value = "身份证") String IDCardNo) {
        return humanService.getInfo(IDCardNo);
    }

    @ApiOperation(value = "获取家庭关系")
    @GetMapping("/getFamily")
    @ResponseBody
    public Result<BasicPopulationInformationShowData> getFamily(HttpServletRequest request) {
        return humanService.getBasicPopulationInformationShowDataList(request);
    }

}
