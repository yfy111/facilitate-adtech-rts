package com.adtech.rts.model.entity;


import com.adtech.rts.model.entity.base.BizDataInfo;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

//卫生活动
@Document(collection="MedicalAction")
@Data
public class MedicalAction extends BizDataInfo {
    private String actionType;
    private String actionTime;
    private Date actionTimeFormat;
    private String serialNo;//就诊号
    private String departmentCode;//就诊科室编号
    private String departmentName;//就诊科室名
    private String doctorName;
    private String doctorCode;
    private String organization;//单位住址
    private String dataBase;

    //处方
//    private List<Prescription> prescriptions=new ArrayList<>();
    //费用详细
//    private List<FeeDetail> feeDetails=new ArrayList<>();
}

