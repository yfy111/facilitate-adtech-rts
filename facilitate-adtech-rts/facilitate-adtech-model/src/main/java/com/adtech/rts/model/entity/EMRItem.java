package com.adtech.rts.model.entity;

import com.adtech.rts.model.entity.base.BizDataInfo;
import lombok.Data;

@Data
public class EMRItem extends BizDataInfo {
    private String itemCode;
    private String inHospitalDiagnoseCode;//住院号
    private String documentCode;
    private String itemName;
    private String documentName;
    private String value;
}
