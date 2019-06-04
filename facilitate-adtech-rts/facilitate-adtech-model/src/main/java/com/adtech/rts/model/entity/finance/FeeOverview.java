package com.adtech.rts.model.entity.finance;

import com.adtech.rts.model.entity.base.BizDataInfo;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection="FeeOverview")
public class FeeOverview extends BizDataInfo {
    private String totalCost;//总价
    private String invoice;//发票
    private String actionTime;
    private String departmentCode;//就诊科室
    private String departmentName;
    private String balanceNo;//结算单号
    private String paymentMethod;//结算方式
    private String serialNo;//就诊号

    private String doctorName;
    private String doctorCode;

}
