package com.adtech.rts.model.enums;

public enum RedisKeyEnum {

    REDIS_GROUP("RTS_REDIS:","rts 分组key"),
    REDIS_24HOURS_HUMAN_COUNT_("24HOURS_HUMAN_COUNT_","人数24小时总计"),
    REDIS_24HOURS_HUMAN_CENT_COUNT_("24HOURS_HUMAN_CENT_COUNT_","人数24小时比例总计"),
    REDIS_24HOURS_HUMAN_COUNT_DOOR("24HOURS_HUMAN_COUNT_DOOR_","门诊住院"),
    REDIS_24HOURS_HUMAN_COUNT_HEALTH_RECORD("24HOURS_HUMAN_COUNT_HEALTH_RECORD_","建档信息"),
    REDIS_24HOURS_HUMAN_COUNT_BIRTH("24HOURS_HUMAN_COUNT_BIRTH_","出生记录"),
    ;

    private String name;
    private String mark;
    RedisKeyEnum(String name,String mark){
        this.name = name;
        this.mark = mark;
    }
    public String getName() {
        return name;
    }

    public String getMark() {
        return mark;
    }
}
