package com.adtech.rts.service;

import com.adtech.rts.data.birth.BornInfoDataImpl;
import com.adtech.rts.data.health_record.HealthRecordDataImpl;
import com.adtech.rts.data.medical.MedicalActionDataImpl;
import com.adtech.rts.model.enums.*;
import com.adtech.rts.model.response.medicalDataBase.AllDayDataHoursResponse;
import com.adtech.rts.model.response.medicalDataBase.MedicalActionBase;
import com.adtech.rts.model.response.medicalDataBase.OutpatientDepartmentPopulationResponse;
import com.adtech.rts.model.response.medicalDataBase.OutpatientDepartmentResponse;
import com.adtech.rts.model.result.Result;
import com.adtech.rts.model.util.DateFormatUtil;
import com.adtech.rts.redisservice.RedisService;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 单独运行一个线程来跑
 */
@Service
public class AllDayDataHoursService {

    @Autowired
    private MedicalActionDataImpl medicalActionData;

    @Autowired
    private BornInfoDataImpl bornInfoData;

    @Autowired
    private RedisService redisService;

    @Autowired
    private OutpatientDepartmentService outpatientDepartmentService;

    @Autowired
    private HealthRecordDataImpl healthRecordData;


    /**
     * 人数比列查询
     *
     * @param regionCode
     * @param organizationCode
     * @return
     */
    public Result<AllDayDataHoursResponse> getAllDayData1(String regionCode, String organizationCode) {
        AllDayDataHoursResponse all = null;
        Result result = new Result(CodeEnum.SUCCESS);
        //获得当前统计好的数据
        String key = RedisKeyEnum.REDIS_24HOURS_HUMAN_COUNT_.getName();
        if (!StringUtils.isEmpty(regionCode)) key += regionCode;
        OutpatientDepartmentResponse r = null;
        if (redisService.hasKey(key))
            r = outpatientDepartmentService.getOutpatientDepartmentPopulation(0, regionCode, organizationCode).getData();
        else r = new OutpatientDepartmentResponse();
        Date date = new Date();
        //获得上次数据
        key = RedisKeyEnum.REDIS_24HOURS_HUMAN_CENT_COUNT_.getName();
        if (!StringUtils.isEmpty(regionCode)) key += regionCode;
        if (redisService.hasKey(key)) {
            all = redisService.get(key, AllDayDataHoursResponse.class);
            if (all == null) {
                all = new AllDayDataHoursResponse();
            }
            //如果超过第二天
            if (all.getTomorrow() != null && date.getTime() > all.getTomorrow().getTime()) {
                redisService.del(key);
                all = new AllDayDataHoursResponse();
                all.setTomorrow(DateFormatUtil.getDayByHour(date, 24));//第二天设置,用于数据清空和比较
            }
        } else {
            all = new AllDayDataHoursResponse();
        }

        Map<String, Object> objectMap = Maps.newHashMap();
        objectMap.put(TableEnum.TABLE_FIELD_REGIONCODE.getName(),regionCode);
        objectMap.put(TableEnum.TABLE_FIELD_BIRTH_BIRTHREGION.getName(),regionCode.length()>6?regionCode.substring(0,6):regionCode);
        //门诊人次累加
        objectMap.put(TableEnum.TABLE_FIELD_ACTION_TYPE.getName(), EMedicalActionType.outpatient.name());
        setMedicalActionDoorData(all.getMedicalActionDoor(), r.getMedicalActionDoor());
        //涨幅计算
        setMedicalActionDoor(all.getMedicalActionDoor(), date, objectMap, EMedicalActionType.outpatient.name());

        //住院人次累加
        objectMap.put(TableEnum.TABLE_FIELD_ACTION_TYPE.getName(), EMedicalActionType.hospitalization.name());
        setMedicalActionDoorData(all.getHospitalization(), r.getHospitalization());
        //涨幅计算
        setMedicalActionDoor(all.getHospitalization(), date, objectMap, EMedicalActionType.hospitalization.name());

        //出生人次累加
        all.setBirth(new MedicalActionBase());
        setMedicalActionDoorData(all.getBirth(), r.getBirth());
        //涨幅计算
        setMedicalActionDoor(all.getBirth(), date, objectMap, EMedicalActionType.birth.name());

        //建档信息
        all.setFileEstablishment(new MedicalActionBase());
        setMedicalActionDoorData(all.getFileEstablishment(), r.getFileEstablishment());
        //涨幅计算
        setMedicalActionDoor(all.getFileEstablishment(), date, objectMap, EMedicalActionType.file.name());

        all.setLastDate(date);//最后时间记录
        all.setTomorrow(StringUtils.isEmpty(all.getTomorrow()) ? DateFormatUtil.getDayByHour(date, 24) : all.getTomorrow());
        redisService.setStringValue(key, JSON.toJSONString(all));
        result.setData(all);
        return result;
    }


