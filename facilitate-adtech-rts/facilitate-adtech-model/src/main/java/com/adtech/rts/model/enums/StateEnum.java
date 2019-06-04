package com.adtech.rts.model.enums;

public enum  StateEnum {
    //rise：上升，drop：下降，flat：持平
    RISE("rise", "上升"),
    DROP("drop", "下降"),
    FLAT("flat", "持平")
    ;

    private String code;
    private String name;

    StateEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
