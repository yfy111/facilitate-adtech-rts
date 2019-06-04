package com.adtech.rts.service;

import com.adtech.rts.data.prescription.FeeOverviewDataImpl;
import com.adtech.rts.data.prescription.PrescriptionDataImpl;
import com.adtech.rts.model.entity.Prescription;
import com.adtech.rts.model.entity.finance.FeeOverview;
import com.adtech.rts.model.enums.CodeEnum;
import com.adtech.rts.model.enums.TableEnum;
import com.adtech.rts.model.page.Page;
import com.adtech.rts.model.response.prescription.PrescriptionResponse;
import com.adtech.rts.model.result.Result;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 就诊记录
 */
@Service
public class PrescriptionService {

    @Autowired
    private PrescriptionDataImpl prescriptionData;

    @Autowired
    private FeeOverviewDataImpl feeOverviewData;


    /**
     * 分页查询处方记录
     *
     * @return
     */
    public Result getPrescriptionPage(String otherIdentities
            , String regionCode
            , String serialNo
            , String organizationCode
            , Integer pageNum, Integer pageSize) {
        Map<String, Object> params = new LinkedHashMap<>();
        if(StringUtils.isEmpty(otherIdentities)) return new Result(CodeEnum.FAIL_PARAMCHECK);
        List<PrescriptionResponse> prescriptionResponses = new ArrayList<>();
        params.put(TableEnum.TABLE_FIELD_JOINTO.getName(), Arrays.asList(otherIdentities.split(",")));
//        params.put(TableEnum.TABLE_FIELD_SERIALNO.getName(), serialNo);
//        params.put(TableEnum.TABLE_FIELD_REGIONCODE.getName(), regionCode);
//        params.put(TableEnum.TABLE_FIELD_IORGANIZATIONCODE.getName(), organizationCode);
        Result result = new Result(CodeEnum.SUCCESS);
        List<Prescription> list = prescriptionData.findByOrParams(params, TableEnum.TABLE_FIELD_JOINTO.getName());
        setList(prescriptionResponses, list);
        Page page = new Page();
        page.setList(prescriptionResponses);
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);
        page.setTotalCount(prescriptionData.countByOrParams(params));
        result.setData(page);
        return result;
    }

    /**
     * 获取用户处方全量列表
     *
     * @param otherIdentities
     * @param regionCode
     * @param serialNo
     * @param organizationCode
     * @return
     */
    public Result getPrescriptionList(String otherIdentities
            , String regionCode
            , String serialNo
            , String organizationCode
    ) {
        if(StringUtils.isEmpty(otherIdentities)) return new Result(CodeEnum.FAIL_PARAMCHECK);
        List<PrescriptionResponse> prescriptionResponses = new ArrayList<>();
        Map<String, Object> params = new LinkedHashMap<>();
//        params.put(TableEnum.TABLE_FIELD_SERIALNO.getName(), serialNo);
        params.put(TableEnum.TABLE_FIELD_JOINTO.getName(), Arrays.asList(otherIdentities.split(",")));
        params.put(TableEnum.TABLE_FIELD_REGIONCODE.getName(), regionCode);
        params.put(TableEnum.TABLE_FIELD_IORGANIZATIONCODE.getName(), organizationCode);
        Result result = new Result(CodeEnum.SUCCESS);
        List<Prescription> list = prescriptionData.findByOrParams(params, TableEnum.TABLE_FIELD_JOINTO.getName());
        setList(prescriptionResponses, list);
        result.setData(prescriptionResponses);
        return result;
    }

    public void setList(List<PrescriptionResponse> prescriptionResponses, List<Prescription> list) {
        Map<String, Object> totalCostParams = new HashMap<>();
        List<String> list1 = Lists.newArrayList();
        list.stream().forEach(item -> {
            if(!StringUtils.isEmpty(item.getOtherIdentities()))
                list1.addAll(item.getOtherIdentities());
        });
        totalCostParams.put(TableEnum.TABLE_FIELD_JOINTO.getName(), list1);
        List<FeeOverview> prescriptions = feeOverviewData.findByTimeOrParams(totalCostParams, TableEnum.TABLE_FIELD_JOINTO.getName());

        list.stream().forEach(item -> {
            list1.addAll(item.getOtherIdentities());
            PrescriptionResponse response = new PrescriptionResponse();

            response.setOtherIdentities(item.getOtherIdentities());
            for (FeeOverview f : prescriptions) {
                if (item.getOtherIdentities().containsAll(f.getJoinTo())) {
                    response.setTotalCost(f.getTotalCost());
                    break;
                }
            }
            response.setActionTime(item.getActionTime());
            response.setDepartmentName(item.getDepartmentName());
            response.setDoctorName(item.getDoctorName());
            response.setOrignalOrganizationName(item.getOrignalOrganizationName());
            response.setPrescriptionType(item.getPrescriptionType());
            response.setPrescriptionTypeName(item.getPrescriptionTypeName());
            response.setDiagnose(item.getDiagnose());
            response.setRemark(item.getRemark());
            response.setDisease(item.getDisease());
            prescriptionResponses.add(response);
        });
    }


}
