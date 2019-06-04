package com.adtech.rts.model.response.emr;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EMRItemHumanResponse {
    @ApiModelProperty(value="患者姓名")
    private String name;//患者姓名
    @ApiModelProperty(value="手机号")
    private String mobile;//手机号
    @ApiModelProperty(value="身份证号")
    private String IDCardNo;//身份证号
    @ApiModelProperty(value="工作单位名称")
    private String organization;//工作单位名称
    @ApiModelProperty(value="居住地址描述")
    private String currentAddress;//居住地址描述
    @ApiModelProperty(value="政治面貌")
    private String politicalLandscape;//政治面貌
    @ApiModelProperty(value="职业")
    private String profession;//职业GB/T6565-2009
    @ApiModelProperty(value="性别")
    private String gender;//性别GB/T2261.1-2003
    @ApiModelProperty(value="民族")
    private String nation;//民族GB3304-1991
    @ApiModelProperty(value="出生地址")
    private String birthProvince;//出生地址
    @ApiModelProperty(value="机构名称")
    private String orignalOrganizationName;//机构名称
    @ApiModelProperty(value="就诊科室名称")
    private String departmentName;//就诊科室名称
    @ApiModelProperty(value="住院时间")
    private String actionTime;//就诊活动时间
    @ApiModelProperty(value="医生名")
    private String doctorName;



}
