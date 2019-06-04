package com.adtech.rts.model.entity;

import com.adtech.rts.model.entity.base.DataInfo;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//出生医学证明
@Data
@Document(collection="BornInfo")
public class BornInfo extends DataInfo {
    private ObjectId _id;//Id
    private List<String> otherIdentities=new ArrayList<>();//其他标识
    private List<String> joinTo=new ArrayList<>();//对应标识
    private String name;
    private String IDCardNo;
    private List<String> EMPIID=new ArrayList<>();
    private String gender;//性别GB/T2261.1-2003
    private String birthdate;
    private Date birthdateFormat;

    private String birthProvince;//省
    private String birthCity;//出生城市
    private String birthRegion;//区
    private String birthTownship;//户籍地地址-乡（镇、街道办事处）
    private String birthVillage;//户籍地地址-村（街、路、弄等）

    private String birthAddress;


    private String midwife;//助产士
    private String remark;
    private String birthCertificateNo;//出生医学证明编号
    private String birthPermitNo;//生育许可
    private String bornWeight;//出生体重
    private String bornLength;//出生长度

    private String parity;//产次
    private String gravidity;//孕次
    private String gestation;//孕周
    private String gestationDay;//孕天

    private String childbirthLocationType;//分娩地点"分娩地点 1 医院2家中3途中4 其它"

    private String childbirthType;//"分娩方式 1  阴道自然分娩2 阴道手术助产3 剖宫产9 其他"
    private String healthyType;//"健康状况 1 良好            2 一般3差"
    private String deformity;//畸形畸形 0 无 1 有
    private String asphyxia;//窒息 0 无 1 有
    private String apgar1;//Apgar评分(1分钟)
    private String apgar5;//Apgar评分(5分钟)
    private String apgar10;//Apgar评分(10分钟)



    private String fatherName;//
    private String fatherAge;//
    private String fatherIDCardNo;//

    private String montherName;//
    private String montherAge;//
    private String montherIDCardNo;//


}
