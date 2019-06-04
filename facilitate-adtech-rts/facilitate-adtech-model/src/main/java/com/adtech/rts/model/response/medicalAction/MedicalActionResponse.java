package com.adtech.rts.model.response.medicalAction;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 卫生活动
 */
@Data
public class MedicalActionResponse{
    @ApiModelProperty(value="区域编码")
    protected String regionCode;
    @ApiModelProperty(value="区域名称")
    protected String regionName;
    @ApiModelProperty(value="机构名称")
    private String orignalOrganizationName;//机构名称
    @ApiModelProperty(value="机构编码")
    private String orignalOrganizationCode;//机构编码
    @ApiModelProperty(value="诊断时间")
    private String actionTime;//诊断时间
    @ApiModelProperty(value="诊断类型")
    private String actionType;//诊断类型
    @ApiModelProperty(value="费用类型")
    private String feeType;//费用类型
    //    private String departmentCode;//就诊科室CODE
    @ApiModelProperty(value="就诊科室名称")
    private String departmentName;//就诊科室名称
    @ApiModelProperty(value="就诊号")
    private String serialNo;//就诊号
    //    private String doctorCode;//医生编码
    @ApiModelProperty(value="年龄")
    private String age;//年龄
    @ApiModelProperty(value="患者姓名")
    private String name;//患者姓名
    @ApiModelProperty(value="身份证号")
    private String IDCardNo;//身份证号
    @ApiModelProperty(value="性别")
    private String gender;//性别
    @ApiModelProperty(value="电话")
    private String mobile;//电话
    @ApiModelProperty(value="居住地址描述")
    private String currentAddress;//居住地址描述
    @ApiModelProperty(value="诊断")
    private String diagnose;//诊断
    @ApiModelProperty(value="临床并发症")
    private String clinicalComplications;//临床并发症
    @ApiModelProperty(value="备注")
    private String remark;//备注
    @ApiModelProperty(value="医生名")
    private String doctorName;//医生名
    @ApiModelProperty(value="审核医师")
    private String auditPhysician;//审核医师
    @ApiModelProperty(value="金额")
    private String cost;//金额
    @ApiModelProperty(value="审核人")
    private String examineName;//审核人
    @ApiModelProperty(value="调配人")
    private String deploymentName;//调配人
    @ApiModelProperty(value="核对人")
    private String checkName;//核对人
    @ApiModelProperty(value="配药人")
    private String dispensingName;//配药人
    @ApiModelProperty(value="父级关联字段")
    private List<String> otherIdentities;;
    @ApiModelProperty(value="诊断列表")
    private List<DiagnoseResponse> diagnoseResponses;
}
