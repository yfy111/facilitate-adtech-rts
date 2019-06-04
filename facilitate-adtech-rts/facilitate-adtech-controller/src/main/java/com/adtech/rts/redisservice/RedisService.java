package com.adtech.rts.redisservice;

import com.adtech.rts.model.enums.RedisKeyEnum;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

@Component
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    /**
     * 获取数据返回对应类型
     *
     * @param key
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> T get(String key, Class<T> tClass) {
        return JSON.parseObject(getValueByKey(key), tClass);
    }

    /**
     * 获取数据
     *
     * @param key
     * @return
     */
    public String getValueByKey(String key) {
        if (StringUtils.isEmpty(key)) throw new NullPointerException();
        key = setKey(key);
        if (stringRedisTemplate.hasKey(key))
            return stringRedisTemplate.opsForValue().get(key);
        return null;

    }

    /**
     * 设置值，设置超时时间
     * 默认为秒
     *
     * @param key
     * @param text
     * @param timeout
     */
    public void setStringValue(String key, String text, long timeout) {
        this.setStringValue(setKey(key), text);
        stringRedisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }


    /**
     * 设置值
     *
     * @param key
     * @param text
     */
    public void setStringValue(String key, String text) {
        stringRedisTemplate.opsForValue().set(setKey(key), text);
    }


    /**
     * 判断是否有key
     *
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
        return stringRedisTemplate.hasKey(setKey(key));
    }


    public void del(String key) {
        stringRedisTemplate.delete(setKey(key));
    }

    private String setKey(String key){
        return RedisKeyEnum.REDIS_GROUP.getName()+key;
    }
}
