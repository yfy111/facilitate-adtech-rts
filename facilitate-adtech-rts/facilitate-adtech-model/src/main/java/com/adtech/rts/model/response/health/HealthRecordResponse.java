package com.adtech.rts.model.response.health;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@Data
public class HealthRecordResponse {
    @ApiModelProperty(value="_id")
    private String _id;
    @ApiModelProperty(value="关联编号")
    private List<String> otherIdentities;
    @ApiModelProperty(value="RH阴性")
    private String RHBlood;    //	  RH阴性
    @ApiModelProperty(value="暴露史（数字表示）")
    private String exposureHistory;    //	  暴露史（数字表示）
    @ApiModelProperty(value="本人电话")
    private String phoneNO;    //	  本人电话
    @ApiModelProperty(value="残疾情况（字段表示）")
    private String disabilitySituation;    //	  残疾情况（字段表示）
    @ApiModelProperty(value="厕所情况")
    private String FTCC;    //	  厕所情况
    @ApiModelProperty(value="常住类型")
    private String PHAS;    //	  常住类型
    @ApiModelProperty(value="出生日期")
    private String birthTime;    //	  出生日期
    @ApiModelProperty(value="厨房排风情况")
    private String FKEFI;    //	  厨房排风情况
    @ApiModelProperty(value="档案编号(自动生成)")
    private String hrn;    //	  档案编号(自动生成)
    @ApiModelProperty(value="个人性别")
    private String gender;    //	  个人性别
    @ApiModelProperty(value="个人姓名")
    private String name;    //	  个人姓名
    @ApiModelProperty(value="个人职业")
    private String occupation;    //	  个人职业
    @ApiModelProperty(value="工作单位")
    private String workUnit;    //	  工作单位
    @ApiModelProperty(value="国籍地区代码")
    private String nationality;    //	  国籍地区代码
    @ApiModelProperty(value="户口性质")
    private String householdType;    //	  户口性质
    @ApiModelProperty(value="婚姻状况")
    private String maritalStatus;    //	  婚姻状况
    @ApiModelProperty(value="建档方式1诊疗服务 2公卫服务 9其他服务")
    private String filingWay;    //	  建档方式1诊疗服务 2公卫服务 9其他服务
    @ApiModelProperty(value="联系人电话")
    private String contactTelephone;    //	  联系人电话
    @ApiModelProperty(value="联系人姓名")
    private String contactName;    //	  联系人姓名
    @ApiModelProperty(value="民族")
    private String nation;    //	  民族
    @ApiModelProperty(value="年龄(临时变量，统计时使用)")
    private String age;    //	  年龄(临时变量，统计时使用)
    @ApiModelProperty(value="禽畜情况")
    private String FPCC;    //	  禽畜情况
    @ApiModelProperty(value="燃料类型")
    private String FFTC;    //	  燃料类型
    @ApiModelProperty(value="身份证号")
    private String IDCardNO;    //	  身份证号
    @ApiModelProperty(value="身份证类型代码")
    private String IDCardType;//	  身份证类型代码
    @ApiModelProperty(value="文化程度")
    private String degree;//	  文化程度
    @ApiModelProperty(value="血型")
    private String ABOBlood;    //	  血型
    @ApiModelProperty(value="药物过敏史")
    private String drugAllergyHistory;    //	  药物过敏史
    @ApiModelProperty(value="医保类型")
    private String healthInsuranceType;    //	  医保类型
    @ApiModelProperty(value="医疗费用支付方式（补充）")
    private String MEP;    //	  医疗费用支付方式（补充）
    @ApiModelProperty(value=" 遗传病史")
    private String geneticHistorySign;    //	  遗传病史
    @ApiModelProperty(value="遗传病史描述")
    private String geneticHistoryName;    //	  遗传病史描述
    @ApiModelProperty(value="饮水情况")
    private String FDC;    //	  饮水情况
    @ApiModelProperty(value="建档时间")
    private String actionTime;
    @ApiModelProperty(value="区域名")
    private String regionName;
    @ApiModelProperty(value="区域编号")
    private String regionCode;
    @ApiModelProperty(value="医院编码")
    private String orignalOrganizationCode;
    @ApiModelProperty(value="医院名")
    private String orignalOrganizationName;
    @ApiModelProperty(value="居住地(省)描述")
    private String currentAddressProvince;//居住地(省)描述
    @ApiModelProperty(value="居住地(市)描述")
    private String currentAddressCity;//居住地(市)描述
    @ApiModelProperty(value="居住地(县)描述")
    private String currentAddressCounty;//居住地(县)描述
    @ApiModelProperty(value="居住地(乡)描述")
    private String currentAddressTownship;//居住地(乡)描述
    @ApiModelProperty(value="居住地(村)描述")
    private String currentAddressVillage;//居住地(村)描述
    @ApiModelProperty(value="居住地(门牌号码)描述")
    private String currentAddressHouseNO;//居住地(门牌号码)描述
    @ApiModelProperty(value="户籍地地址-省）")
    private String domicileProvince;//户籍地地址-省（自治区、直辖市）
    @ApiModelProperty(value="户籍地地址-市")
    private String domicileCity;//户籍地地址-市（地区、州）
    @ApiModelProperty(value="户籍地地址-县")
    private String domicileCounty;//户籍地地址-县（区）
    @ApiModelProperty(value="户籍地地址-乡")
    private String domicileTownship;//户籍地地址-乡（镇、街道办事处）
    @ApiModelProperty(value="户籍地地址-村")
    private String domicileVillage;//户籍地地址-村（街、路、弄等）
    @ApiModelProperty(value="/户籍地地址-门牌号码")
    private String domicileHousenumber;//户籍地地址-门牌号码

}
