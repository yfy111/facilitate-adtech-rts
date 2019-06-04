package com.adtech.rts.model.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

//西药处方明细
@Data
public class WesternMedicineDetail {
//    药物剂型代码	drugDosageCode



//    药物使用频次代码	drugUseFrequencyCode
//    药物使用频次说明
//            药物使用途径代码
//    药物使用途径说明

    private String drugUseSecondaryDose;//    药物使用次剂量	drugUseSecondaryDose
    private String drugCode;

    private String drugName;//    药物剂型名称

    private String drugUseDoseUnits; //    药物使用次剂量单位
    private String drugUseTotalDose;//    药物使用总剂量
    private String drugUseTotalDoseUnits;//    药物使用总剂量单位
    private int drugUseDay;//
    private String produceManufacturer;//生产厂家
    private String packagingManufacturer;//包装厂家
    private BigDecimal unitPrice;
    private int quantity;//包装数量
    private String standard;//药物规格
    private String dosageFormCode;//剂型
    private String dosageFormName;//剂型
    private String frequency;//频次
    private String drugUseWayCode;//使用途径编码
    private String drugUseWayDescription;

    private boolean isDeleted;
    private Date createTime;
    private Date lastUpdateTime;//最后更新实际
}
