package com.adtech.rts.model.response.bpi;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 人口基本信息
 */
@Data
public class BasicPopulationInformationShowData {

    @ApiModelProperty(value = "主键", required = true)
    private String ID;

    @ApiModelProperty(value = "个人编号", required = true)
    private String PERSON_ID;

    @ApiModelProperty(value = "全局索引ID", required = true)
    private String PIX_ID;

    @ApiModelProperty(value = "管理地区行政区划", required = true)
    private String AREA_CODE;

    @ApiModelProperty(value = "姓名", required = true)
    private String PERSON_NAME;

    @ApiModelProperty(value = "证件类型", required = true)
    private String IDCARD_TYPE;

    @ApiModelProperty(value = "身份证号", required = true)
    private String IDCARD;

    @ApiModelProperty(value = "性别", required = true)
    private String GENDER_CODE;

    @ApiModelProperty(value = "民族", required = true)
    private String ETHNICITY_CODE;

    @ApiModelProperty(value = "出生日期", required = true)
    private String BIRTH_DATE;

    @ApiModelProperty(value = "是否应该建档(0:否,1:是)", required = true)
    private String SHOULD_CREATE_FILE;

    @ApiModelProperty(value = "是否已建健康档案(0:未建档,1:已建档)", required = true)
    private String IS_EHR_CREATED;

    @ApiModelProperty(value = "是否已建电子病历(0:未建档,1:已建档)", required = true)
    private String IS_EMR_CREATED;

    @ApiModelProperty(value = "健康档案创建时间", required = true)
    private String EHR_CREATE_TIME;

    @ApiModelProperty(value = "电子病历创建时间", required = true)
    private String EMR_CREATE_TIME;

    @ApiModelProperty(value = "记录创建时间", required = true)
    private String CREATE_TIME;

    @ApiModelProperty(value = "记录最后更新时间", required = true)
    private String UPDATE_TIME;

    @ApiModelProperty(value = "婚姻状况", required = true)
    private String MARITAL_STATUS_CODE;

    @ApiModelProperty(value = "婚变日期", required = true)
    private String DIVORCE_DATE;

    @ApiModelProperty(value = "政治面貌", required = true)
    private String POLITICAL_STATUS_CODE;

    @ApiModelProperty(value = "文化程度", required = true)
    private String EDU_BG_CODE;

    @ApiModelProperty(value = "职业代码", required = true)
    private String OCCUPATION_CODE;

    @ApiModelProperty(value = "健康状况", required = true)
    private String HEALTH_STATUS_CODE;

    @ApiModelProperty(value = "户口性质", required = true)
    private String REGISTERED_RESIDENCE_TYPE;

    @ApiModelProperty(value = "联系电话", required = true)
    private String CONTACT_PHONE;

    @ApiModelProperty(value = "死亡日期", required = true)
    private String DATE_OF_DEATH;

    @ApiModelProperty(value = "死亡原因", required = true)
    private String CAUSE_OF_DEATH;

    @ApiModelProperty(value = "户籍地址", required = true)
    private String REGISTERED_RESIDENCE;

    @ApiModelProperty(value = "现居地", required = true)
    private String CURRENT_RESIDENCE;

    @ApiModelProperty(value = "户籍地代码", required = true)
    private String HOUSEHOLD_REGISTER_PLACE_CODE;

    @ApiModelProperty(value = "现居地代码", required = true)
    private String NOW_LIVE_PLACE_CODE;

    @ApiModelProperty(value = "基本信息变更：1，扩展信息变更：2", required = true)
    private String CHANGE_LB;

    @ApiModelProperty(value = "家庭成员关系", required = true)
    private FamilyMemberShwData familyMemberShwData;
}
