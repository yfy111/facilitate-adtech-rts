package com.adtech.rts.model.entity;

import com.adtech.rts.model.entity.base.BizDataInfo;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection="PrescriptionDetail")
public class PrescriptionDetail extends BizDataInfo {

    private String drugUseSecondaryDose;//    药物使用次剂量	drugUseSecondaryDose
    private String drugCode; // 药物编码
    private String drugName;//    药物名称
    private String drugUseDoseUnits; //    药物使用次剂量单位
    private String drugUseTotalDose;//    药物使用总剂量
    private String drugUseTotalDoseUnits;//    药物使用总剂量单位
    private String drugUseDay;//用药天数
    private String produceManufacturer;//生产厂家
    private String packagingManufacturer;//包装厂家
    private String unitPrice; //单价
    private String quantity;//数量
    private String standard;//药物规格
    private String dosageFormCode;//剂型
    private String dosageFormName;//剂型
    private String frequency;//频次,频率
    private String times;//次数
    private String drugUseWayCode;//使用途径编码
    private String drugUseWay;//用法
    private String unit;//单位
    private String checkPeople;//核对人
    private String dispenser;//配药人
    private String deletePeople;//作废人
    private String executor;//执行人
    private String batchNumber;//批号
    private String departmentCode;//开方科室
    private String departmentName;
    private String implementDepartmentCode;//执行科室
    private String implementDepartmentName;
    private String remark;//备注

}
