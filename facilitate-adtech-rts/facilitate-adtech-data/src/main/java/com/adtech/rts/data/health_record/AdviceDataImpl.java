package com.adtech.rts.data.health_record;

import com.adtech.rts.data.base.BaseQuery;
import com.adtech.rts.model.entity.Advice;
import com.adtech.rts.model.enums.TableEnum;
import com.adtech.rts.model.util.SetQueryForParams;
import com.google.common.collect.Lists;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class AdviceDataImpl {


    @Autowired
    private BaseQuery baseQuery;

    /**
     * 根据条件查询分页信息
     *
     * @return
     */
    public List<Advice> selectByParams(Map<String, Object> params, Integer pageNum, Integer pageSize) {
        Query query = SetQueryForParams.setQueryForMap(params);
        query.addCriteria(Criteria.where("name").ne(null));
        // 排序
        query.with(new Sort(Sort.Direction.DESC, TableEnum.TABLE_SORT_ACTIONTIME_FORMAT.getName()));
        List<Advice> list = baseQuery.selectByPageParams(query, pageNum, pageSize, Advice.class);
        return list;
    }

    /**
     * 根据条件全等查询列表
     *
     * @param params
     * @return
     */
    public List<Advice> selectByParams(Map<String, Object> params) {
        Document query = SetQueryForParams.setDocumentQueryForMap(params);
        List<Advice> list = baseQuery.selectByParams(query, Advice.class, TableEnum.ADVICE_TABLE.getName());
        return list;
    }


    /**
     * 根据条件全等查询列表
     *
     * @param params
     * @return
     */
    public List<Advice> selectByQueryParams(Map<String, Object> params) {
        Query query = SetQueryForParams.setQueryForMap(params);
        List<Advice> list = baseQuery.selectByParams(query, Advice.class);
        return list;
    }

    /**
     * 根据条件createDate大于查询列表
     * 增量
     *
     * @param params
     * @return
     */
    public List<Advice> selectByCreateDateParams(Map<String, Object> params) {
        Document query = SetQueryForParams.setDocumentQueryForMap(params);
        Document gt = new Document();
        gt.put("$gt", params.get(TableEnum.TABLE_SORT_CREATEDATE.getName()));
        query.put(TableEnum.TABLE_SORT_CREATEDATE.getName(), gt);
        List<Advice> list = baseQuery.selectByParams(query, Advice.class, TableEnum.ADVICE_TABLE.getName());
        return list;
    }


    /**
     * 根据actionTimeFormat条件查询list
     *
     * @param params
     * @return
     */
    public List<Advice> findByTimeParams(Map<String, Object> params) {
        BasicDBObject obj = new BasicDBObject();
        BasicDBObject objl = new BasicDBObject();
        objl.put("$gt", params.get("birthBeginTime"));
        objl.put("$lt", params.get("endTime"));
        obj.put(TableEnum.TABLE_SORT_ACTIONTIME_FORMAT.getName(), objl);
        List<Advice> list = baseQuery.selectByParams(obj, Advice.class, TableEnum.ADVICE_TABLE.getName());
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
        query.addCriteria(Criteria.where("name").ne(null));
        return baseQuery.countByParams(query, TableEnum.ADVICE_TABLE.getName());
    }

    /**
     * 根据时间段获取统计数据
     *
     * @param params
     * @return
     */
    public Long countByTime(Map<String, Object> params) {
        BasicDBObject obj = new BasicDBObject();
        BasicDBObject objl = new BasicDBObject();
        objl.put("$gt", params.get("beginTime"));
        objl.put("$lt", params.get("endTime"));
        obj.put(TableEnum.TABLE_SORT_ACTIONTIME_FORMAT.getName(), objl);
        return baseQuery.countByParams(obj, TableEnum.ADVICE_TABLE.getName());
    }

    /**
     * 通过otherIdentities 进行查询
     *
     * @param params
     * @return
     */
    public List<Advice> getListByIdentities(Map<String, Object> params) {
        Document query = SetQueryForParams.setDocumentQueryForMap(params);
        List<Advice> list = baseQuery.selectByParams(query, Advice.class, TableEnum.ADVICE_TABLE.getName());
        return list;
    }

    /**
     * 通过otherIdentities 进行or查询
     *
     * @param params
     * @return
     */
    public List<Advice> getListByOr(Map<String, Object> params, String listName) {
        Document or = new Document();
        BasicDBList values = baseQuery.createOrValues(params, listName);
        if (values == null) return Lists.newArrayList();
        or.put("$or", values);
        List<Advice> lists = baseQuery.selectByParams(or, Advice.class, TableEnum.ADVICE_TABLE.getName());
        return lists;
    }
}
