package com.adtech.rts.model.entity;


import com.adtech.rts.model.entity.base.DataInfo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

//西药处方
@Data
public class WesternMedicinePrescription extends DataInfo {
    private List<WesternMedicineDetail> details=new ArrayList<>();
    private String departmentName;//科室
    private String departmentCode;//科室编码
    private String doctorName;
    private String doctorId;
    private String medicineUseType;//用药类型
    private String medicineUseName;//用药名称
    private BigDecimal prescriptionAmount;//处方金额
    private String no;//处方编号


}
