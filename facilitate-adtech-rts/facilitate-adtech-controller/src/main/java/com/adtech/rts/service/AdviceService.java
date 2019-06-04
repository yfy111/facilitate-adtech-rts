package com.adtech.rts.service;

import com.adtech.rts.data.health_record.AdviceDataImpl;
import com.adtech.rts.data.human.HumanDataImpl;
import com.adtech.rts.model.entity.Advice;
import com.adtech.rts.model.entity.Human;
import com.adtech.rts.model.enums.CodeEnum;
import com.adtech.rts.model.enums.TableEnum;
import com.adtech.rts.model.page.Page;
import com.adtech.rts.model.response.health.AdviceResponse;
import com.adtech.rts.model.result.Result;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 门诊处方信息
 */
@Slf4j
@Service
public class AdviceService {


    @Autowired
    private HumanDataImpl humanData;


    @Autowired
    private AdviceDataImpl adviceData;

    /**
     * 查询翻页数据
     *
     * @param pageNum
     * @param regionCode
     * @param organizationCode
     * @param pageSize
     * @return
     */
    public Result<Page> getInfoPage(Integer pageNum, String regionCode, String organizationCode, String idCard, Integer pageSize) {
        Result result = new Result<>(CodeEnum.SUCCESS);
        Map<String, Object> params = new HashMap<>();
        params.put(TableEnum.TABLE_FIELD_REGIONCODE.getName(), regionCode);
        params.put(TableEnum.TABLE_FIELD_IORGANIZATIONCODE.getName(), organizationCode);
        params.put(TableEnum.TABLE_FIELD_IDCARDNO.getName(),idCard);
        List<AdviceResponse> responseList = Lists.newArrayList();
        List<Advice> healthRecordList = adviceData.selectByParams(params, pageNum, pageSize);
//        List<Human> humans = getHumans(healthRecordList);
        setData(responseList, healthRecordList);
        Page page = new Page();
        page.setList(responseList);
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);
        page.setTotalCount(adviceData.countByParams(params));
        result.setData(page);
        return result;
    }



    /**
     * 根据条件获得全量list
     *
     * @return
     */
    public Result<List<Advice>> getList(String regionCode, String organizationCode, String hospitalizationSerialNo) {
        if(StringUtils.isEmpty(hospitalizationSerialNo)) return new Result(CodeEnum.FAIL_PARAMCHECK);
        Result result = new Result(CodeEnum.SUCCESS);
        Map<String, Object> params = new HashMap<>();
        params.put(TableEnum.TABLE_FIELD_HOSPITALIZATIONSERIALNO.getName(), hospitalizationSerialNo);
        params.put(TableEnum.TABLE_FIELD_REGIONCODE.getName(), regionCode);
        params.put(TableEnum.TABLE_FIELD_IORGANIZATIONCODE.getName(), organizationCode);
        List<Advice> list = adviceData.selectByParams(params);
        List<AdviceResponse> responseList = Lists.newArrayList();
        setData(responseList, list);
        result.setData(list);
        return result;
    }



    /**
     * 根据条件获得全量list
     *  对应刘老板那个坑的骚操作
     * @return
     */
    public List<AdviceResponse> getList(String regionCode, String organizationCode, List<String> hospitalizationSerialNo) {
        if(hospitalizationSerialNo.isEmpty()) return Lists.newArrayList();
        Set<String> set = Sets.newHashSet(hospitalizationSerialNo);
        hospitalizationSerialNo.clear();
        hospitalizationSerialNo.addAll(set);
        Map<String, Object> params = new HashMap<>();
        params.put(TableEnum.TABLE_FIELD_HOSPITALIZATIONSERIALNO.getName(), hospitalizationSerialNo);
        params.put(TableEnum.TABLE_FIELD_REGIONCODE.getName(), regionCode);
        params.put(TableEnum.TABLE_FIELD_IORGANIZATIONCODE.getName(), organizationCode);
        List<Advice> list = adviceData.getListByOr(params,TableEnum.TABLE_FIELD_HOSPITALIZATIONSERIALNO.getName());
        List<AdviceResponse> responseList = Lists.newArrayList();
        setData(responseList, list);
        responseList.sort((d1, d2) -> d2.getBeginTime().compareTo(d1.getBeginTime()));
        return responseList;
    }

    /**
     * 详情查询
     *
     * @param regionCode
     * @param organizationCode
     * @param _id
     * @return
     */
    public Result<AdviceResponse> findInfo(String regionCode, String organizationCode, String _id,String idCard) {
        if(StringUtils.isEmpty(regionCode)&&StringUtils.isEmpty(organizationCode)&&StringUtils.isEmpty(_id)&&StringUtils.isEmpty(idCard))
            return new Result<>(CodeEnum.FAIL_PARAMCHECK);
        Result result = new Result(CodeEnum.SUCCESS);
        Map<String, Object> params = Maps.newHashMap();
        params.put(TableEnum.TABLE_FIELD_REGIONCODE.getName(), regionCode);
        params.put(TableEnum.TABLE_FIELD_IORGANIZATIONCODE.getName(), organizationCode);
        if(!StringUtils.isEmpty(_id))
            params.put(TableEnum.TABLE_FIELD_OBJECT_ID.getName(), new ObjectId(_id));
        params.put(TableEnum.TABLE_FIELD_IDCARDNO.getName(),idCard);
        List<Advice> healthRecordList = adviceData.selectByQueryParams(params);
        List<AdviceResponse> recordResponses = Lists.newArrayList();
//        List<Human> humans = getHumans(healthRecordList);
        setData(recordResponses, healthRecordList);
        if(recordResponses.isEmpty()) result.setData(new AdviceResponse());
        else result.setData(recordResponses.get(0));
        return result;
    }

    /**
     * 查询human
     */
    private List<Human> getHumans(List<Advice> healthRecordList) {
        List<String> identityList = Lists.newArrayList();
        for (Advice info : healthRecordList) {
            if(info.getJoinTo()==null) continue;
            identityList.addAll(info.getJoinTo());
        }
        Map<String, Object> humanMap = Maps.newHashMap();
        humanMap.put(TableEnum.TABLE_OTHERIDENTITIES.getName(), identityList);
        List<Human> humans = humanData.getListByIdentitiesOr(humanMap, TableEnum.TABLE_OTHERIDENTITIES.getName());
        return humans;
    }

    private void setData(List<AdviceResponse> responseList, List<Advice> records) {
        for (Advice h : records) {
            AdviceResponse response = new AdviceResponse();
            response.setAdviceFromDoctorName(h.getAdviceFromDoctorName());
            response.setAdviceType(h.getAdviceType());
            response.setAmount(h.getAmount());
            response.setCancelTime(h.getCancelTime());
            response.setCheckDoctorName(h.getCheckDoctorName());
            response.setCode(h.getCode());
            response.setContent(h.getContent());
            response.setDepartmentName(h.getDepartmentName());
            response.setDosage(h.getDosage());
            response.setDrugUseWay(h.getDrugUseWay());
            response.setExcuteDepartmentName(h.getExcuteDepartmentName());
            response.setExcuteDoctorName(h.getExcuteDoctorName());
            response.setExcuteNurseName(h.getExcuteNurseName());
            response.setExcuteTime(h.getExcuteTime());
            response.setFrequency(h.getFrequency());
            response.setHospitalizationSerialNo(h.getHospitalizationSerialNo());
            response.setMedicationTime(h.getMedicationTime());
            response.setName(h.getName());
            response.setPrescriptionNo(h.getPrescriptionNo());
            response.setRemark(h.getRemark());
            response.setSerialNo(h.getSerialNo());
            response.setSpeed(h.getSpeed());
            response.setStopDoctorName(h.getStopDoctorName());
            response.setTimes(h.getTimes());
            response.setStopNurseName(h.getStopNurseName());
            response.setUnit(h.getUnit());
            response.setUnitPrice(h.getUnitPrice());
            response.setBeginTime(h.getBeginTime());
            response.setEndTime(h.getEndTime());
            responseList.add(response);
        }
    }


}
