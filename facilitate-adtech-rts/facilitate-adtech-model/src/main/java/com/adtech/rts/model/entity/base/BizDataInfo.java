package com.adtech.rts.model.entity.base;

import com.adtech.rts.model.entity.common.ItemData;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection="BornInfo")
public class BizDataInfo extends DataInfo {
    protected List<String> joinTo;
    protected List<String> otherIdentities;//其他标识
    protected List<ItemData> items;//系统
}
