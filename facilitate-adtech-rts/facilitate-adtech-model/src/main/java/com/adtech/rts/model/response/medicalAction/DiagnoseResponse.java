package com.adtech.rts.model.response.medicalAction;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DiagnoseResponse {
    @ApiModelProperty(value="疾病")
    private String disease;//疾病
    @ApiModelProperty(value="诊断")
    private String diagnose;//诊断
    @ApiModelProperty(value="就诊时间")
    private String actionTime;
}
