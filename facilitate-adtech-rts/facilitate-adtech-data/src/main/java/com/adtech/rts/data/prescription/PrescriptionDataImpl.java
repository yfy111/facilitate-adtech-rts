package com.adtech.rts.data.prescription;

import com.adtech.rts.data.base.BaseQuery;
import com.adtech.rts.model.entity.Prescription;
import com.adtech.rts.model.entity.finance.FeeOverview;
import com.adtech.rts.model.enums.TableEnum;
import com.adtech.rts.model.util.SetQueryForParams;
import com.google.common.collect.Lists;
import com.mongodb.BasicDBList;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service
public class PrescriptionDataImpl {

    @Autowired
    private BaseQuery baseQuery;

    /**
     * 根据条件分页
     *
     * @param params
     * @param pageNum
     * @return
     */
    public List<Prescription> findByParams(Map<String, Object> params, Integer pageNum, Integer pageSize) {
        Query query = SetQueryForParams.setQueryForMap(params);
        // 排序
        query.with(new Sort(Sort.Direction.DESC, TableEnum.TABLE_SORT_LASTUPDATETIME.getName()));
        List<Prescription> list = baseQuery.selectByPageParams(query, pageNum, pageSize, Prescription.class);
        return list;
    }


    /**
     * 根据条件分页
     *
     * @param params
     * @return
     */
    public List<Prescription> findByParams(Map<String, Object> params) {
        Document query= SetQueryForParams.setDocumentQueryForMap(params);
        // 排序
        Document sort = new Document();
        sort.put(TableEnum.TABLE_SORT_LASTUPDATETIME.getName(),-1);
        List<Prescription> list = baseQuery.selectByParams(query, Prescription.class,TableEnum.PRESCRIPTION_TABLE.getName());
        return list;
    }


    /**
     * 根据条件查询全量list
     *
     * @param params
     * @return
     */
    public List<Prescription> findByOrParams(Map<String, Object> params, String listName) {
        Document or = new Document();
        BasicDBList values = baseQuery.createOrValues(params,listName);
        if(values==null) return Lists.newArrayList();
        or.put("$or",values);
        // 排序
        Document sort = new Document();
        sort.put(TableEnum.TABLE_SORT_LASTUPDATETIME.getName(),-1);
        return baseQuery.selectSortByParams(or, Prescription.class,TableEnum.PRESCRIPTION_TABLE.getName(),sort);
    }

    /**
     * 详情查看
     *
     * @param params
     * @return
     */
    public Prescription findInfo(Map<String, Object> params) {
        Query query = SetQueryForParams.setQueryForMap(params);
        return baseQuery.selectOneByParams(query, Prescription.class);
    }


    /**
     * 获取总数
     *
     * @param params
     * @return
     */
    public Long countByParams(Map<String, Object> params) {
        Query query = SetQueryForParams.setQueryForMap(params);
        return baseQuery.countByParams(query, TableEnum.PRESCRIPTION_TABLE.getName());
    }

    /**
     * 获取总数
     *
     * @param params
     * @return
     */
    public Long countByOrParams(Map<String, Object> params) {
        Query query = baseQuery.createOrQuery(params,TableEnum.TABLE_FIELD_JOINTO.getName());
        return baseQuery.countByParams(query, TableEnum.PRESCRIPTION_TABLE.getName());
    }

}
