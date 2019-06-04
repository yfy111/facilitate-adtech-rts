package com.adtech.rts.model.enums;

public enum TableEnum {
    MEDICALACTION_TABLE("MedicalAction", "医疗活动表"),
    HUMAN_TABLE("Human", "个人表"),
    PRESCRIPTION_TABLE("Prescription", "处方表"),
    PRESCRIPTIONDETAIL_TABLE("PrescriptionDetail", "处方表明细"),
    FEEDETAIL_TABLE("FeeDetail", "处方表明细金额表"),
    FEEOVERVIEW_TABLE("FeeOverview", "处方总金额表"),
    EMRITEM_TABLE("EMRItem", "处方总金额表"),
    BIRTHINFO_TABLE("BornInfo", "出生医学证明表"),
    ADVICE_TABLE("Advice", "医嘱信息表"),
    HEALTHRECORD_TABLE("HealthRecord", "建档信息表"),
    HOSPITALIZATIONINFO_TABLE("HospitalizationInfo", "医生记录表"),
    DIAGNOSE_TABLE("Diagnose", "诊疗表"),

    TABLE_SORT_LASTUPDATETIME("lastUpdateTime", "最后更新时间"),
    TABLE_SORT_CREATEDATE("createTime", "创建时间"),
    TABLE_SORT_ACTIONTIME("actionTime", "就诊时间"),
    TABLE_SORT_BIRTHDATE("birthdate", "出生时间"),
    TABLE_SORT_ACTIONTIME_FORMAT("actionTimeFormat", "就诊时间"),
    TABLE_SORT_BIRTHDATEFORMAT_FORMAT("birthdateFormat", "出生时间"),
    TABLE_OTHERIDENTITIES("otherIdentities", "关联ID otherIdentities list"),
    TABLE_FIELD_JOINTO("joinTo", "关联ID joinTo list"),
    TABLE_FIELD_IDCARDNO("IDCardNo", "身份证号"),
    TABLE_FIELD_NAME("name", "姓名"),
    TABLE_FIELD_IORGANIZATIONCODE("orignalOrganizationCode", "医院编码"),
    TABLE_FIELD_ORIGNALORGANIZATIONNAME("orignalOrganizationName", "医院名"),
    TABLE_FIELD_HOSPITALIZATIONSERIALNO("hospitalizationSerialNo", "住院号"),
    TABLE_FIELD_OBJECT_ID("_id", "ObjectId"),
    TABLE_FIELD_ACTION_TYPE("actionType", "就诊类型"),
    TABLE_FIELD_SERIALNO("serialNo", "就诊号"),
    TABLE_FIELD_PRESCRIPTIONNO("prescriptionNo", "处方号"),
    TABLE_FIELD_ISDELETED("isDeleted", "删除状态"),
    TABLE_FIELD_REGIONCODE("regionCode", "区域编码"),
    TABLE_FIELD_BIRTH_BIRTHREGION("birthRegion", "出生区域编码");

    private String name;
    private String msg;

    TableEnum(String name, String msg) {
        this.name = name;
        this.msg = msg;
    }

    public String getName() {
        return name;
    }

    public String getMsg() {
        return msg;
    }

}
