package com.adtech.rts.data.prescription;

import com.adtech.rts.data.base.BaseQuery;
import com.adtech.rts.model.entity.PrescriptionDetail;
import com.adtech.rts.model.enums.TableEnum;
import com.adtech.rts.model.util.SetQueryForParams;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service
public class PrescriptionDetailDataImpl {

    @Autowired
    private BaseQuery baseQuery;

    /**
     * 根据条件分页
     * @param params
     * @param pageNum
     * @return
     */
    public List<PrescriptionDetail> findByParams(Map<String,Object> params, Integer pageNum,Integer pageSize){
        Query query= SetQueryForParams.setQueryForMap(params);
        // 排序
        query.with(new Sort(Sort.Direction.DESC, TableEnum.TABLE_SORT_LASTUPDATETIME.getName()));
        List<PrescriptionDetail> list = baseQuery.selectByPageParams(query,pageNum,pageSize,PrescriptionDetail.class);
        return list;
    }


    /**
     * 根据条件查询所有列表
     * @param params
     * @return
     */
    public List<PrescriptionDetail> findByParams(Map<String,Object> params){
        Document query= SetQueryForParams.setDocumentQueryForMap(params);
        // 排序
        Document sort = new Document();
        sort.put(TableEnum.TABLE_SORT_LASTUPDATETIME.getName(),-1);
        List<PrescriptionDetail> list = baseQuery.selectByParams(query,PrescriptionDetail.class,TableEnum.PRESCRIPTIONDETAIL_TABLE.getName());
        return list;
    }


    /**
     * 详情查看
     * @param params
     * @return
     */
    public PrescriptionDetail findInfo(Map<String,Object> params){
        Query query = SetQueryForParams.setQueryForMap(params);
        return baseQuery.selectOneByParams(query,PrescriptionDetail.class);
    }


    /**
     * 获取总数
     * @param params
     * @return
     */
    public Long countByParams(Map<String,Object> params){
        Query query= SetQueryForParams.setQueryForMap(params);
        return baseQuery.countByParams(query, TableEnum.PRESCRIPTIONDETAIL_TABLE.getName());
    }
}
