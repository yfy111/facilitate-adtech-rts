package com.adtech.rts.model.response.medicalDataBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 卫生活动
 */
@Data
public class MedicalActionBase {
    @ApiModelProperty(value="今日实时")
    private String nowDay;//今日实时
    @ApiModelProperty(value="昨日")
    private String yesterDay;//昨日
    @ApiModelProperty(value="同比")
    private String year_on_year;//同比
    @ApiModelProperty(value="rise：上升，drop：下降，flat：持平")
    private String state;
}
