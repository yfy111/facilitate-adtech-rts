package com.adtech.rts.service;

import com.adtech.rts.data.human.HumanDataImpl;
import com.adtech.rts.data.medical.MedicalActionDataImpl;
import com.adtech.rts.httpclient.RtsInterface;
import com.adtech.rts.model.entity.Diagnose;
import com.adtech.rts.model.entity.Human;
import com.adtech.rts.model.entity.MedicalAction;
import com.adtech.rts.model.enums.CodeEnum;
import com.adtech.rts.model.enums.EMedicalActionType;
import com.adtech.rts.model.enums.TableEnum;
import com.adtech.rts.model.response.medicalAction.MedicalActionResponse;
import com.adtech.rts.model.response.medicalAction.PingAnMedicalActionResponse;
import com.adtech.rts.model.result.Result;
import com.adtech.rts.pingan.PingAnTest;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 门诊处方信息
 */
@Slf4j
@Service
public class MedicalActionService {

    @Autowired
    private MedicalActionDataImpl medicalActionData;

    @Autowired
    private HumanDataImpl humanData;

    @Autowired
    private HumanService humanService;

    @Autowired
    private RtsInterface rtsInterface;

    @Autowired
    private DiagnoseService diagnoseService;

    /**
     * 获取个人门诊信息
     *
     * @return
     */
    public Result<MedicalActionResponse> getMedicalActionInfo( String otherIdentities,String serialNo, String regionCode, String organizationCode) {
        if(StringUtils.isEmpty(otherIdentities)) return new Result<>(CodeEnum.FAIL_PARAMCHECK);
        Result result = new Result(CodeEnum.SUCCESS);
        Map<String, Object> map = new HashMap<>();
        List<String> otherIdentitiesList = Arrays.asList(otherIdentities.split(","));
        map.put(TableEnum.TABLE_OTHERIDENTITIES.getName(), otherIdentitiesList);
        MedicalAction item = medicalActionData.findInfo(map);
        MedicalActionResponse response = new MedicalActionResponse();
        response.setDiagnoseResponses(diagnoseService.getList(map));
        Map<String,Object> map1 = new HashMap<>();
        List<Human> humans=null;
        if(item==null||item.getJoinTo()==null){
            item = new MedicalAction();
            item.setJoinTo(Lists.newArrayList());
        }else{
            map1.put(TableEnum.TABLE_OTHERIDENTITIES.getName(),item.getJoinTo());
            humans = humanData.getListByIdentitiesOr(map1,TableEnum.TABLE_OTHERIDENTITIES.getName());
        }
        setData(item, response,humans==null||humans.isEmpty()?null:humans.get(0));
        result.setData(response);
        return result;
    }


    /**
     * 获取个人所有门诊记录信息
     * @param idCard
     * @return
     */
    public Result getMedicalActionList(String idCard) {
        Result result = new Result();
        if (StringUtils.isEmpty(idCard)) {
            result.setCode(CodeEnum.FAIL_PARAMCHECK.getCode());
            result.setMsg(CodeEnum.FAIL_PARAMCHECK.getMsg());
            return result;
        }
        Human human =  humanService.getInfo(idCard).getData();
        if (StringUtils.isEmpty(human.getIDCardNo())) {
            result.setCode(CodeEnum.FAIL_PARAMCHECK.getCode());
            result.setMsg(CodeEnum.FAIL_PARAMCHECK.getMsg());
            return result;
        }
        Map<String, Object> map = new HashMap<>();
        map.put(TableEnum.TABLE_FIELD_JOINTO.getName(), human.getOtherIdentities());
        List<MedicalAction> list = medicalActionData.findByOrParams(map,TableEnum.TABLE_FIELD_JOINTO.getName());
        List<MedicalActionResponse> responseList = new ArrayList<>();
        result.setCode(CodeEnum.SUCCESS.getCode());
        result.setMsg(CodeEnum.SUCCESS.getMsg());
        setData(list, responseList,human);
        setData(responseList, PingAnTest.getPingAnData(idCard),idCard);
        result.setData(responseList);
        return result;
    }

