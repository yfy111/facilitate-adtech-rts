package com.adtech.rts.model.response.prescription;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PrescriptionDetailResponse {

    @ApiModelProperty(value="药物使用次剂量")
    private String drugUseSecondaryDose;//    药物使用次剂量	drugUseSecondaryDose
    @ApiModelProperty(value="药物编码")
    private String drugCode; // 药物编码
    @ApiModelProperty(value="药物名称")
    private String drugName;//    药物名称
    @ApiModelProperty(value="药物使用次剂量单位")
    private String drugUseDoseUnits; //    药物使用次剂量单位
    @ApiModelProperty(value="药物使用总剂量")
    private String drugUseTotalDose;//    药物使用总剂量
    @ApiModelProperty(value="药物使用总剂量单位")
    private String drugUseTotalDoseUnits;//    药物使用总剂量单位
    @ApiModelProperty(value="用药天数")
    private String drugUseDay;//用药天数
    @ApiModelProperty(value="生产厂家")
    private String produceManufacturer;//生产厂家
    @ApiModelProperty(value="包装厂家")
    private String packagingManufacturer;//包装厂家
    @ApiModelProperty(value="单价")
    private String unitPrice; //单价
    @ApiModelProperty(value="数量")
    private String quantity;//数量
    @ApiModelProperty(value="药物规格")
    private String standard;//药物规格
    @ApiModelProperty(value="剂型CODE")
    private String dosageFormCode;//剂型CODE
    @ApiModelProperty(value="剂型")
    private String dosageFormName;//剂型
    @ApiModelProperty(value="频次,频率")
    private String frequency;//频次,频率
    @ApiModelProperty(value="次数")
    private String times;//次数
    @ApiModelProperty(value="使用途径编码")
    private String drugUseWayCode;//使用途径编码
    @ApiModelProperty(value="用法")
    private String drugUseWay;//用法
    @ApiModelProperty(value="单位")
    private String unit;//单位
    @ApiModelProperty(value="核对人")
    private String checkPeople;//核对人
    @ApiModelProperty(value="配药人")
    private String dispenser;//配药人
    @ApiModelProperty(value="作废人")
    private String deletePeople;//作废人
    @ApiModelProperty(value="执行人")
    private String executor;//执行人
    @ApiModelProperty(value="批号")
    private String batchNumber;//批号
    @ApiModelProperty(value="开方科室Code")
    private String departmentCode;//开方科室
    @ApiModelProperty(value="开方科室")
    private String departmentName;
    @ApiModelProperty(value="执行科室CODE")
    private String implementDepartmentCode;//执行科室
    @ApiModelProperty(value="执行科室")
    private String implementDepartmentName;
    @ApiModelProperty(value="备注")
    private String remark;//备注
}
