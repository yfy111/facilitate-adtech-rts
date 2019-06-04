package com.adtech.rts.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

//病人
@Data
public class HumanResponse{
    @ApiModelProperty(value="ID")
    private String _id;
    @ApiModelProperty(value="个人编号")
    private List<String> otherIdentities;
    @ApiModelProperty(value="卫生活动类型")
    private String actionType;
    @ApiModelProperty(value="患者姓名")
    private String name;//患者姓名
    @ApiModelProperty(value="手机号")
    private String mobile;//手机号
    @ApiModelProperty(value="身份证号")
    private String IDCardNo;//身份证号
    @ApiModelProperty(value="主治医生名")
    private String doctorName;//医生名
    @ApiModelProperty(value="历史医生")
    private String historyDoctorName;//历史医生
    @ApiModelProperty(value="出院医生")
    private String outpatientDoctorName;//出院医生
    @ApiModelProperty(value="就诊号")
    private String serialNo;//就诊号
//    private String departmentCode;//科室CODE
    @ApiModelProperty(value="科室名称")
    private String departmentName;//科室名称
    @ApiModelProperty(value="机构名称")
    private String orignalOrganizationName;//机构名称
    @ApiModelProperty(value="机构编码")
    private String orignalOrganizationCode;//机构编码
    @ApiModelProperty(value="区域code")
    private String regionCode;//区域code
    @ApiModelProperty(value="区域")
    private String regionName;//区域
    @ApiModelProperty(value="合计费用")
    private String totalPrice;//合计费用
    @ApiModelProperty(value="处方金额")
    private String prescriptionsPrice;//处方金额
    @ApiModelProperty(value="检查检验金额")
    private String checkPrice;//检查检验金额
    @ApiModelProperty(value="时间")
    private String actionTime;//时间
    @ApiModelProperty(value="比对")
    private String validIDCardNo;
//    private Date lastUpdateTime;
    @ApiModelProperty(value="建档信息")
    private String recordMessage;

}
