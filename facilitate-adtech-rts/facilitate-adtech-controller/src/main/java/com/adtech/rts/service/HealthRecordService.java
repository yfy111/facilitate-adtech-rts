package com.adtech.rts.service;

import com.adtech.rts.data.health_record.HealthRecordDataImpl;
import com.adtech.rts.data.human.HumanDataImpl;
import com.adtech.rts.model.entity.HealthRecord;
import com.adtech.rts.model.entity.Human;
import com.adtech.rts.model.enums.CodeEnum;
import com.adtech.rts.model.enums.TableEnum;
import com.adtech.rts.model.page.Page;
import com.adtech.rts.model.response.health.HealthRecordResponse;
import com.adtech.rts.model.result.Result;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 门诊处方信息
 */
@Slf4j
@Service
public class HealthRecordService {


    @Autowired
    private HumanDataImpl humanData;


    @Autowired
    private HealthRecordDataImpl healthRecordData;

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
        List<HealthRecordResponse> responseList = Lists.newArrayList();
        List<HealthRecord> healthRecordList = healthRecordData.selectByParams(params, pageNum, pageSize);
        List<Human> humans = getHumans(healthRecordList);
        setData(responseList, healthRecordList, humans);
        Page page = new Page();
        page.setList(responseList);
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);
        page.setTotalCount(healthRecordData.countByParams(params));
        result.setData(page);
        return result;
    }


    /**
     * 获得全量list
     *
     * @return
     */
    public Result<List<HealthRecord>> getList() {
        Result result = new Result(CodeEnum.SUCCESS);
        Map<String, Object> params = Maps.newHashMap();
        List<HealthRecord> list = healthRecordData.selectByParams(params);
        result.setData(list);
        return result;
    }


    /**
     * 详情查询
     *
     * @param regionCode
     * @param organizationCode
     * @param _id
     * @return
     */
    public Result<HealthRecordResponse> findInfo(String regionCode, String organizationCode, String _id,String idCard) {
        if(StringUtils.isEmpty(regionCode)&&StringUtils.isEmpty(organizationCode)&&StringUtils.isEmpty(_id)&&StringUtils.isEmpty(idCard))
            return new Result<>(CodeEnum.FAIL_PARAMCHECK);
        Result result = new Result(CodeEnum.SUCCESS);
        Map<String, Object> params = Maps.newHashMap();
        params.put(TableEnum.TABLE_FIELD_REGIONCODE.getName(), regionCode);
        params.put(TableEnum.TABLE_FIELD_IORGANIZATIONCODE.getName(), organizationCode);
        if(!StringUtils.isEmpty(_id))
            params.put(TableEnum.TABLE_FIELD_OBJECT_ID.getName(), new ObjectId(_id));
        params.put(TableEnum.TABLE_FIELD_IDCARDNO.getName(),idCard);
        List<HealthRecord> healthRecordList = healthRecordData.selectByQueryParams(params);
        List<HealthRecordResponse> recordResponses = Lists.newArrayList();
        List<Human> humans = getHumans(healthRecordList);
        setData(recordResponses, healthRecordList, humans);
        if(recordResponses.isEmpty()) result.setData(new HealthRecordResponse());
        else result.setData(recordResponses.get(0));
        return result;
    }

    /**
     * 查询human
     */
    private List<Human> getHumans(List<HealthRecord> healthRecordList) {
        List<String> identityList = Lists.newArrayList();
        for (HealthRecord info : healthRecordList) {
            if(info.getJoinTo()==null) continue;
            identityList.addAll(info.getJoinTo());
        }
        Map<String, Object> humanMap = Maps.newHashMap();
        humanMap.put(TableEnum.TABLE_OTHERIDENTITIES.getName(), identityList);
        List<Human> humans = humanData.getListByIdentitiesOr(humanMap, TableEnum.TABLE_OTHERIDENTITIES.getName());
        return humans;
    }

    private void setData(List<HealthRecordResponse> responseList, List<HealthRecord> records, List<Human> humans) {
        for (HealthRecord h : records) {
            HealthRecordResponse recordResponse = new HealthRecordResponse();
            recordResponse.set_id(h.get_id().toString());
            recordResponse.setOtherIdentities(h.getOtherIdentities());
            recordResponse.setABOBlood(h.getABOBlood());
            recordResponse.setAge(h.getAge());
            recordResponse.setActionTime(h.getActionTime());
            recordResponse.setBirthTime(h.getBirthdate());
            recordResponse.setContactName(h.getContactName());
            recordResponse.setContactTelephone(h.getContactTelephone());
            recordResponse.setDegree(h.getDegree());
            recordResponse.setDisabilitySituation(h.getDisabilitySituation());
            recordResponse.setDrugAllergyHistory(h.getDrugAllergyHistory());
            recordResponse.setExposureHistory(h.getExposureHistory());
            recordResponse.setFDC(h.getFDC());
            recordResponse.setFFTC(h.getFFTC());
            recordResponse.setFilingWay(h.getFilingWay());
            recordResponse.setFKEFI(h.getFKEFI());
            recordResponse.setFPCC(h.getFPCC());
            recordResponse.setFTCC(h.getFTCC());
            recordResponse.setGender(h.getGender());
            recordResponse.setGeneticHistoryName(h.getGeneticHistoryName());
            recordResponse.setGeneticHistorySign(h.getGeneticHistorySign());
            recordResponse.setHealthInsuranceType(h.getHealthInsuranceType());
            recordResponse.setHouseholdType(h.getHouseholdType());
            recordResponse.setHrn(h.getHRN());
            recordResponse.setIDCardNO(h.getIDCardNo());
            recordResponse.setIDCardType(h.getIDCardType());
            recordResponse.setMaritalStatus(h.getMaritalStatus());
            recordResponse.setMEP(h.getMEP());
            recordResponse.setName(h.getName());
            recordResponse.setNation(h.getNation());
            recordResponse.setNationality(h.getNationality());
            recordResponse.setOccupation(h.getProfession());
            recordResponse.setPHAS(h.getPHAS());
            recordResponse.setPhoneNO(h.getPhoneNO());
            recordResponse.setRHBlood(h.getRHBlood());
            recordResponse.setWorkUnit(h.getOrganization());
            recordResponse.setOrignalOrganizationCode(h.getOrignalOrganizationCode());
            recordResponse.setOrignalOrganizationName(h.getOrignalOrganizationName());
            for(Human human : humans){
                if(StringUtils.isEmpty(human.getIDCardNo())||StringUtils.isEmpty(h.getIDCardNo())) continue;
                if(human.getIDCardNo().equals(h.getIDCardNo())){
                    recordResponse.setCurrentAddressProvince(human.getCurrentAddressProvince());
                    recordResponse.setCurrentAddressCity(human.getCurrentAddressCity());
                    recordResponse.setCurrentAddressCounty(human.getCurrentAddressCounty());
                    recordResponse.setCurrentAddressTownship(human.getCurrentAddressTownship());
                    recordResponse.setCurrentAddressVillage(human.getCurrentAddressVillage());
                    recordResponse.setCurrentAddressHouseNO(human.getCurrentAddressHouseNO());

                    recordResponse.setDomicileCity(human.getDomicileCity());
                    recordResponse.setDomicileCounty(human.getDomicileCounty());
                    recordResponse.setDomicileHousenumber(human.getDomicileHousenumber());
                    recordResponse.setDomicileProvince(human.getDomicileProvince());
                    recordResponse.setDomicileTownship(human.getDomicileTownship());
                    recordResponse.setDomicileVillage(human.getDomicileVillage());
                }
            }
            responseList.add(recordResponse);
        }
    }


}
