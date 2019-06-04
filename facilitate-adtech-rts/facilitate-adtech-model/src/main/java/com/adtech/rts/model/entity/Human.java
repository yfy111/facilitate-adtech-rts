package com.adtech.rts.model.entity;

import com.adtech.rts.model.entity.base.BaseEntity;
import com.adtech.rts.model.entity.common.ItemData;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//病人
@Data
@Document(collection="Human")
public class Human extends BaseEntity {
    private String name;
    private String IDCardNo;
    private List<String> EMPIID=new ArrayList<>();
    private List<MedicalAction> actions=new ArrayList<>();

    private String idCardAddress;
    private String idCardValidDate;//身份证有效期
   // private String name;
    private String birthdate;//出生日期
    private String birthProvince;//出生地址-省（自治区、直辖市）
    private String birthProvinceCode;
    private String birthCity;//出生地址-市（地区、州）
    private String birthCityCode;
    private String birthRegion;//出生地址-县（区）
    private String birthRegionCode;
    private String birthHospital;//出生医院
    private String birthCertificateNO;//出生医学证明号


    private List<String> mobiles=new ArrayList<>();//手机号码
    private List<String> certificates=new ArrayList<>();//证件--------

    private String nationality;//国籍GB/T2659-2000
    private String gender;//性别GB/T2261.1-2003
    private String nation;//民族GB3304-1991


    private String currentAddress;//居住地址描述
    private String currentAddressProvince;//居住地(省)描述
    private String currentAddressCity;//居住地(市)描述
    private String currentAddressCounty;//居住地(县)描述
    private String currentAddressTownship;//居住地(乡)描述
    private String currentAddressVillage;//居住地(村)描述
    private String currentAddressHouseNO;//居住地(门牌号码)描述
    private String currentAddressPostalCode;//邮编
    private String districtDivisionCode;//居住地-区域编码

    private String domicileFullAddress;//户籍地址完整描述
    private String domicileProvince;//户籍地地址-省（自治区、直辖市）
    private String domicileCity;//户籍地地址-市（地区、州）
    private String domicileCounty;//户籍地地址-县（区）
    private String domicileTownship;//户籍地地址-乡（镇、街道办事处）
    private String domicileVillage;//户籍地地址-村（街、路、弄等）
    private String domicileHousenumber;//户籍地地址-门牌号码
    private String domicileAreaCode;//户籍地-区域编码（按行政区划里的最小级地区编码）

    private String politicalLandscape;//政治面貌
    private String degree;//学历GB/T4658-2006
    private String maritalStatus;//婚姻状况GB/T2261.2-2003
    private String profession;//职业GB/T6565-2009

    private String email;//邮箱
    private String organization;//工作单位名称
    private String organizationTel;//工作单位联系电话
    private String permanentAddressSign;//常住地址户籍标志 1：户籍；2：非户籍
    private String deathTime;//死亡时间
    private String deathSign;//死亡标识 1：是；0：否
    private String medicalInsuranceClass;//医疗保险类别CV02.01.204
    private String religion;//宗教
    private String nativeLanguage;//母语
    private String multipletsSign;//多胎标识 1：双胞胎；2：三胞胎、3：四胞胎
    private String multipletsBirthSerialNO;//多胎出生顺序号
    private String racial;//种族代码 1：黄种人；2：白种人；3：黑种人；4：棕种人

    private String ABOBlood;//ABO血型代码 CV04.50.005
    private String RHBlood;//RH血型代码 1：Rh阴性；2：Rh阳性；3：不详
//    private String micardCode;//医保卡卡号
//    private String pocn;//社保号
    private String familyHistory;//家族史
    private String allergyHistory;//过敏史

    private List<ItemData> historyDatas;//如果数据产生差异，放进这里。

    private String validIDCardNo;//是否校验了身份证
    protected List<String> otherIdentities=new ArrayList<>();//其他标识
    private List<String> orignalIds;//原始的Id
    private List<String> orignalCodes;
    private Date lastUpdateTime;
}
