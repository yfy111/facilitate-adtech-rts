package com.adtech.rts.data.medical;

import com.adtech.rts.data.base.BaseQuery;
import com.adtech.rts.model.entity.HospitalizationInfo;
import com.adtech.rts.model.entity.Human;
import com.adtech.rts.model.enums.TableEnum;
import com.google.common.collect.Lists;
import com.mongodb.BasicDBList;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class HospitalizationInfoDataImpl {

    @Autowired
    private BaseQuery baseQuery;
    /**
     * listName 进行or查询
     *
     * @param params
     * @return
     */
    public List<HospitalizationInfo> getListByOr(Map<String, Object> params, String listName) {
        Document or = new Document();
        BasicDBList values = baseQuery.createOrValues(params,listName);
        if(values==null) return Lists.newArrayList();
        or.put("$or", values);
        List<HospitalizationInfo> lists = baseQuery.selectByParams(or, HospitalizationInfo.class, TableEnum.HOSPITALIZATIONINFO_TABLE.getName());
        return lists;
    }
}
