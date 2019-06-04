package com.adtech.rts.model.util;

import com.adtech.rts.model.enums.TableEnum;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * 将参数设置到query中
 */
public class SetQueryForParams {
    /**
     * 从map中将参数设置到query中
     * @param params
     */
    public static Query setQueryForMap(Map<String,Object> params){
        Query query=new Query();
        params.keySet().forEach(key->{
            //空值验证
            if(!StringUtils.isEmpty(params.get(key))){
                query.addCriteria(Criteria.where(key).is(params.get(key)));
            }
        });
        return query;
    }

    public static Document setDocumentQueryForMap(Map<String,Object> params){
        Document query = new Document();
        params.keySet().forEach(key->{
            //空值验证
            if(!StringUtils.isEmpty(params.get(key))){
                query.put(key,params.get(key));
            }
        });
        return query;
    }





}
