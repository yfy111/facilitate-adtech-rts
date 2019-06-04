package com.adtech.rts.model.response.medicalAction;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PingAnMedicalActionResponse {

    @ApiModelProperty(value="科室")
    private String departmentName;
    @ApiModelProperty(value="病症")
    private String diseaseName;
    @ApiModelProperty(value="时间")
    private String visitTime;
    @ApiModelProperty(value="性别")
    private String gender;
    @ApiModelProperty(value="医院等级")
    private String hospitalLevel;
    @ApiModelProperty(value="就诊类型")
    private String medicalCategory;
    @ApiModelProperty(value="诊断")
    private String diagnose;
    @ApiModelProperty(value="姓名")
    private String name;
    @ApiModelProperty(value="医院名")
    private String hospital;
    @ApiModelProperty(value="年龄")
    private String age;
    @ApiModelProperty(value="就诊号")
    private String serialNo;
}
