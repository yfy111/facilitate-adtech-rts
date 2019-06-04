package com.adtech.rts.model.entity;

import com.adtech.rts.model.entity.base.BizDataInfo;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection="HealthRecord")
public class HealthRecord  extends BizDataInfo {
    private ObjectId _id;
    private String RHBlood;    //	  RH阴性
    private String exposureHistory;    //	  暴露史（数字表示）
    private String phoneNO;    //	  本人电话

    private String disabilitySituation;    //	  残疾情况（字段表示）
    private String FTCC;    //	  厕所情况
    private String PHAS;    //	  常住类型
    private String birthdate;    //	  出生日期
    private String FKEFI;    //	  厨房排风情况
    private String HRN;    //	  档案编号(自动生成)
    private String gender;    //	  个人性别
    private String name;    //	  个人姓名
    private String profession;    //	  个人职业
    private String organization;    //	  工作单位
    private String nationality;    //	  国籍地区代码
    private String householdType;    //	  户口性质
    private String maritalStatus;    //	  婚姻状况
    private String filingWay;    //	  建档方式1诊疗服务 2公卫服务 9其他服务
    private String contactTelephone;    //	  联系人电话
    private String contactName;    //	  联系人姓名
    private String nation;    //	  民族
    private String age;    //	  年龄(临时变量，统计时使用)
    private String FPCC;    //	  禽畜情况
    private String FFTC;    //	  燃料类型
    private String IDCardNo;    //	  身份证号
    private String IDCardType;//	  身份证类型代码
    private String degree;//	  文化程度
    private String ABOBlood;    //	  血型
    private String drugAllergyHistory;    //	  药物过敏史
    private String healthInsuranceType;    //	  医保类型
    private String MEP;    //	  医疗费用支付方式（补充）
    private String geneticHistorySign;    //	  遗传病史
    private String geneticHistoryName;    //	  遗传病史描述
    private String FDC;    //	  饮水情况
    private String actionTime;
    private Date actionTimeFormat;
}
