package com.adtech.rts.service;

import com.adtech.rts.data.health_record.HealthRecordDataImpl;
import com.adtech.rts.data.human.HumanDataImpl;
import com.adtech.rts.data.medical.HospitalizationInfoDataImpl;
import com.adtech.rts.data.medical.MedicalActionDataImpl;
import com.adtech.rts.httpclient.RtsInterface;
import com.adtech.rts.model.entity.HealthRecord;
import com.adtech.rts.model.entity.HospitalizationInfo;
import com.adtech.rts.model.entity.Human;
import com.adtech.rts.model.entity.MedicalAction;
import com.adtech.rts.model.enums.CodeEnum;
import com.adtech.rts.model.enums.TableEnum;
import com.adtech.rts.model.page.Page;
import com.adtech.rts.model.page.PageForList;
import com.adtech.rts.model.response.HumanResponse;
import com.adtech.rts.model.response.bpi.BasicPopulationInformationShowData;
import com.adtech.rts.model.result.Result;
import com.adtech.rts.properties.FacilitateProperties;
import com.adtech.rts.redisservice.RedisService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
@Slf4j
public class HumanService {

    @Autowired
    private HumanDataImpl humanData;

    @Autowired
    private MedicalActionDataImpl medicalActionData;

    @Autowired
    private HospitalizationInfoDataImpl hospitalizationInfoData;

    @Autowired
    private RtsInterface rtsInterface;

    @Autowired
    private FacilitateProperties facilitateProperties;

    @Autowired
    private HealthRecordDataImpl healthRecordData;

    @Autowired
    private PageForList pageForList;

    @Autowired
    private RedisService redisService;

    /**
     * 获取患者信息-page
     *
     * @param pageNum
     * @return
     */
    public Result<Page> getInfoPage(Integer pageNum, String regionCode, String organizationCode, Integer pageSize
            ,String actionType,String name,String idCard,String hospitalName) {
        Result result = new Result<>(CodeEnum.SUCCESS);
        Page page = new Page();
        //条件区域
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> humanParams = new HashMap<>();
        //查询人员信息
        List<String> identitiesList = Lists.newArrayList();
        Set<String> set = Sets.newHashSet();
        //list申明区域
        List<MedicalAction> medicalActions = null;
        List<Human> humans = null;
        //如果是按照人查询则使用human做主表
        if(!StringUtils.isEmpty(name)||!StringUtils.isEmpty(idCard)){
            humanParams.put(TableEnum.TABLE_FIELD_NAME.getName(),name);
            humanParams.put(TableEnum.TABLE_FIELD_IDCARDNO.getName(),idCard);
            humans = humanData.selectByParams(humanParams,pageNum,pageSize);
            if(humans==null||humans.isEmpty()){
                medicalActions = Lists.newArrayList();
            }else{
                humans.forEach(item->set.addAll(item.getOtherIdentities()));
                identitiesList.addAll(set);
                params.put(TableEnum.TABLE_FIELD_JOINTO.getName(),identitiesList);
                medicalActions  = medicalActionData.findByOrParams(params,TableEnum.TABLE_FIELD_JOINTO.getName());
                medicalActions = pageForList.pageList(medicalActions,pageSize,pageNum).getList();
                page.setTotalCount(Long.valueOf(medicalActions.size()));
            }

        }else{
            params.put(TableEnum.TABLE_FIELD_REGIONCODE.getName(), regionCode);
            params.put(TableEnum.TABLE_FIELD_IORGANIZATIONCODE.getName(), organizationCode);
            params.put(TableEnum.TABLE_FIELD_ACTION_TYPE.getName(),actionType);
            params.put(TableEnum.TABLE_FIELD_ORIGNALORGANIZATIONNAME.getName(),hospitalName);
            medicalActions  = medicalActionData.findByParams(params, pageNum, pageSize);
            //查询所有的human
            medicalActions.stream().forEach(item -> identitiesList.addAll(item.getJoinTo()));
            humanParams.put(TableEnum.TABLE_OTHERIDENTITIES.getName(),identitiesList);
            humans = humanData.getListByIdentitiesOr(humanParams,TableEnum.TABLE_OTHERIDENTITIES.getName());
            page.setTotalCount(medicalActionData.countByParams(params));
        }
        List<HumanResponse> humanResponses = new ArrayList<>();
        //查询医生关联条件
        List<String> doctorJoinTo = Lists.newArrayList();
        medicalActions.stream().forEach(item -> doctorJoinTo.addAll(item.getOtherIdentities()));
        Map<String,Object> doctorMap = Maps.newHashMap();
        doctorMap.put(TableEnum.TABLE_FIELD_JOINTO.getName(),doctorJoinTo);
        List<HospitalizationInfo> doctorList =  hospitalizationInfoData.getListByOr(doctorMap,TableEnum.TABLE_FIELD_JOINTO.getName());

        //获得身份证号，查询建档信息
        List<String> idCards = Lists.newArrayList();
        Map<String,List<HealthRecord>> healthRecordMap = getHealthRecordList(humans,idCards);//获得对应建档map
        setData(medicalActions,humans,healthRecordMap,doctorList,humanResponses);//设置数据

        page.setList(humanResponses);
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);

