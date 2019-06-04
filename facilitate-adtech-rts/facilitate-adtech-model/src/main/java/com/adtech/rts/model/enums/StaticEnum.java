package com.adtech.rts.model.enums;

/**
 * 存放常量
 */
public enum  StaticEnum {

    APP_ID("5C909DA347A7C50C38619BEE","name"),
    APP_URL_APLICATION("http://192.168.9.201:33530","认证服务器"),
    REDIS_GROUP_("RTS_REDIS:","rts 分组key"),
    ;
    private String name;
    private String mark;
    StaticEnum(String name,String mark){
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
