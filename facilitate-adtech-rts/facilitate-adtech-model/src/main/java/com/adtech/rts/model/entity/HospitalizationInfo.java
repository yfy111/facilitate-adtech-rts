package com.adtech.rts.model.entity;

import com.adtech.rts.model.entity.base.BizDataInfo;
import lombok.Data;

//出入院信息表
@Data
public class HospitalizationInfo extends BizDataInfo
{
    private String serialNo;//住院号

    private String inHospitalDepartmentCode;//入院科室
    private String inHospitalDepartmentName;
    private String leaveHospitalDepartmentCode;//出院科室
    private String leaveHospitalDepartmentName;


    private String bedCode;//床号
    private String roomCode;
    private String doctorCode;//主治医生
    private String doctorName;//主治医生

    private String historyDoctorCode;//曾经医生
    private String historyDoctorName;//曾经医生

    private String directorDoctorCode;
    private String directorDoctorName;//主任医生


    private String leaveHospitalTime;//出院时间
    private String inHospitalTime;//入院时间

    private String treatmentResult;//治疗结果


    private String inHospitalDiagnoseCode;//诊断编码
    private String inHospitalDiagnose;
    private String diagnoseType;//诊断类型

    private String leaveHospitalDiagnoseCode;
    private String leaveHospitalDiagnose;

    private String patientType;//病人类别
    private String leaveHospitalType;//出院类别
    private String inHospitalType;//入院类别

    private String createUser;//录入人

    private String outpatientDoctorCode;//门诊医生
    private String outpatientDoctorName;//门诊医生

    private String outpatientDiagnoseCode;//门诊诊断编码
    private String outpatientDiagnose;

    private String complicationCode;//并发症code
    private String complicationName;
}
