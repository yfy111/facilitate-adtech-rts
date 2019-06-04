package com.adtech.rts.service;

import com.adtech.rts.data.medical.DiagnoseDataImpl;
import com.adtech.rts.model.entity.Diagnose;
import com.adtech.rts.model.enums.TableEnum;
import com.adtech.rts.model.response.medicalAction.DiagnoseResponse;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 门诊处方信息
 */
@Slf4j
@Service
public class DiagnoseService {


    @Autowired
    private DiagnoseDataImpl diagnoseData;

    public List<DiagnoseResponse> getList(Map<String,Object> params){
        Map<String,Object> map = Maps.newHashMap();
        map.put(TableEnum.TABLE_FIELD_JOINTO.getName(),params.get(TableEnum.TABLE_OTHERIDENTITIES.getName()));
        List<Diagnose> list =  diagnoseData.findByOrParams(map, TableEnum.TABLE_FIELD_JOINTO.getName());
        List<DiagnoseResponse> list1 = Lists.newArrayList();
        for(Diagnose d : list){
            DiagnoseResponse response = new DiagnoseResponse();
            response.setActionTime(d.getActionTime());
            response.setDiagnose(d.getDiagnose());
            response.setDisease(d.getDisease());
            list1.add(response);
        }
        return list1;
    }

}
