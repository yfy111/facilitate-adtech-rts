package com.adtech.rts.model.response.medicalDataBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OutpatientDepartmentPopulationResponse {
    @ApiModelProperty(value="键")
    private String name;
    @ApiModelProperty(value="值")
    private String value;
}
