package com.adtech.rts.model.entity;

import com.adtech.rts.model.entity.base.BizDataInfo;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

//处方
@Data
@Document(collection="Prescription")
public class Prescription extends BizDataInfo {

    private String actionTime;
    private String diagnose;//诊断
    private String disease;//疾病
    private String remark;//备注
    private String chargeType;//费用类型
    private String prescriptionType;//处方类型
    private String prescriptionTypeName;//处方类型名称

    private String departmentCode;//就诊科室
    private String departmentName;
    private String serialNo;//就诊号

    private String doctorName;
    private String doctorCode;

    private String prescriptionNo;//处方编号


}
