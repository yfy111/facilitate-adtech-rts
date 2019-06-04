package com.adtech.rts.model.entity.base;

import com.adtech.rts.model.entity.common.ItemData;
import lombok.Data;
import org.bson.types.ObjectId;

import java.util.List;

@Data
public abstract class BaseDataInfo extends DataInfo {

    protected String orignalName;
    protected String orignalCode;
    protected String standardName;//系统
    protected String standardCode;//系统
    protected List<ItemData> items;//系统
    protected ObjectId _id;//系统
}
