package com.adtech.rts.model.entity;

import com.adtech.rts.model.entity.base.BizDataInfo;
import lombok.Data;

//医嘱
@Data

public class Advice  extends BizDataInfo {
    private String hospitalizationSerialNo;//住院号
    private String serialNo;//医嘱编号
    private String prescriptionNo;//处方号
    private String code;//编码
    private String name;//医嘱名称
    private String content;//医嘱内容
    private String amount;//数量
    private String drugUseWay;//用法
    private String times;//当日执行次数
    private String frequency;//频次,频率
    private String speed;//速度
    private String unit;//单位
    private String unitPrice;//单价
    private String dosage;//用量
    private String medicationTime;//用药时间
    private String adviceFromDoctorName;
    private String adviceFromDoctorCode;//开医嘱人
    private String checkDoctorName;
    private String checkDoctorCode;//核对人
    private String excuteNurseCode;//执行护士\
    private String excuteNurseName;//执行护
    private String excuteDoctorName;//
    private String excuteTime;//执行时间

    private String departmentCode;//就诊科室
    private  String departmentName;
    private String excuteDepartmentCode;//执行科室
    private String excuteDepartmentName;
    private String adviceType;//医嘱类型
    private String remark;

    private String beginTime;
    private String endTime;
    private String cancelTime;
    private String stopDoctorCode;
    private String  stopDoctorName;
    private String stopNurseCode;//医嘱停止护士
    private String stopNurseName;
}
