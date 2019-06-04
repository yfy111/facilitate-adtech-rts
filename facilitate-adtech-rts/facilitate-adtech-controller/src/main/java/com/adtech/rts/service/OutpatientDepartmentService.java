package com.adtech.rts.service;

import com.adtech.rts.model.enums.CodeEnum;
import com.adtech.rts.model.enums.RedisKeyEnum;
import com.adtech.rts.model.response.medicalDataBase.OutpatientDepartmentResponse;
import com.adtech.rts.model.result.Result;
import com.adtech.rts.redisservice.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class OutpatientDepartmentService {

    @Autowired
    private RedisService redisService;

    /**
     * 当天小时门诊人数统计
     *
     * @return
     */
    public Result<OutpatientDepartmentResponse> getOutpatientDepartmentPopulation(Integer hours, String regionCode, String organizationCode) {
        Result result = new Result(CodeEnum.SUCCESS);
        OutpatientDepartmentResponse response = new OutpatientDepartmentResponse();
        String key = RedisKeyEnum.REDIS_24HOURS_HUMAN_COUNT_.getName();
        if(!StringUtils.isEmpty(regionCode)) key+=regionCode;
        if (redisService.hasKey(key)) {
            response = redisService.get(key, OutpatientDepartmentResponse.class);//获取缓存
        }
        result.setData(response);
        return result;
    }


}
