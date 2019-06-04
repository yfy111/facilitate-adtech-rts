package com.adtech.rts.model.entity.base;

import lombok.Data;

import java.util.Date;

@Data
public abstract class DataInfo {
    protected String queueDataId;//系统
    protected String orignalDataBase;//系统


    protected String standardOrganizationCode;
    protected String standardOrganizationName;
    protected String orignalOrganizationCode;
    protected String orignalOrganizationName;
    protected String orignalId;

    protected String app;//系统
    protected String company;//系统

    protected boolean isDeleted;//是否删除
    protected Date createTime;
    protected Date lastUpdateTime;

    protected String regionCode;
    protected String regionName;
    protected String city;
    protected String cityCode;
}
