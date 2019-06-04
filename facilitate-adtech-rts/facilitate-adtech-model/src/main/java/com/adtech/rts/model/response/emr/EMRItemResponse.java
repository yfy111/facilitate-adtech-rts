package com.adtech.rts.model.response.emr;

import com.adtech.rts.model.entity.base.BizDataInfo;
import com.adtech.rts.model.response.health.AdviceResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class EMRItemResponse extends BizDataInfo {
    @ApiModelProperty(value="住院信息列表")
    List<EMRItemListResponse> emrList;
    @ApiModelProperty(value="医嘱")
    List<AdviceResponse> adviceList;
    @ApiModelProperty(value="个人信息")
    private EMRItemHumanResponse humanResponse;

}
