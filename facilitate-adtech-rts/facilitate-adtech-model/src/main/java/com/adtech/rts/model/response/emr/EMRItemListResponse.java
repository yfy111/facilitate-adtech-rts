package com.adtech.rts.model.response.emr;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class EMRItemListResponse {
    @ApiModelProperty(value="itemCode")
    private String itemCode;
    @ApiModelProperty(value="inHospitalDiagnoseCode")
    private String inHospitalDiagnoseCode;//住院号
    @ApiModelProperty(value="documentCode")
    private String documentCode;
    @ApiModelProperty(value="itemName")
    private String itemName;
    @ApiModelProperty(value="documentName")
    private String documentName;
    @ApiModelProperty(value="value")
    private String value;
    @ApiModelProperty(value="otherIdentities")
    private List<String> otherIdentities;
}
