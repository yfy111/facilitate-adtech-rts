package com.adtech.rts.model.entity.common;

import lombok.Data;

import java.util.Date;

@Data
public class ItemData {
    private String columnName;
    private String value;
    private String dataType;
    private String expression;
    private String validType;//验证类型
    private String standardName; //标准字段
    private String orignalValue;//原始值
    private Integer dataLength;
    private Integer integerLength;//整数长度
    private Integer decimalLength;//小数长度
    private String columnComment;
    private boolean isPk;
    private String orgCode;
    private Date createTime;
    private String remark;
    private String orignalId;

}
