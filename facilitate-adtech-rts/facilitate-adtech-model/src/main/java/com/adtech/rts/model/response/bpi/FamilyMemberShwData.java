package com.adtech.rts.model.response.bpi;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 家庭关系
 */
@Data
public class FamilyMemberShwData {

    //    @ApiModelProperty(value = "父亲", required = true)
    //    private FamilyMemberMessageShwData father;
    @ApiModelProperty(value = "父母一辈", required = true)
    private List<FamilyMemberMessageShwData> parent = new ArrayList<>();

    //    @ApiModelProperty(value = "母亲", required = true)
    //    private FamilyMemberMessageShwData mather;
    @ApiModelProperty(value = "配偶", required = true)
    private FamilyMemberMessageShwData spouse;

    @ApiModelProperty(value = "本人", required = true)
    private FamilyMemberMessageShwData myself;

    //    @ApiModelProperty(value = "女儿", required = true)
    //    private List<FamilyMemberMessageShwData> childrenGirl= new ArrayList<>();
    //    @ApiModelProperty(value = "儿子", required = true)
    //    private List<FamilyMemberMessageShwData> childrenBoy= new ArrayList<>();
    @ApiModelProperty(value = "子女", required = true)
    private List<FamilyMemberMessageShwData> children = new ArrayList<>();
}
