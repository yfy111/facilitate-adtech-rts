package com.adtech.rts.service.runUtil;

import com.adtech.rts.data.birth.BornInfoDataImpl;
import com.adtech.rts.data.health_record.HealthRecordDataImpl;
import com.adtech.rts.data.medical.MedicalActionDataImpl;
import com.adtech.rts.model.entity.BornInfo;
import com.adtech.rts.model.entity.HealthRecord;
import com.adtech.rts.model.entity.MedicalAction;
import com.adtech.rts.model.entity.util.ActionTimeUtil;
import com.adtech.rts.model.enums.EMedicalActionType;
import com.adtech.rts.model.enums.RedisKeyEnum;
import com.adtech.rts.model.enums.TableEnum;
import com.adtech.rts.model.response.medicalDataBase.OutpatientDepartmentPopulationResponse;
import com.adtech.rts.model.response.medicalDataBase.OutpatientDepartmentResponse;
import com.adtech.rts.model.stic.RegionCode;
import com.adtech.rts.model.util.DateFormatUtil;
import com.adtech.rts.model.util.EntityConvertUtil;
import com.adtech.rts.redisservice.RedisService;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
@Slf4j
public class RunServiceUtil {

    @Autowired
    private RedisService redisService;

    @Autowired
    private MedicalActionDataImpl medicalActionData;

    @Autowired
    private BornInfoDataImpl bornInfoData;

    @Autowired
    private HealthRecordDataImpl healthRecordData;


    private static final String MMSS = ":00:00";//分秒后缀


