package com.adtech.rts.data.emritem;

import com.adtech.rts.data.base.BaseQuery;
import com.adtech.rts.model.entity.EMRItem;
import com.adtech.rts.model.enums.TableEnum;
import com.adtech.rts.model.util.SetQueryForParams;
import com.google.common.collect.Lists;
import com.mongodb.BasicDBList;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EMRItemDataImpl {

    @Autowired
    private BaseQuery baseQuery;

    /**
     * 根据条件查询分页信息
     *
     * @return
     */
    public List<EMRItem> selectByParams(Map<String, Object> params, Integer pageNum, Integer pageSize) {
        Query query = SetQueryForParams.setQueryForMap(params);
        // 排序
        query.with(new Sort(Sort.Direction.DESC, TableEnum.TABLE_SORT_LASTUPDATETIME.getName()));
        List<EMRItem> list = baseQuery.selectByPageParams(query, pageNum, pageSize,EMRItem.class);
        return list;
    }

    /**
     * 根据条件全等查询列表
     *
     * @param params
     * @return
     */
    public List<EMRItem> selectByParams(Map<String, Object> params) {
        Document query = SetQueryForParams.setDocumentQueryForMap(params);
        List<EMRItem> list = baseQuery.selectByParams(query, EMRItem.class, TableEnum.EMRITEM_TABLE.getName());
        return list;
    }


    /**
     * 根据条件createDate大于查询列表
     * 增量
     *
     * @param params
     * @return
     */
    public List<EMRItem> selectByCreateDateParams(Map<String, Object> params) {
        Document query = SetQueryForParams.setDocumentQueryForMap(params);
        Document gt = new Document();
        gt.put("$gt", params.get(TableEnum.TABLE_SORT_CREATEDATE.getName()));
        query.put(TableEnum.TABLE_SORT_CREATEDATE.getName(), gt);
        List<EMRItem> list = baseQuery.selectByParams(query, EMRItem.class, TableEnum.EMRITEM_TABLE.getName());
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
        return baseQuery.countByParams(query, TableEnum.EMRITEM_TABLE.getName());
    }


    /**
     * 通过otherIdentities 进行查询
     *
     * @param params
     * @return
     */
    public List<EMRItem> getListByIdentities(Map<String, Object> params) {
        Document query = SetQueryForParams.setDocumentQueryForMap(params);
        List<EMRItem> list = baseQuery.selectByParams(query, EMRItem.class, TableEnum.EMRITEM_TABLE.getName());
        return list;
    }

    /**
     * 通过otherIdentities 进行or查询
     *
     * @param params
     * @return
     */
    public List<EMRItem> getListByIdentitiesOr(Map<String, Object> params,String listName) {
        Document or = new Document();
        BasicDBList values = baseQuery.createOrValues(params,listName);
        if(values==null) return Lists.newArrayList();
        or.put(TableEnum.TABLE_FIELD_REGIONCODE.getName(),params.get(TableEnum.TABLE_FIELD_REGIONCODE.getName()));
        or.put(TableEnum.TABLE_FIELD_IORGANIZATIONCODE.getName(),params.get(TableEnum.TABLE_FIELD_IORGANIZATIONCODE.getName()));
        or.put("$or", values);
        List<EMRItem> lists = baseQuery.selectByParams(or,EMRItem.class, TableEnum.EMRITEM_TABLE.getName());
        return lists;
    }
}
