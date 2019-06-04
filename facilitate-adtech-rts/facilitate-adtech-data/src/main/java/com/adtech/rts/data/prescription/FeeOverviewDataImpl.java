package com.adtech.rts.data.prescription;

import com.adtech.rts.data.base.BaseQuery;
import com.adtech.rts.model.entity.MedicalAction;
import com.adtech.rts.model.entity.Prescription;
import com.adtech.rts.model.entity.finance.FeeOverview;
import com.adtech.rts.model.enums.TableEnum;
import com.adtech.rts.model.util.SetQueryForParams;
import com.google.common.collect.Lists;
import com.mongodb.BasicDBList;
import org.bson.Document;
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
public class FeeOverviewDataImpl {

    @Autowired
    private BaseQuery baseQuery;

    /**
     * 根据条件分页
     *
     * @param params
     * @param pageNum
     * @return
     */
    public List<FeeOverview> findByParams(Map<String, Object> params, Integer pageNum, Integer pageSize) {
        Query query = SetQueryForParams.setQueryForMap(params);
        // 排序
        query.with(new Sort(Sort.Direction.DESC, TableEnum.TABLE_SORT_LASTUPDATETIME.getName()));
        List<FeeOverview> list = baseQuery.selectByPageParams(query, pageNum, pageSize, FeeOverview.class);
        return list;
    }


    /**
     * 根据条件查询全量list
     *
     * @param params
     * @return
     */
    public List<FeeOverview> findByTimeOrParams(Map<String, Object> params, String listName) {
        Document or = new Document();
        BasicDBList  values = baseQuery.createOrValues(params,listName);
        if(values==null) return Lists.newArrayList();
        or.put("$or",values);
        // 排序
        Document sort = new Document();
        sort.put(TableEnum.TABLE_SORT_LASTUPDATETIME.getName(),-1);
        return baseQuery.selectSortByParams(or, FeeOverview.class,TableEnum.FEEOVERVIEW_TABLE.getName(),sort);
    }


    /**
     * 详情查看
     *
     * @param params
     * @return
     */
    public FeeOverview findInfo(Map<String, Object> params) {
        Query query = SetQueryForParams.setQueryForMap(params);
        return baseQuery.selectOneByParams(query, FeeOverview.class);
    }


    /**
     * 获取总数
     *
     * @param params
     * @return
     */
    public Long countByParams(Map<String, Object> params) {
        Query query = SetQueryForParams.setQueryForMap(params);
        return baseQuery.countByParams(query, TableEnum.FEEOVERVIEW_TABLE.getName());
    }

    /**
     * 根据时间段获取统计数据
     *
     * @param params
     * @return
     */
    public Long countByTime(Map<String, Object> params) {
        Query query = new Query();
        query.addCriteria(Criteria.where(TableEnum.TABLE_SORT_ACTIONTIME_FORMAT.getName()).gte(params.get("beginTime")).lte(params.get("endTime")));
        return baseQuery.countByParams(query, TableEnum.FEEOVERVIEW_TABLE.getName());
    }
}
