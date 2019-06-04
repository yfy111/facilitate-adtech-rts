package com.adtech.rts.model.entity.finance;

import com.adtech.rts.model.entity.base.BizDataInfo;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

//费用明细
@Data
@Document(collection="FeeDetail")
public class FeeDetail extends BizDataInfo {
    private String  amount;//数量
    private String unit;
    private String unitPrice;//单价
    private String totalCost;//总价

    private String drugFromDepartmentCode;
    private String drugFromDepartmentName;//开药科室

    private String implementDepartmentCode;//执行科室
    private String implementDepartmentName;


    private String drugFromDoctorCode;
    private String drugFromDoctorName;//开药医生

    private String feeType;//费用类型
    private String hairMedicinePeople;//发药

    private String balanceNo;//结算单号
    private String prescriptionNo;//处方编号
    private String index;//序号

    private String refundAmount;//退方数量
    private String refundTime;//退方时间
    private String refundPeople;//退方人
    private String refundPrescriptionNo;//退方单号

}
