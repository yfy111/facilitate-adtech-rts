package com.adtech.rts.service;

import com.adtech.rts.data.prescription.PrescriptionDetailDataImpl;
import com.adtech.rts.model.entity.PrescriptionDetail;
import com.adtech.rts.model.enums.CodeEnum;
import com.adtech.rts.model.enums.TableEnum;
import com.adtech.rts.model.response.prescription.PrescriptionDetailResponse;
import com.adtech.rts.model.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 就诊记录
 */
@Service
public class PrescriptionDetailService {

    @Autowired
    private PrescriptionDetailDataImpl prescriptionDetailData;

    /**
     * 查询处方详情信息
     * @return
     */
    public Result<List<PrescriptionDetailResponse>> findByParams(String joinTo, String regionCode){
        Result result =  new Result<>();
        if(StringUtils.isEmpty(joinTo)){
            result.setCode(CodeEnum.FAIL_PARAMCHECK.getCode());
            result.setMsg(CodeEnum.FAIL_PARAMCHECK.getMsg());
        }else{
            Map<String,Object> params = new HashMap<>();
            List<String> joinToList = Arrays.asList(joinTo.split(","));
            params.put(TableEnum.TABLE_FIELD_JOINTO.getName(),joinToList);
//            params.put(TableEnum.TABLE_FIELD_REGIONCODE.getName(),regionCode);
            result.setCode(CodeEnum.SUCCESS.getCode());
            result.setMsg(CodeEnum.SUCCESS.getMsg());
            List<PrescriptionDetailResponse> dlist = new ArrayList<>();
            List<PrescriptionDetail> list = prescriptionDetailData.findByParams(params);
            list.forEach(item->{
                PrescriptionDetailResponse p = new PrescriptionDetailResponse();
                dlist.add(p);
                p.setDepartmentCode(item.getDepartmentCode());
                p.setDepartmentName(item.getDepartmentName());
                p.setBatchNumber(item.getBatchNumber());
                p.setCheckPeople(item.getCheckPeople());
                p.setDeletePeople(item.getDeletePeople());
                p.setDispenser(item.getDispenser());
                p.setDosageFormCode(item.getDosageFormCode());
                p.setDosageFormName(item.getDosageFormName());
                p.setDrugCode(item.getDrugCode());
                p.setDrugName(item.getDrugName());
                p.setDrugUseDay(item.getDrugUseDay());
                p.setDrugUseDoseUnits(item.getDrugUseDoseUnits());
                p.setDrugUseSecondaryDose(item.getDrugUseSecondaryDose());
                p.setDrugUseTotalDose(item.getDrugUseTotalDose());
                p.setDrugUseTotalDoseUnits(item.getDrugUseTotalDoseUnits());
                p.setDrugUseWay(item.getDrugUseWay());
                p.setDrugUseWayCode(item.getDrugUseWayCode());
                p.setExecutor(item.getExecutor());
                p.setFrequency(item.getFrequency());
                p.setImplementDepartmentCode(item.getImplementDepartmentCode());
                p.setImplementDepartmentName(item.getImplementDepartmentName());
                p.setQuantity(item.getQuantity());
                p.setPackagingManufacturer(item.getPackagingManufacturer());
                p.setProduceManufacturer(item.getProduceManufacturer());
                p.setRemark(item.getRemark());
                p.setStandard(item.getStandard());
                p.setTimes(item.getTimes());
                p.setUnit(item.getUnit());
                p.setUnitPrice(item.getUnitPrice());
            });
            result.setData(dlist);
        }
        return result;
    }


}
