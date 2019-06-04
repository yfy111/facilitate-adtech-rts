package com.adtech.rts.service;

import cn.hutool.crypto.SecureUtil;
import com.adtech.rts.httpclient.RtsInterface;
import com.adtech.rts.model.enums.CodeEnum;
import com.adtech.rts.model.result.Result;
import com.adtech.rts.properties.FacilitateProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * 用户登录
 */
@Service
public class LoginService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RtsInterface rtsInterface;

    @Autowired
    private FacilitateProperties facilitateProperties;
    /**
     * 登录实现
     * @param userName
     * @param password
     * @return
     */
    public Result login(String userName, String password, HttpServletResponse response){
        if(StringUtils.isEmpty(userName)||StringUtils.isEmpty(password)) return new Result(CodeEnum.SIGN_ERROR);
        else if(!userName.equals(facilitateProperties.getUserName())||!password.equals(facilitateProperties.getPassWord()))return new Result((CodeEnum.SIGN_ERROR));
        //获得token
        String secret = SecureUtil.md5(userName);
        String token = rtsInterface.getToken(rtsInterface.getAppKye(),secret);
        redisTemplate.opsForValue().set(secret,token,60*30, TimeUnit.SECONDS);
        response.setHeader("rts_token",token);
        response.setHeader("rts_secret",secret);
        Result result = new Result((CodeEnum.SIGN_SUCCESS));
        return result;
    }
}
