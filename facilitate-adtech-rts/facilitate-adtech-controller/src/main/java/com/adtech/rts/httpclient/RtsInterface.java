package com.adtech.rts.httpclient;

import com.adtech.rts.model.enums.CodeEnum;
import com.adtech.rts.model.enums.StaticEnum;
import com.adtech.rts.model.result.Result;
import com.adtech.rts.properties.FacilitateProperties;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 调用rts
 */
@Service
public class RtsInterface {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private FacilitateProperties facilitateProperties;


    /**
     * 解析 userToken
     */
    public Result resolveUsertoken(String secret, String token) {
        Result result = new Result();
        String url = facilitateProperties.getApplication_app()+ "/api/user-base/resolve-user-token?appKey=" + getAppKye();
        Map<String, Object> map = new HashMap<>();
        map.put("secret", secret);
        map.put("token", token);
        Map<String, Object> mp = (Map) JSON.parse(restPost(url, String.class, map));
        if ("1".equals(mp.get("code").toString())) {
            result.setCode(CodeEnum.SUCCESS.getCode());
            result.setMsg(CodeEnum.SUCCESS.getMsg());
            result.setData(mp.get("detail"));
        } else {
            result.setCode(CodeEnum.FAIL_UNAUTHORIZED.getCode());
            result.setMsg(CodeEnum.FAIL_UNAUTHORIZED.getMsg());
        }
        return result;
    }


    /**
     * 获取APP-KEY
     *
     * @return
     */
    public String getAppKye() {
        String url1 = facilitateProperties.getApplication_app()+ "/api/join/get-app-key";
        Map<String, Object> map = new HashMap<>();
        map.put("appId", StaticEnum.APP_ID.getName());
        Map<String, Object> mp = (Map) JSON.parse(restPost(url1, String.class, map));
        if (!"1".equals(mp.get("code").toString()))
            return "0";
        return ((Map) mp.get("detail")).get("appKey").toString();
    }

    /**
     * 获取token
     *
     * @return
     */
    public String getToken(String appKye, String secret) {
        String url = facilitateProperties.getApplication_app()+ "/api/user-base/get-user-token?appKey=" + appKye;
        Map<String, Object> map = new HashMap<>();
        map.put("secret", secret);
        Map<String, Object> params = new HashMap<>();
        params.put("role_name", "people");
        map.put("content", params);
        Map<String, Object> mp = (Map) JSON.parse(restPost(url, String.class, map));
        if (!"1".equals(mp.get("code").toString()))
            return "0";
        return ((Map) mp.get("detail")).get("token").toString();
    }

    /**
     * post 请求
     *
     * @param url
     * @param clazz
     * @param params
     * @param <T>
     * @return
     */
    public <T> T restPost(String url, Class<T> clazz, Map<String, Object> params) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> formEntity = new HttpEntity<>(JSON.toJSONString(params), headers);
        return restTemplate.postForObject(url, formEntity, clazz);
    }
}
