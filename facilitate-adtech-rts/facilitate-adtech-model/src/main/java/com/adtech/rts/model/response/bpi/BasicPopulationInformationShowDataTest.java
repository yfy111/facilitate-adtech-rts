package com.adtech.rts.model.response.bpi;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 人口基本信息
 */
@Data
public class BasicPopulationInformationShowDataTest {
    @ApiModelProperty(value = "主键", required = true)
    private String ID;
    @ApiModelProperty(value = "个人编号", required = true)
    private String personid;
    @ApiModelProperty(value = "全局索引ID", required = true)
    private String pixid;
    @ApiModelProperty(value = "管理地区行政区划", required = true)
    private String areacode;
    @ApiModelProperty(value = "姓名", required = true)
    private String personname;
    @ApiModelProperty(value = "证件类型", required = true)
    private String idcardtype;
    @ApiModelProperty(value = "身份证号", required = true)
    private String idcard;
    @ApiModelProperty(value = "性别", required = true)
    private String gendercode;
    @ApiModelProperty(value = "民族", required = true)
    private String ethnicitycode;
    @ApiModelProperty(value = "出生日期", required = true)
    private String birthdate;
    @ApiModelProperty(value = "是否应该建档(0:否,1:是)", required = true)
    private String shouldcreatefile;
    @ApiModelProperty(value = "是否已建健康档案(0:未建档,1:已建档)", required = true)
    private String isehrcreated;
    @ApiModelProperty(value = "是否已建电子病历(0:未建档,1:已建档)", required = true)
    private String isemrcreated;
    @ApiModelProperty(value = "健康档案创建时间", required = true)
    private String ehrcreatetime;
    @ApiModelProperty(value = "电子病历创建时间", required = true)
    private String emrcreatetime;
    @ApiModelProperty(value = "记录创建时间", required = true)
    private String createtime;
    @ApiModelProperty(value = "记录最后更新时间", required = true)
    private String updatetime;
    @ApiModelProperty(value = "婚姻状况", required = true)
    private String maritalstatuscode;
    @ApiModelProperty(value = "婚变日期", required = true)
    private String divorcedate;
    @ApiModelProperty(value = "政治面貌", required = true)
    private String politicalstatuscode;
    @ApiModelProperty(value = "文化程度", required = true)
    private String edu_bgcode;
    @ApiModelProperty(value = "职业代码", required = true)
    private String occupationcode;
    @ApiModelProperty(value = "健康状况", required = true)
    private String healthstatuscode;
    @ApiModelProperty(value = "户口性质", required = true)
    private String registeredresidencetype;
    @ApiModelProperty(value = "联系电话", required = true)
    private String contactphone;
    @ApiModelProperty(value = "死亡日期", required = true)
    private String dateofdeath;
    @ApiModelProperty(value = "死亡原因", required = true)
    private String causeofdeath;
    @ApiModelProperty(value = "户籍地址", required = true)
    private String registeredresidence;
    @ApiModelProperty(value = "现居地", required = true)
    private String currentresidence;
    @ApiModelProperty(value = "户籍地代码", required = true)
    private String householdregisterplace_code;
    @ApiModelProperty(value = "现居地代码", required = true)
    private String nowliveplacecode;
    @ApiModelProperty(value = "基本信息变更：1，扩展信息变更：2", required = true)
    private String changelb;


}
