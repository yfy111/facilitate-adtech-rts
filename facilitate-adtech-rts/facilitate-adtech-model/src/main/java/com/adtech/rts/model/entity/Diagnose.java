package com.adtech.rts.model.entity;

import com.adtech.rts.model.entity.base.BizDataInfo;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection="Diagnose")
public class Diagnose  extends BizDataInfo {

    private String disease;//疾病
    private String diagnose;//诊断
    private String actionTime;
    private String serialNo;
    private String doctorCode;
    private String doctorName;
}
