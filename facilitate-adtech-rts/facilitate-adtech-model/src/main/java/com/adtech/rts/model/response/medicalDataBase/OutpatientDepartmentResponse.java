package com.adtech.rts.model.response.medicalDataBase;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OutpatientDepartmentResponse {
    @ApiModelProperty(value="门诊人次")
    private List<OutpatientDepartmentPopulationResponse> medicalActionDoor= Lists.newArrayList();
    @ApiModelProperty(value="建档人数")
    private List<OutpatientDepartmentPopulationResponse> fileEstablishment= Lists.newArrayList();
    @ApiModelProperty(value="住院人次")
    private List<OutpatientDepartmentPopulationResponse> hospitalization= Lists.newArrayList();
    @ApiModelProperty(value="出生人次")
    private List<OutpatientDepartmentPopulationResponse> birth= Lists.newArrayList();
    @ApiModelProperty(value="随访人次")
    private List<OutpatientDepartmentPopulationResponse> followUp= Lists.newArrayList();
    @ApiModelProperty(value="疫苗接种人次")
    private List<OutpatientDepartmentPopulationResponse> vaccine= Lists.newArrayList();

//    @ApiModelProperty(value="转诊人次")
//    private List<OutpatientDepartmentPopulationResponse> referralPopulation;
    private Date doorLastTime;
    private Date birthLastTime;
    private Date followUpLastTime;
    private Date vaccineLastTime;
    private Date fileLastTime;
    private Date tomorrow;
}
