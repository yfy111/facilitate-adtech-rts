package com.adtech.rts.model.response.birth;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

//出生医学证明
@Data
public class BornInfoResponse {
    @ApiModelProperty(value = "ID")
    private String _id;
    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "性别")
    private String gender;//性别GB/T2261.1-2003
    @ApiModelProperty(value = "生日")
    private String birthdate;
    @ApiModelProperty(value = "出生省")
    private String birthProvince;//省
    @ApiModelProperty(value = "出生城市")
    private String birthCity;//出生城市
    @ApiModelProperty(value = "出生区县")
    private String birthRegion;//区
    @ApiModelProperty(value = "出生地址")
    private String birthAddress;
    @ApiModelProperty(value = "接生机构")
    private String orignalOrganizationName;
    @ApiModelProperty(value = "助产护士")
    private String midwife;//助产士
    @ApiModelProperty(value = "出生医学编号")
    private String birthCertificateNo;//出生医学证明编号
    @ApiModelProperty(value = "生育许可")
    private String birthPermitNo;//生育许可
    @ApiModelProperty(value = "出生体重")
    private String bornWeight;//出生体重
    @ApiModelProperty(value = "出生长度")
    private String bornLength;//出生长度
    @ApiModelProperty(value = "产次")
    private String parity;//产次
    @ApiModelProperty(value = "孕次")
    private String gravidity;//孕次
    @ApiModelProperty(value = "孕周")
    private String gestation;//孕周
    @ApiModelProperty(value = "孕天")
    private String gestationDay;//孕天
    @ApiModelProperty(value = "分娩地点 1 医院2家中3途中4 其它")
    private String childbirthLocationType;//分娩地点"分娩地点 1 医院2家中3途中4 其它"
    @ApiModelProperty(value = "分娩方式 1  阴道自然分娩2 阴道手术助产3 剖宫产9 其他")
    private String childbirthType;//"分娩方式 1  阴道自然分娩2 阴道手术助产3 剖宫产9 其他"
    @ApiModelProperty(value = "健康状况 1 良好 2 一般3差")
    private String healthyType;//""
    @ApiModelProperty(value = "畸形畸形 0 无 1 有")
    private String deformity;//
    @ApiModelProperty(value = "父亲名")
    private String fatherName;//
    @ApiModelProperty(value = "父亲民族")
    private String fatherNation;
    @ApiModelProperty(value = "父亲身份证")
    private String fatherIDCardNo;//
    @ApiModelProperty(value = "母亲名")
    private String motherName;//
    @ApiModelProperty(value = "母亲民族")
    private String motherNation;
    @ApiModelProperty(value = "母亲身份证")
    private String motherIDCardNo;//

}
