package com.adtech.rts.model.response.bpi;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.function.Function;

/**
 * 家庭关系
 */
@Data
public class FamilyMemberMessageShwData {

    @ApiModelProperty(value = "姓名", required = true)
    private String name = "无数据";

    @ApiModelProperty(value = "关系名称", required = true)
    private String member;

    @ApiModelProperty(value = "关系编码", required = true)
    private String memberId;

    @ApiModelProperty(value = "身份证脱敏字段", required = true)
    private String idCard;

    @ApiModelProperty(value = "身份证未脱敏,防骚搞字段", required = true)
    private String idCardNoHide;

    @ApiModelProperty(value = "性别:0-女，1-男", required = true)
    private String sex;

    @ApiModelProperty(value = "图片", required = true)
    private String img;

    @ApiModelProperty(value = "排序", required = true)
    private String sort;

    @ApiModelProperty(value = "辈分标识，与本人相隔辈数；本人为0，父母为1，婆婆爷爷为2，以此类推", required = true)
    private String seniority;

    @ApiModelProperty(value = "前端显示class", required = true)
    private String clazz;

    private static Function<String, FamilyMemberMessageShwData> createBySex = sex -> {
        FamilyMemberMessageShwData data = new FamilyMemberMessageShwData();
        data.sex = sex;
        return data;
    };

    // 生成男性的家庭成员对象
    public static FamilyMemberMessageShwData male() {
        return createBySex.apply("1");
    }

    // 生成女性的家庭成员对象
    public static FamilyMemberMessageShwData female() {
        return createBySex.apply("0");
    }

}
