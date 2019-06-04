package com.adtech.rts.model.stic;

import com.google.common.collect.Maps;

import java.util.Map;

public class RegionCode {

    public static Map<String,String> map = Maps.newHashMap();
    static{
        map.put("", "全市");
        map.put("500228000000", "梁平区");
        map.put("500233000000", "忠县");
    }
}
