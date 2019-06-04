package com.adtech.rts.model.response.health;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class AdviceResponse {
    @ApiModelProperty(value="住院号")
    private String hospitalizationSerialNo;//住院号
    @ApiModelProperty(value="医嘱编号")
    private String serialNo;//医嘱编号
    @ApiModelProperty(value="处方号")
    private String prescriptionNo;//处方号
    @ApiModelProperty(value="编码")
    private String code;//编码
    @ApiModelProperty(value="医嘱名称")
    private String name;//医嘱名称
    @ApiModelProperty(value="医嘱内容")
    private String content;//医嘱内容
    @ApiModelProperty(value="数量")
    private String amount;//数量
    @ApiModelProperty(value="用法")
    private String drugUseWay;//用法
    @ApiModelProperty(value="当日执行次数")
    private String times;//当日执行次数
    @ApiModelProperty(value="频次")
    private String frequency;//频次,频率
    @ApiModelProperty(value="速度")
    private String speed;//速度
    @ApiModelProperty(value="单位")
    private String unit;//单位
    @ApiModelProperty(value="单价")
    private String unitPrice;//单价
    @ApiModelProperty(value="用量")
    private String dosage;//用量
    @ApiModelProperty(value="用药时间")
    private String medicationTime;//用药时间
    @ApiModelProperty(value="开医嘱人")
    private String adviceFromDoctorName;//开医嘱人
    @ApiModelProperty(value="核对人")
    private String checkDoctorName;
    @ApiModelProperty(value="执行护士")
    private String excuteNurseName;//执行护
    @ApiModelProperty(value="执行医生")
    private String excuteDoctorName;//
    @ApiModelProperty(value="执行时间")
    private String excuteTime;//执行时间
    @ApiModelProperty(value="就诊科室")
    private  String departmentName;
    @ApiModelProperty(value="执行科室")
    private String excuteDepartmentName;
    @ApiModelProperty(value="医嘱类型")
    private String adviceType;//医嘱类型
    @ApiModelProperty(value="备注")
    private String remark;
    @ApiModelProperty(value="-")
    private String cancelTime;
    @ApiModelProperty(value="医嘱停止医生")
    private String  stopDoctorName;
    @ApiModelProperty(value="医嘱停止护士")
    private String stopNurseName;
    private String beginTime;
    private String endTime;

}
