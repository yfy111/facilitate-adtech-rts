package com.adtech.rts.data.human;

import com.adtech.rts.data.base.BaseQuery;
import com.adtech.rts.model.entity.Human;
import com.adtech.rts.model.enums.TableEnum;
import com.adtech.rts.model.util.SetQueryForParams;
import com.google.common.collect.Lists;
import com.mongodb.BasicDBList;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class HumanDataImpl {

    @Autowired
    private BaseQuery baseQuery;

    /**
     * 根据条件查询分页信息
     *
     * @return
     */
    public List<Human> selectByParams(Map<String, Object> params, Integer pageNum, Integer pageSize) {
        Query query = SetQueryForParams.setQueryForMap(params);
        // 排序
        query.with(new Sort(Sort.Direction.DESC, TableEnum.TABLE_SORT_LASTUPDATETIME.getName()));
        List<Human> list = baseQuery.selectByPageParams(query, pageNum, pageSize, Human.class);
        return list;
    }

    /**
     * 根据条件全等查询列表
     *
     * @param params
     * @return
     */
    public List<Human> selectByParams(Map<String, Object> params) {
        Document query = SetQueryForParams.setDocumentQueryForMap(params);
        List<Human> list = baseQuery.selectByParams(query, Human.class, TableEnum.HUMAN_TABLE.getName());
        return list;
    }


    /**
     * 根据条件createDate大于查询列表
     * 增量
     *
     * @param params
     * @return
     */
    public List<Human> selectByCreateDateParams(Map<String, Object> params) {
        Document query = SetQueryForParams.setDocumentQueryForMap(params);
        Document gt = new Document();
        gt.put("$gt", params.get(TableEnum.TABLE_SORT_CREATEDATE.getName()));
        query.put(TableEnum.TABLE_SORT_CREATEDATE.getName(), gt);
        List<Human> list = baseQuery.selectByParams(query, Human.class, TableEnum.HUMAN_TABLE.getName());
        return list;
    }


    /**
     * 获取总数
     *
     * @param params
     * @return
     */
    public Long countByParams(Map<String, Object> params) {
        Query query = SetQueryForParams.setQueryForMap(params);
        return baseQuery.countByParams(query, TableEnum.HUMAN_TABLE.getName());
    }


    /**
     * 通过otherIdentities 进行查询
     *
     * @param params
     * @return
     */
    public List<Human> getListByIdentities(Map<String, Object> params) {
        Document query = SetQueryForParams.setDocumentQueryForMap(params);
        List<Human> list = baseQuery.selectByParams(query, Human.class, TableEnum.HUMAN_TABLE.getName());
        return list;
    }

    /**
     * listName 进行or查询
     *
     * @param params
     * @return
     */
    public List<Human> getListByIdentitiesOr(Map<String, Object> params,String listName) {
        Document or = new Document();
        BasicDBList values = baseQuery.createOrValues(params,listName);
        if(values==null) return Lists.newArrayList();
        or.put("$or", values);
        List<Human> lists = baseQuery.selectByParams(or, Human.class, TableEnum.HUMAN_TABLE.getName());
        return lists;
    }
}