    /**
     * 设置人次
     */
    private void setMedicalActionDoorData(MedicalActionBase medicalActionDoor, List<OutpatientDepartmentPopulationResponse> doorPopulation) {
        Integer c = 0;
        if (doorPopulation != null) {
            for (OutpatientDepartmentPopulationResponse r : doorPopulation) {
                if (StringUtils.isEmpty(r.getValue())) continue;
                c += Integer.valueOf(r.getValue());
            }
        }
        medicalActionDoor.setNowDay(c.toString());
    }

    /**
     * 人次日统计
     *
     * @param base
     * @param date
     * @param params
     */
    private void setMedicalActionDoor(MedicalActionBase base, Date date, Map<String, Object> params, String type) {
        //昨天的数据
        setYesterdayParams(params, date);
        if (StringUtils.isEmpty(base.getYesterDay()) || "0.0".equals(base.getYesterDay())|| "0".equals(base.getYesterDay())) {
            //昨日值设置
            Long yesterday = 0L;
            if (EMedicalActionType.hospitalization.name().equals(type) || EMedicalActionType.outpatient.name().equals(type)) {
                yesterday = medicalActionData.countByTime(params);
            } else if (EMedicalActionType.birth.name().equals(type)) {
                yesterday = bornInfoData.countByTime(params);
            } else if (EMedicalActionType.vaccine.name().equals(type)) {

            } else if (EMedicalActionType.file.name().equals(type)) {
                yesterday = healthRecordData.countByTime(params);
            }

            base.setYesterDay(yesterday.toString());
        }
        setBaseDat(base);
    }




    /**
     * 设置值
     *
     * @param base
     */
    private void setBaseDat(MedicalActionBase base) {
        Double t = Double.valueOf(base.getNowDay() == null ? "0.0" : base.getNowDay());
        Double y = Double.valueOf(base.getYesterDay() == null ? "0.0" : base.getYesterDay());
        Double d = 0.0;
        if (y != 0.0) {
            d = (t - y) / y;
            base.setYear_on_year(d.toString());
        } else {
            base.setYear_on_year("max");
        }

        setState(base, d);
    }


    /**
     * 设置状态
     *
     * @param base
     * @param d
     */
    private void setState(MedicalActionBase base, Double d) {
        if (d > 0.0)
            base.setState(StateEnum.RISE.getCode());
        else if (d == 0.0 && !base.getYear_on_year().equals("-"))
            base.setState(StateEnum.FLAT.getCode());
        else if (d == 0.0 && base.getYear_on_year().equals("-"))
            base.setState(StateEnum.FLAT.getCode());
        else base.setState(StateEnum.DROP.getCode());
    }

    /**
     * 昨天
     *
     * @param params
     * @param date
     */
    private void setYesterdayParams(Map<String, Object> params, Date date) {
        params.put("beginTime", DateFormatUtil.getDayByHour(date, -24));//昨天
        params.put("endTime", DateFormatUtil.getDayByHour(date, 0));//今天
    }

}