        result.setData(page);
        return result;
    }


    /**
     * 根据身份证号查询建档信息
     * 转换成map
     * @param humans
     * @param idCards
     */
    private Map<String,List<HealthRecord>> getHealthRecordList(List<Human> humans,List<String> idCards){
        Set<String> idCardSet = Sets.newHashSet();//身份证去重
        for(Human h : humans){
            if(StringUtils.isEmpty(h.getIDCardNo())) continue;
            idCardSet.add(h.getIDCardNo());
        }
        idCards.addAll(idCardSet);
        Map<String,Object> healthRecordParams = Maps.newHashMap();
        healthRecordParams.put(TableEnum.TABLE_FIELD_IDCARDNO.getName(),idCards);
        List<HealthRecord> healthRecordList =  healthRecordData.getListByIdentitiesOr(healthRecordParams,TableEnum.TABLE_FIELD_IDCARDNO.getName());
        Map<String,List<HealthRecord>> map = Maps.newHashMap();
        for(HealthRecord h : healthRecordList){
            if(map.containsKey(h.getIDCardNo())){
                map.get(h.getIDCardNo()).add(h);
            }else{
                List<HealthRecord> list = Lists.newArrayList();
                list.add(h);
                map.put(h.getIDCardNo(),list);
            }
        }
        return map;
    }

    /**
     * @param IDCardNo
     * @return
     */
    public Result<Human> getInfo(String IDCardNo) {
        Result result = new Result<>(CodeEnum.SUCCESS);
        Map<String, Object> humanParams = new HashMap<>();
        humanParams.put(TableEnum.TABLE_FIELD_IDCARDNO.getName(), IDCardNo);
        List<Human> list = humanData.selectByParams(humanParams);
        if (list.isEmpty()) result.setData(new Human());
        else result.setData(list.get(0));
        return result;
    }

    /**
     * 通过otherIdentities获取list
     * @param map
     * @return
     */
    public List<Human> getList(Map<String,Object> map){
       return humanData.selectByParams(map);
    }

    /**
     * 获取家庭信息
     *
     * @return
     */
    public Result<BasicPopulationInformationShowData> getBasicPopulationInformationShowDataList(HttpServletRequest request) {
        String url = facilitateProperties.getApplication_plus() + "/api/pbc/get-people";
        Map<String, Object> map = new HashMap<>();
        map.put("plus_token", request.getHeader("rts_token"));
        map.put("plus_secret", request.getHeader("rts_secret"));
        Result<BasicPopulationInformationShowData> result = rtsInterface.restPost(url, Result.class, map);
        log.info("plus人口库数据调用返回：{}",result);
        return result;
    }


    private void setData(List<MedicalAction> medicalActions, List<Human> humans,
                         Map<String,List<HealthRecord>> healthRecordMap,List<HospitalizationInfo> doctorList,
                         List<HumanResponse> humanResponses){
        for(MedicalAction item : medicalActions){
            HumanResponse hr = new HumanResponse();
            hr.setActionTime(item.getActionTime());
            hr.setSerialNo(item.getSerialNo());
            hr.setOrignalOrganizationCode(item.getOrignalOrganizationCode());
            hr.setOrignalOrganizationName(item.getOrignalOrganizationName());
            hr.setDoctorName(item.getDoctorName());
            hr.setRegionCode(item.getRegionCode());
            hr.setRegionName(item.getRegionName());
            hr.setActionType(item.getActionType());
            hr.setDepartmentName(item.getDepartmentName());
            hr.setOtherIdentities(item.getOtherIdentities());
            for(Human h : humans){
                //判断包含关系
                if(h.getOtherIdentities().containsAll(item.getJoinTo())){
                    hr.setName(h.getName());
                    hr.setIDCardNo(h.getIDCardNo());
                    hr.setValidIDCardNo(h.getValidIDCardNo());
                    if (h.getMobiles() != null && !h.getMobiles().isEmpty())
                        hr.setMobile(h.getMobiles().get(0));
                }
            }
            if(StringUtils.isEmpty(hr.getIDCardNo())) hr.setRecordMessage("未知");
            else{
                if(healthRecordMap.containsKey(hr.getIDCardNo())){
                    if(healthRecordMap.get(hr.getIDCardNo()).isEmpty()) hr.setRecordMessage("本地未建档");
                    else hr.setRecordMessage("已建档"+healthRecordMap.get(hr.getIDCardNo()).size()+"次");
                }else {
                    hr.setRecordMessage("本地未建档");
                }
            }
            for(HospitalizationInfo h: doctorList){
                //判断包含关系
                if(h.getJoinTo().containsAll(item.getOtherIdentities())){
                    if(!StringUtils.isEmpty(h.getDoctorName()))
                        hr.setDoctorName(h.getDoctorName());
                    if(!StringUtils.isEmpty(h.getHistoryDoctorName()))
                        hr.setHistoryDoctorName(h.getHistoryDoctorName());
                    if(!StringUtils.isEmpty(h.getOutpatientDoctorName()))
                        hr.setDoctorName(h.getOutpatientDoctorName());
                }
            }
            humanResponses.add(hr);
        }
    }


}
