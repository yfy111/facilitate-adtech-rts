package com.adtech.rts.model.response.medicalDataBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 24小时医疗数据汇总表
 */
@Data
public class AllDayDataHoursResponse {

    @ApiModelProperty(value="门(急)诊")
    private MedicalActionBase medicalActionDoor= new MedicalActionBase();
//    @ApiModelProperty(value="转诊")
//    private MedicalActionBase medicalActionReferral;
//    @ApiModelProperty(value="输液")
//    private MedicalActionBase medicalActionInfusion;
//    @ApiModelProperty(value="处方金额")
//    private MedicalActionBase medicalActionPrescriptionAmount;
//    @ApiModelProperty(value="输液金额")
//    private MedicalActionBase medicalActionInfusionAmount;
//    @ApiModelProperty(value="人均处方金额")
//    private MedicalActionBase medicalActionPrescriptionAmountAvg;
//    @ApiModelProperty(value="人均输液金额")
//    private MedicalActionBase medicalActionInfusionAmountAvg;



    @ApiModelProperty(value="建档人数")
    private MedicalActionBase fileEstablishment= new MedicalActionBase();
    @ApiModelProperty(value="住院人次")
    private MedicalActionBase hospitalization= new MedicalActionBase();
    @ApiModelProperty(value="出生人次")
    private MedicalActionBase birth= new MedicalActionBase();
    @ApiModelProperty(value="随访人次")
    private MedicalActionBase followUp= new MedicalActionBase();
    @ApiModelProperty(value="疫苗接种人次")
    private MedicalActionBase vaccine= new MedicalActionBase();
    private Date lastDate;//增量时间记录
    private Date tomorrow;//明天


}
