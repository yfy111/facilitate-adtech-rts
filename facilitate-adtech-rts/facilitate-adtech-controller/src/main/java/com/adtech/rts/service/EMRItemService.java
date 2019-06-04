package com.adtech.rts.service;

import com.adtech.rts.data.emritem.EMRItemDataImpl;
import com.adtech.rts.model.entity.EMRItem;
import com.adtech.rts.model.entity.Human;
import com.adtech.rts.model.enums.CodeEnum;
import com.adtech.rts.model.enums.TableEnum;
import com.adtech.rts.model.response.medicalAction.MedicalActionResponse;
import com.adtech.rts.model.response.emr.EMRItemHumanResponse;
import com.adtech.rts.model.response.emr.EMRItemListResponse;
import com.adtech.rts.model.response.emr.EMRItemResponse;
import com.adtech.rts.model.result.Result;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class EMRItemService {


    @Autowired
    private EMRItemDataImpl emrItemData;

    @Autowired
    private HumanService humanService;

    @Autowired
    private AdviceService adviceService;

    @Autowired
    private MedicalActionService medicalActionService;

    public Result<List<EMRItemResponse>> getList(String joinTo,String serialNo, String regionCode, String organizationCode) {
        Result result = new Result<>();
        if (StringUtils.isEmpty(joinTo)) {
            result.setCode(CodeEnum.FAIL_PARAMCHECK.getCode());
            result.setMsg(CodeEnum.FAIL_PARAMCHECK.getMsg());
            return result;
        }
        Map<String,Object> params = new HashMap<>();
        List<String> joinToList = Arrays.asList(joinTo.split(","));
        params.put(TableEnum.TABLE_FIELD_JOINTO.getName(),joinToList);
        params.put(TableEnum.TABLE_FIELD_REGIONCODE.getName(),regionCode);
        params.put(TableEnum.TABLE_FIELD_IORGANIZATIONCODE.getName(),organizationCode);
        result.setCode(CodeEnum.SUCCESS.getCode());
        result.setMsg(CodeEnum.SUCCESS.getMsg());
        List<EMRItemListResponse> dlist = Lists.newArrayList();
        List<EMRItem> list = emrItemData.getListByIdentitiesOr(params,TableEnum.TABLE_FIELD_JOINTO.getName());
        Map<String,Object> humanParams = new HashMap<>();
        humanParams.put(TableEnum.TABLE_OTHERIDENTITIES.getName(),joinToList);
        List<Human> humanList = humanService.getList(humanParams);
        EMRItemResponse itemResponse = new EMRItemResponse();
        Set<EMRItem> set = Sets.newHashSet(list);//去重
        List<String> hospitalizationSerialNos = Lists.newArrayList();//住院号
        for(EMRItem item : set){
            hospitalizationSerialNos.add(item.getInHospitalDiagnoseCode());
            EMRItemListResponse response= new EMRItemListResponse();
            setData(response,item);
            dlist.add(response);
        }
        itemResponse.setAdviceList(adviceService.getList(regionCode,organizationCode,hospitalizationSerialNos));
        EMRItemHumanResponse emrItemHumanResponse = new EMRItemHumanResponse();
        itemResponse.setEmrList(dlist);
        MedicalActionResponse medicalActionResponse =  medicalActionService.getMedicalActionInfo(joinTo,serialNo,regionCode,organizationCode).getData();
        setData(emrItemHumanResponse,humanList.isEmpty()?null:humanList.get(0),medicalActionResponse);
        itemResponse.setHumanResponse(emrItemHumanResponse);
        result.setData(itemResponse);
        return result;
    }

    private void setData(EMRItemHumanResponse humanResponse,Human human,MedicalActionResponse medicalActionResponse){
        if(human!=null){
            humanResponse.setBirthProvince(human.getBirthProvince());
            humanResponse.setCurrentAddress(human.getCurrentAddress());
            humanResponse.setGender(human.getGender());
            humanResponse.setIDCardNo(human.getIDCardNo());
            humanResponse.setMobile(human.getMobiles()==null||human.getMobiles().isEmpty()?null:human.getMobiles().get(0));
            humanResponse.setName(human.getName());
            humanResponse.setNation(human.getNation());
            humanResponse.setOrganization(human.getOrganization());
            humanResponse.setPoliticalLandscape(human.getPoliticalLandscape());
            humanResponse.setProfession(human.getProfession());
        }
        if(medicalActionResponse!=null){
            humanResponse.setDepartmentName(medicalActionResponse.getDepartmentName());
            humanResponse.setOrignalOrganizationName(medicalActionResponse.getOrignalOrganizationName());
            humanResponse.setActionTime(medicalActionResponse.getActionTime());
            humanResponse.setDoctorName(medicalActionResponse.getDoctorName());
        }
    }


    /**
     * 设置值
     */
    private void setData(EMRItemListResponse response, EMRItem item){
        response.setDocumentCode(item.getDocumentCode());
        response.setDocumentName(item.getDocumentName());
        response.setInHospitalDiagnoseCode(item.getInHospitalDiagnoseCode());
        response.setItemCode(item.getItemCode());
        response.setItemName(item.getItemName());
        response.setOtherIdentities(item.getOtherIdentities());
        if(!StringUtils.isEmpty(item.getValue()))
            response.setValue(item.getValue().replace("诊断依据：",""));
    }
}
