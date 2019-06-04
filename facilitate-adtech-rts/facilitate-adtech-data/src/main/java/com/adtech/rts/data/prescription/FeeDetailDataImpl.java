package com.adtech.rts.data.prescription;

import com.adtech.rts.data.base.BaseQuery;
import com.adtech.rts.model.entity.finance.FeeDetail;
import com.adtech.rts.model.enums.TableEnum;
import com.adtech.rts.model.util.SetQueryForParams;
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
public class FeeDetailDataImpl {

    @Autowired
    private BaseQuery baseQuery;

    /**
     * 根据条件分页
     *
     * @param params
     * @param pageNum
     * @return
     */
    public List<FeeDetail> findByParams(Map<String, Object> params, Integer pageNum, Integer pageSize) {
        Query query = SetQueryForParams.setQueryForMap(params);
        // 排序
        query.with(new Sort(Sort.Direction.DESC, TableEnum.TABLE_SORT_LASTUPDATETIME.getName()));
        List<FeeDetail> list = baseQuery.selectByPageParams(query, pageNum, pageSize, FeeDetail.class);
        return list;
    }


    /**
     * 详情查看
     *
     * @param params
     * @return
     */
    public FeeDetail findInfo(Map<String, Object> params) {
        Query query = SetQueryForParams.setQueryForMap(params);
        return baseQuery.selectOneByParams(query, FeeDetail.class);
    }


    /**
     * 获取总数
     *
     * @param params
     * @return
     */
    public Long countByParams(Map<String, Object> params) {
        Query query = SetQueryForParams.setQueryForMap(params);
        return baseQuery.countByParams(query, TableEnum.FEEDETAIL_TABLE.getName());
    }
}
