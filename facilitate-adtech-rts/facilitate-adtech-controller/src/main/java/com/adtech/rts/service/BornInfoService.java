package com.adtech.rts.service;

import com.adtech.rts.data.birth.BornInfoDataImpl;
import com.adtech.rts.data.human.HumanDataImpl;
import com.adtech.rts.model.entity.BornInfo;
import com.adtech.rts.model.entity.Human;
import com.adtech.rts.model.entity.MedicalAction;
import com.adtech.rts.model.enums.CodeEnum;
import com.adtech.rts.model.enums.TableEnum;
import com.adtech.rts.model.page.Page;
import com.adtech.rts.model.response.birth.BornInfoResponse;
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
public class BornInfoService {


    @Autowired
    private HumanDataImpl humanData;


    @Autowired
    private BornInfoDataImpl bornInfoData;


    private static final String father= "father";

    private static final String mother= "mother";

    /**
     * 查询翻页数据
     * @param pageNum
     * @param regionCode
     * @param organizationCode
     * @param pageSize
     * @return
     */
    public Result<Page> getInfoPage(Integer pageNum, String regionCode, String organizationCode,String idCard,Integer pageSize) {
        Result result = new Result<>(CodeEnum.SUCCESS);
        Map<String, Object> params = new HashMap<>();
        if(!StringUtils.isEmpty(regionCode)){
            if(regionCode.length()>6){
                regionCode= regionCode.substring(0,6);
            }
        }
        params.put(TableEnum.TABLE_FIELD_BIRTH_BIRTHREGION.getName(), regionCode);
        params.put(TableEnum.TABLE_FIELD_IORGANIZATIONCODE.getName(), organizationCode);
        params.put(TableEnum.TABLE_FIELD_IDCARDNO.getName(),idCard);
        List<BornInfoResponse> responseList = Lists.newArrayList();
        List<BornInfo> bornInfoList = bornInfoData.selectByParams(params, pageNum, pageSize);

        /**
         * 查询父母信息
         */
        List<String> identityList= Lists.newArrayList();
        for(BornInfo info : bornInfoList){
            identityList.addAll(info.getJoinTo());
        }
        Map<String,Object> humanMap = Maps.newHashMap();
        humanMap.put(TableEnum.TABLE_OTHERIDENTITIES.getName(),identityList);
        List<Human> humans = humanData.getListByIdentitiesOr(humanMap,TableEnum.TABLE_OTHERIDENTITIES.getName());

        setData(responseList,bornInfoList,humans);
        Page page = new Page();
        page.setList(responseList);
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);
        page.setTotalCount(bornInfoData.countByParams(params));
        result.setData(page);
        return result;
    }




    /**
     * 获得全量list
     * @return
     */
    public Result<List<BornInfo>> getList(){
        Result result = new Result(CodeEnum.SUCCESS);
        Map<String,Object> params = Maps.newHashMap();
        List<BornInfo> list = bornInfoData.selectByParams(params);
        result.setData(list);
        return result;
    }


    /**
     * 详情查询
     * @param regionCode
     * @param organizationCode
     * @param _id
     * @return
     */
    public Result<BornInfoResponse> findInfo(String regionCode, String organizationCode,String _id,String idCard){
        if(StringUtils.isEmpty(regionCode)&&StringUtils.isEmpty(organizationCode)&&StringUtils.isEmpty(_id)&&StringUtils.isEmpty(idCard))
            return new Result<>(CodeEnum.FAIL_PARAMCHECK);
        Result result = new Result(CodeEnum.SUCCESS);
        Map<String,Object> params = Maps.newHashMap();
        params.put(TableEnum.TABLE_FIELD_REGIONCODE.getName(), regionCode);
        params.put(TableEnum.TABLE_FIELD_IORGANIZATIONCODE.getName(), organizationCode);
        if(!StringUtils.isEmpty(_id))
            params.put(TableEnum.TABLE_FIELD_OBJECT_ID.getName(), new ObjectId(_id));
        params.put(TableEnum.TABLE_FIELD_IDCARDNO.getName(),idCard);
        List<BornInfo> bornInfoList = bornInfoData.selectByQueryParams(params);

        /**
         * 查询父母信息
         */
        List<String> identityList= Lists.newArrayList();
        for(BornInfo info : bornInfoList){
            identityList.addAll(info.getJoinTo());
        }
        Map<String,Object> humanMap = Maps.newHashMap();
        humanMap.put(TableEnum.TABLE_OTHERIDENTITIES.getName(),identityList);
        List<Human> humans = humanData.getListByIdentitiesOr(humanMap,TableEnum.TABLE_OTHERIDENTITIES.getName());
        List<BornInfoResponse> responseList = Lists.newArrayList();
        setData(responseList,bornInfoList,humans);
        if(responseList.isEmpty())
            result.setData(new BornInfoResponse());
        else result.setData(responseList.get(0));
        return result;
    }


    private void setData(List<BornInfoResponse> responseList, List<BornInfo> bornInfoList,List<Human> humans) {
        for(BornInfo info : bornInfoList){
            BornInfoResponse bornInfoResponse = new BornInfoResponse();
            bornInfoResponse.set_id(info.get_id().toString());
            bornInfoResponse.setBirthAddress(info.getBirthAddress());
            bornInfoResponse.setBirthCertificateNo(info.getBirthCertificateNo());
            bornInfoResponse.setBirthCity(info.getBirthCity());
            bornInfoResponse.setBirthdate(info.getBirthdate());
            bornInfoResponse.setBirthPermitNo(info.getBirthPermitNo());
            bornInfoResponse.setBirthProvince(info.getBirthProvince());
            bornInfoResponse.setBirthRegion(info.getBirthRegion());
            bornInfoResponse.setBornLength(info.getBornLength());
            bornInfoResponse.setBornWeight(info.getBornWeight());
            bornInfoResponse.setChildbirthLocationType(info.getChildbirthLocationType());
            bornInfoResponse.setChildbirthType(info.getChildbirthType());
            bornInfoResponse.setDeformity(info.getDeformity());
            bornInfoResponse.setGender(info.getGender());
            bornInfoResponse.setGestation(info.getGestation());
            bornInfoResponse.setGestationDay(info.getGestationDay());
            bornInfoResponse.setHealthyType(info.getHealthyType());
            bornInfoResponse.setMidwife(info.getMidwife());
            bornInfoResponse.setName(info.getName());
            bornInfoResponse.setGravidity(info.getGravidity());
            bornInfoResponse.setParity(info.getParity());
            bornInfoResponse.setOrignalOrganizationName(info.getOrignalOrganizationName());
            //判断包含关系
            for(Human human : humans){
//                if(human.getOtherIdentities().containsAll(info.getJoinTo())){
                    if(info.getJoinTo().isEmpty()) continue;
                    for(String s : info.getJoinTo()){
                        if(StringUtils.isEmpty(s)) continue;
                        if(!human.getOtherIdentities().contains(s)) continue;
                        //母亲
                        if(s.indexOf(mother)>=0){
                            bornInfoResponse.setMotherNation(human.getNation());
                            bornInfoResponse.setMotherName(human.getName());
                            bornInfoResponse.setMotherIDCardNo(human.getIDCardNo());
                        }
                        //父亲
                        if(s.indexOf(father)>=0){
                            bornInfoResponse.setFatherNation(human.getNation());
                            bornInfoResponse.setFatherName(human.getName());
                            bornInfoResponse.setFatherIDCardNo(human.getIDCardNo());
                        }
                    }
//                }
            }
            responseList.add(bornInfoResponse);
        }
    }

}
