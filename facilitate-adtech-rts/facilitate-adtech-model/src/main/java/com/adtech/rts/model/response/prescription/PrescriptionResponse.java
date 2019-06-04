package com.adtech.rts.model.response.prescription;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PrescriptionResponse {
    @ApiModelProperty(value="该记录ID")
    private List<String> otherIdentities;//该记录ID
    @ApiModelProperty(value="机构名称")
    private String orignalOrganizationName;//机构名称
    @ApiModelProperty(value="机构编码")
    private String orignalOrganizationCode;//机构编码
    @ApiModelProperty(value="诊断时间")
    private String actionTime;//诊断时间
    @ApiModelProperty(value="处方类型:01西药；02草药；03项目；04中成药；defalt01")
    private String  prescriptionType;//处方类型
    @ApiModelProperty(value="处方类型名称")
    private String  prescriptionTypeName;//处方类型名称
    @ApiModelProperty(value="费用类型")
    private String chargeType;//费用类型
    @ApiModelProperty(value="就诊科室")
    private String departmentCode;//就诊科室
    @ApiModelProperty(value="就诊科室名称")
    private String departmentName;//就诊科室名称
    @ApiModelProperty(value="医生")
    private String doctorName;//医生
    @ApiModelProperty(value="处方金额")
    private String totalCost;//处方金额
    @ApiModelProperty(value="处方状态")
    private String prescriptionStatus;//处方状态
    @ApiModelProperty(value="诊断")
    private String diagnose;
    @ApiModelProperty(value="疾病")
    private String disease;//疾病
    @ApiModelProperty(value="备注")
    private String remark;
}