    /**
     * 线程开启
     */
    @Autowired
    public void run1() {
        // 基于接口的实现
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Map<String,String> map = RegionCode.map;
                        for(String s : map.keySet()){
                            run2(s);
                        }
                        Thread.sleep(5000); // 休息5000ms
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    /**
     * 清除线程开启
     */
    @Autowired
    public void run3() {
        // 基于接口的实现
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Map<String,String> map = RegionCode.map;
                        for(String s : map.keySet()){
                            deleteCatch(s);
                        }
                        log.info("2分钟定时缓存清空!!!!!!");
                        Thread.sleep(1000*60*2); // 休息2分钟
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    /**
     * 缓存清除器
     */
    private void  deleteCatch(String regionCode){
        if(StringUtils.isEmpty(regionCode)){
            redisService.del(RedisKeyEnum.REDIS_24HOURS_HUMAN_COUNT_.getName());
            redisService.del(RedisKeyEnum.REDIS_24HOURS_HUMAN_CENT_COUNT_.getName());
        }else {
            redisService.del(RedisKeyEnum.REDIS_24HOURS_HUMAN_COUNT_.getName()+regionCode);
            redisService.del(RedisKeyEnum.REDIS_24HOURS_HUMAN_CENT_COUNT_.getName()+regionCode);
        }
    }



    /**
     * 定时获取数据，与用户请求解耦
     */
    public void run2(String regionCode) {
        OutpatientDepartmentResponse response = new OutpatientDepartmentResponse();
        Date date = new Date();
        Date beginTime = DateFormatUtil.getDayByHour(date, 0);//当天
        Date endTime = DateFormatUtil.getDayByHour(date, 24);//明天
        Map<String, Object> map = Maps.newHashMap();
        resetTime(map, beginTime, endTime);
        String key = RedisKeyEnum.REDIS_24HOURS_HUMAN_COUNT_.getName();
        if(!StringUtils.isEmpty(regionCode)) {
            key+=regionCode;
            map.put(TableEnum.TABLE_FIELD_REGIONCODE.getName(),regionCode);
            map.put(TableEnum.TABLE_FIELD_BIRTH_BIRTHREGION.getName(),regionCode.length()>6?regionCode.substring(0,6):regionCode);
        }
        if (redisService.hasKey(key)) {
            response = redisService.get(key, OutpatientDepartmentResponse.class);//获取缓存
            if (response != null) {
                if (response.getTomorrow() != null && date.getTime() > response.getTomorrow().getTime()) {
                    redisService.del(key);
                    response = new OutpatientDepartmentResponse();
                    resetTime(map, beginTime, endTime);
                    response.setTomorrow(endTime);
                } else {
                    resetTime(response, map, beginTime, endTime);
                }
            }
        } else {
            response.setTomorrow(endTime);
        }
        //增量查询就诊活动
        List<MedicalAction> list = medicalActionData.findByTimeParams(map);
        //当hasKey判断刚过key就被删除后，response会在redis里获得一个空值，所以需要重新赋值
        if(response==null) response =new OutpatientDepartmentResponse();
        //门急诊人次、住院
        increment(response, list);

        //出生
        List<BornInfo> bornInfoList = bornInfoData.findByTimeParams(map);
        Map<String, String> birthFields = Maps.newHashMap();
        birthFields.put(TableEnum.TABLE_SORT_ACTIONTIME_FORMAT.getName(), TableEnum.TABLE_SORT_BIRTHDATEFORMAT_FORMAT.getName());
        birthFields.put(TableEnum.TABLE_SORT_ACTIONTIME.getName(), TableEnum.TABLE_SORT_BIRTHDATE.getName());
        increment(EntityConvertUtil.convertForEntity(bornInfoList, ActionTimeUtil.class, birthFields), response, EMedicalActionType.birth.name());
        //建档
        List<HealthRecord> healthRecordList = healthRecordData.findByTimeParams(map);
        Map<String, String> healthFields = Maps.newHashMap();
        healthFields.put(TableEnum.TABLE_SORT_ACTIONTIME_FORMAT.getName(), TableEnum.TABLE_SORT_ACTIONTIME_FORMAT.getName());
        healthFields.put(TableEnum.TABLE_SORT_ACTIONTIME.getName(), TableEnum.TABLE_SORT_ACTIONTIME.getName());
        increment(EntityConvertUtil.convertForEntity(healthRecordList, ActionTimeUtil.class, healthFields), response, EMedicalActionType.file.name());
        //疫苗
        redisService.setStringValue(key, JSON.toJSONString(response));

    }


    /**
     * 时间重置
     *
     * @param response
     * @param map
     * @param beginTime
     * @param endTime
     */
    private void resetTime(OutpatientDepartmentResponse response, Map<String, Object> map, Date beginTime, Date endTime) {
        map.put("doorBeginTime", response.getDoorLastTime() == null ? beginTime : response.getDoorLastTime());
//        map.put("birthBeginTime", response.getBirthLastTime() == null ? beginTime : response.getBirthLastTime());
    }

    /**
     * 时间重置
     *
     * @param map
     * @param beginTime
     * @param endTime
     */
    private void resetTime(Map<String, Object> map, Date beginTime, Date endTime) {
        map.put("doorBeginTime", beginTime);
        map.put("birthBeginTime", beginTime);
        map.put("endTime", endTime);
    }

    /**
     * 取值赋值增量
     *
     * @param response
     * @param list
     */
    private void increment(OutpatientDepartmentResponse response, List<MedicalAction> list) {
        Map<String, List<MedicalAction>> mp = Maps.newHashMap();
        //根据类型分组
        for (MedicalAction m : list) {
            if(StringUtils.isEmpty(m.getActionTime())||StringUtils.isEmpty(m.getActionType())) continue;
            if (mp.containsKey(m.getActionType())) {
                mp.get(m.getActionType()).add(m);
            } else {
                List<MedicalAction> l = Lists.newArrayList();
                l.add(m);
                mp.put(m.getActionType(), l);
            }
        }

        Set<String> set = mp.keySet();
        for (String s : set) {
            if (EMedicalActionType.hospitalization.name().equals(s)) {
                increment(response.getHospitalization(), EntityConvertUtil.convertForEntity(mp.get(s), ActionTimeUtil.class));
            } else if (EMedicalActionType.outpatient.name().equals(s) || EMedicalActionType.emergency.name().equals(s)) {
                increment(response.getMedicalActionDoor(), EntityConvertUtil.convertForEntity(mp.get(s), ActionTimeUtil.class));
            }
        }
        if (!list.isEmpty()) {
            //倒序
            list.sort((d1, d2) -> d2.getActionTimeFormat().compareTo(d1.getActionTimeFormat()));
            //获得最后一个时间
            response.setDoorLastTime(list.get(0).getActionTimeFormat());
        }
    }


    /**
     * 取值赋值增量
     *
     * @param list
     */
    private void increment(List<ActionTimeUtil> list, OutpatientDepartmentResponse response, String type) {
        if (!list.isEmpty()) {
            //倒序
            list.sort((d1, d2) -> d2.getActionTimeFormat().compareTo(d1.getActionTimeFormat()));
            if (EMedicalActionType.birth.name().equals(type)) {
                response.setBirth(Lists.newArrayList());
                incrementNo(response.getBirth(), list);
                response.setBirthLastTime(list.get(0).getActionTimeFormat());
            }
            if (EMedicalActionType.followUp.name().equals(type)) {
                increment(response.getFollowUp(), list);
                response.setFollowUpLastTime(list.get(0).getActionTimeFormat());
            }
            if (EMedicalActionType.vaccine.name().equals(type)) {
                increment(response.getVaccine(), list);
                response.setVaccineLastTime(list.get(0).getActionTimeFormat());
            }
            if (EMedicalActionType.file.name().equals(type)) {
                response.setFileEstablishment(Lists.newArrayList());
                increment(response.getFileEstablishment(), list);
                response.setFileLastTime(list.get(0).getActionTimeFormat());
            }
        }
    }


    /**
     * 非增量-针对出生医学证明
     *
     * @param oList
     * @param list
     */
    private void incrementNo(List<OutpatientDepartmentPopulationResponse> oList, List<ActionTimeUtil> list) {
        //根据actionTime分组
        Map<String, Integer> c = Maps.newHashMap();
        setActionTimeUtil(c, list);
        setOutpatientDepartmentPopulationResponseList(oList, c.keySet(), c);

    }

    /**
     * 取值赋值增量
     *
     * @param oList
     * @param list
     */
    private void increment(List<OutpatientDepartmentPopulationResponse> oList, List<ActionTimeUtil> list) {
        //根据actionTime分组
        Map<String, Integer> c = Maps.newHashMap();
        setActionTimeUtil(c, list);
        //先将并集数据累加 ，并生成一个新map
        Map<String, Integer> map = Maps.newHashMap();
        Set<String> keys = c.keySet();
        for (String s : keys) {
            if(StringUtils.isEmpty(s)) continue;
            int i = 0;//计数器
            for (OutpatientDepartmentPopulationResponse r : oList) {
                if (r.getName().equals(s)) {
                    //存在
                    r.setValue((c.get(s) + Integer.valueOf(r.getValue())) + "");//累加
                    i++;
                }
            }
            if (i == 0) {
                map.put(s, c.get(s));
            }
        }
        setOutpatientDepartmentPopulationResponseList(oList, map.keySet(), c);
        //排序
        if(oList!=null&&!oList.isEmpty())
            oList.sort((d1, d2) -> d1.getName().compareTo(d2.getName()));
    }


    /**
     * 时间分割
     *
     * @param c
     * @param list
     */
    private void setActionTimeUtil(Map<String, Integer> c, List<ActionTimeUtil> list) {
        String t = null;
        for (ActionTimeUtil item : list) {
            if (!StringUtils.isEmpty(item.getActionTime()) && item.getActionTime().length() >= 13)
                t = item.getActionTime().substring(11, 13) + MMSS;
            if (c.containsKey(t)) c.put(t, c.get(t) + 1);
            else c.put(t, 1);
        }
    }

    /**
     * 赋值操作
     * @param oList
     * @param keys
     * @param c
     */
    private void setOutpatientDepartmentPopulationResponseList(List<OutpatientDepartmentPopulationResponse> oList
            , Set<String> keys, Map<String, Integer> c) {
        //获取map交集数据放入list
        for (String s : keys) {
            OutpatientDepartmentPopulationResponse r1 = new OutpatientDepartmentPopulationResponse();
            r1.setName(s);
            r1.setValue(c.get(s).toString());
            oList.add(r1);
        }
    }
}
