package com.adtech.rts.data.medical;

import com.adtech.rts.data.base.BaseQuery;
import com.adtech.rts.model.entity.Diagnose;
import com.adtech.rts.model.enums.TableEnum;
import com.adtech.rts.model.util.SetQueryForParams;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class DiagnoseDataImpl {

    @Autowired
    private BaseQuery baseQuery;

    /**
     * 根据条件分页
     *
     * @param params
     * @param pageNum
     * @return
     */
    public List<Diagnose> findByParams(Map<String, Object> params, Integer pageNum, Integer pageSize) {
        Query query = SetQueryForParams.setQueryForMap(params);
        query.with(new Sort(Sort.Direction.DESC, TableEnum.TABLE_SORT_ACTIONTIME_FORMAT.getName()));
        // 排序
        List<Diagnose> list = baseQuery.selectByPageParams(query, pageNum, pageSize, Diagnose.class);
        return list;
    }

    /**
     * 根据条件查询list
     *
     * @param params
     * @return
     */
    public List<Diagnose> findByParams(Map<String, Object> params) {
        Document query = SetQueryForParams.setDocumentQueryForMap(params);
        List<Diagnose> list = baseQuery.selectByParams(query, Diagnose.class, TableEnum.DIAGNOSE_TABLE.getName());
        return list;
    }

    /**
     * 根据条件createDate大于查询列表
     * 增量
     *
     * @param params
     * @return
     */
    public List<Diagnose> selectByCreateDateParams(Map<String, Object> params, Integer pageNum, Integer pageSize) {
        Query query = new Query();
        query.with(new Sort(Sort.Direction.DESC, TableEnum.TABLE_SORT_LASTUPDATETIME.getName()));
        query.addCriteria(Criteria.where(TableEnum.TABLE_SORT_CREATEDATE.getName())
                .gt(params.get(TableEnum.TABLE_SORT_CREATEDATE.getName())));
        List<Diagnose> list = baseQuery.selectByPageParams(query, pageNum, pageSize, Diagnose.class);
        return list;
    }



    /**
     * 根据条件查询list
     *
     * @param params
     * @return
     */
    public List<Diagnose> findByTimeParams(Map<String, Object> params) {
        BasicDBObject obj = new BasicDBObject();
        BasicDBObject objl = new BasicDBObject();
        objl.put("$gt", params.get("doorBeginTime"));
        objl.put("$lt",params.get("endTime"));
        obj.put(TableEnum.TABLE_SORT_ACTIONTIME_FORMAT.getName(), objl);
        if(!StringUtils.isEmpty(params.get(TableEnum.TABLE_FIELD_REGIONCODE.getName())))
            obj.put(TableEnum.TABLE_FIELD_REGIONCODE.getName(), params.get(TableEnum.TABLE_FIELD_REGIONCODE.getName()));
        List<Diagnose> list = baseQuery.selectByParams(obj, Diagnose.class, TableEnum.DIAGNOSE_TABLE.getName());
        return list;
    }


    /**
     * 根据条件查询全量list
     *
     * @param params
     * @return
     */
    public List<Diagnose> findByOrParams(Map<String, Object> params, String listName) {
        Document or = new Document();
        BasicDBList values = baseQuery.createOrValues(params,listName);
        or.put("$or", values);
        // 排序
        Document sort = new Document();
        sort.put(TableEnum.TABLE_SORT_ACTIONTIME.getName(), -1);
        return baseQuery.selectSortByParams(or, Diagnose.class, TableEnum.DIAGNOSE_TABLE.getName(), sort);
    }


    /**
     * 详情查看
     *
     * @param params
     * @return
     */
    public Diagnose findInfo(Map<String, Object> params) {
        Query query = SetQueryForParams.setQueryForMap(params);
        return baseQuery.selectOneByParams(query, Diagnose.class);
    }

    /**
     * 获取总数
     *
     * @param params
     * @return
     */
    public Long countByParams(Map<String, Object> params) {
        Query query = SetQueryForParams.setQueryForMap(params);
        return baseQuery.countByParams(query, TableEnum.DIAGNOSE_TABLE.getName());
    }

    /**
     * 根据时间段获取统计数据
     *
     * @param params
     * @return
     */
    public Long countByTime(Map<String, Object> params) {
        Query query = new Query();
        if(!StringUtils.isEmpty(params.get(TableEnum.TABLE_FIELD_REGIONCODE.getName())))
            query.addCriteria(Criteria.where(TableEnum.TABLE_FIELD_REGIONCODE.getName()).is(params.get(TableEnum.TABLE_FIELD_REGIONCODE.getName())));
        query.addCriteria(Criteria.where(TableEnum.TABLE_FIELD_ACTION_TYPE.getName()).is(params.get(TableEnum.TABLE_FIELD_ACTION_TYPE.getName())));
        query.addCriteria(Criteria.where(TableEnum.TABLE_SORT_ACTIONTIME_FORMAT.getName()).gte(params.get("beginTime")).lt(params.get("endTime")));
        return baseQuery.countByParams(query, TableEnum.DIAGNOSE_TABLE.getName());
    }

    /**
     * 使用or查询
     *
     * @param params
     * @return
     */
    public Long countByOr(Map<String, Object> params) {
        Query query = baseQuery.createOrQuery(params, TableEnum.TABLE_FIELD_JOINTO.getName());
        if (query==null) return 0L;
        return baseQuery.countByParams(query, TableEnum.DIAGNOSE_TABLE.getName());
    }

}