    /**
     * 根据父级otherIdentities 查询子集joinTo
     *
     * @return
     */
    public Result<Long> getMedicalActionCountByOr(String secret, String token) {
        Result result1 = rtsInterface.resolveUsertoken(secret, token);
        if(result1.getCode()!=100){
            return new Result(CodeEnum.FAIL_UNAUTHORIZED);
        }
        log.info("解密数据：{}", result1);
        Human human = humanService.getInfo(((Map) result1.getData()).get("idCard").toString()).getData();
        Map<String, Object> params = new HashMap<>();
        params.put(TableEnum.TABLE_FIELD_JOINTO.getName(), human.getOtherIdentities());
        Result result = new Result(CodeEnum.SUCCESS);
        result.setData(medicalActionData.countByOr(params));
        return result;
    }

    /**
     * 数据集合并
     * @param list
     * @param responseList
     * @param human
     */
    private void setData(List<MedicalAction> list, List<MedicalActionResponse> responseList,Human human) {
        if(human!=null){
            for(MedicalAction item : list){
                if(StringUtils.isEmpty(item.getDataBase()))
//                if(EMedicalActionType.hospitalization.name().equals(item.getActionType())||StringUtils.isEmpty(item.getDataBase()))
                    continue;
                MedicalActionResponse response = new MedicalActionResponse();
                setData(item, response,human);
                responseList.add(response);
            }
        }else{
            List<String> identitiesList = Lists.newArrayList();
            //获取所有identities
            list.forEach(item -> identitiesList.addAll(item.getJoinTo()));
            Map<String,Object> map = new HashMap<>();
            map.put(TableEnum.TABLE_OTHERIDENTITIES.getName(),identitiesList);
            List<Human> humans = humanData.getListByIdentitiesOr(map,TableEnum.TABLE_OTHERIDENTITIES.getName());
            for(MedicalAction item : list){
                if(EMedicalActionType.hospitalization.name().equals(item.getActionType())&&StringUtils.isEmpty(item.getDataBase()))
                    continue;
                MedicalActionResponse response = new MedicalActionResponse();
                for(Human h : humans){
                    if(h.getOtherIdentities().containsAll(item.getJoinTo())){
                        setData(item, response,h);
                    }
                }
                responseList.add(response);
            }
        }

    }

    /**
     * 数据集合并
     * @param item
     * @param response
     * @param h
     */
    private void setData(MedicalAction item, MedicalActionResponse response,Human h) {
        response.setActionTime(item.getActionTime());
        response.setSerialNo(item.getSerialNo());
        response.setOrignalOrganizationCode(item.getOrignalOrganizationCode());
        response.setOrignalOrganizationName(item.getOrignalOrganizationName());
        response.setDoctorName(item.getDoctorName());
        response.setRegionCode(item.getRegionCode());
        response.setRegionName(item.getRegionName());
        response.setDepartmentName(item.getDepartmentName());
        response.setOtherIdentities(item.getOtherIdentities());
        response.setActionType(item.getActionType());
        Map<String, Object> map1 = new IdentityHashMap<>();
        item.getJoinTo().forEach(s -> map1.put(TableEnum.TABLE_OTHERIDENTITIES.getName(), s));
        if(h!=null){
            Human human  = h;
            response.setName(human.getName());
            response.setGender(human.getGender());
            response.setIDCardNo(human.getIDCardNo());
            response.setCurrentAddress(StringUtils.isEmpty(human.getCurrentAddress()) ? human.getOrganization() : human.getCurrentAddress());
            if (!human.getMobiles().isEmpty()) response.setMobile(human.getMobiles().get(0));
        }
    }

    /**
     * 平安数据集合并
     */
    private void setData(List<MedicalActionResponse> responseList,List<PingAnMedicalActionResponse> pingAnData,String idCard){
        for(PingAnMedicalActionResponse p : pingAnData){
            MedicalActionResponse m  = new MedicalActionResponse();
            m.setDiagnose(p.getDiagnose());
            m.setActionTime(p.getVisitTime());
            m.setActionType(p.getMedicalCategory());
            m.setDepartmentName(p.getDepartmentName());
            m.setOrignalOrganizationName(p.getHospital());
            m.setIDCardNo(idCard);
            m.setGender(p.getGender());
            m.setName(p.getName());
            m.setAge(p.getAge());
            m.setSerialNo(p.getSerialNo());
            m.setClinicalComplications(p.getDiseaseName());
            responseList.add(m);
        }
    }

}
