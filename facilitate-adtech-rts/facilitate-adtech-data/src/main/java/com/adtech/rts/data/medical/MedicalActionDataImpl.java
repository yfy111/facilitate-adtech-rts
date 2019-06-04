package com.adtech.rts.data.medical;

import com.adtech.rts.data.base.BaseQuery;
import com.adtech.rts.model.entity.MedicalAction;
import com.adtech.rts.model.enums.EMedicalActionType;
import com.adtech.rts.model.enums.TableEnum;
import com.adtech.rts.model.util.SetQueryForParams;
import com.google.common.collect.Lists;
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
public class MedicalActionDataImpl {

    @Autowired
    private BaseQuery baseQuery;

    /**
     * 根据条件分页
     *
     * @param params
     * @param pageNum
     * @return
     */
    public List<MedicalAction> findByParams(Map<String, Object> params, Integer pageNum, Integer pageSize) {
        Query query = SetQueryForParams.setQueryForMap(params);
        if(EMedicalActionType.hospitalization.name().equals(params.get(TableEnum.TABLE_FIELD_ACTION_TYPE.getName()))){
            //如果是住院
            query.addCriteria(Criteria.where("dataBase").ne(null));
        }
        query.with(new Sort(Sort.Direction.DESC, TableEnum.TABLE_SORT_ACTIONTIME_FORMAT.getName()));
        // 排序
        List<MedicalAction> list = baseQuery.selectByPageParams(query, pageNum, pageSize, MedicalAction.class);
        return list;
    }

    /**
     * 根据条件查询list
     *
     * @param params
     * @return
     */
    public List<MedicalAction> findByParams(Map<String, Object> params) {
        Document query = SetQueryForParams.setDocumentQueryForMap(params);
        List<MedicalAction> list = baseQuery.selectByParams(query, MedicalAction.class, TableEnum.MEDICALACTION_TABLE.getName());
        return list;
    }

    /**
     * 根据条件createDate大于查询列表
     * 增量
     *
     * @param params
     * @return
     */
    public List<MedicalAction> selectByCreateDateParams(Map<String, Object> params, Integer pageNum, Integer pageSize) {
        Query query = new Query();
        query.with(new Sort(Sort.Direction.DESC, TableEnum.TABLE_SORT_LASTUPDATETIME.getName()));
        query.addCriteria(Criteria.where(TableEnum.TABLE_SORT_CREATEDATE.getName())
                .gt(params.get(TableEnum.TABLE_SORT_CREATEDATE.getName())));
        List<MedicalAction> list = baseQuery.selectByPageParams(query, pageNum, pageSize, MedicalAction.class);
        return list;
    }

//    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
//
//    public static Date formatD(String dateStr) {
//        return formatD(dateStr, DATE_TIME_PATTERN);
//    }
//
//    public static Date formatD(String dateStr, String format) {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
//        Date ret = null;
//        try {
//            ret = simpleDateFormat.parse(dateStr);
//        } catch (ParseException e) {
//            //
//        }
//        return ret;
//    }

//    public static Date dateToISODate(String dateStr) {
//        //T代表后面跟着时间，Z代表UTC统一时间
//        Date date = formatD(dateStr);
//        SimpleDateFormat format =
//                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//        format.setCalendar(new GregorianCalendar(new SimpleTimeZone(0, "GMT")));
//        String isoDate = format.format(date);
//        try {
//            return format.parse(isoDate);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }


    /**
     * 根据条件查询list
     *
     * @param params
     * @return
     */
    public List<MedicalAction> findByTimeParams(Map<String, Object> params) {

        BasicDBObject obj = new BasicDBObject();
        BasicDBObject objl = new BasicDBObject();
//        obj.put(TableEnum.TABLE_FIELD_ACTION_TYPE.getName(), params.get(TableEnum.TABLE_FIELD_ACTION_TYPE.getName()));
        objl.put("$gt", params.get("doorBeginTime"));
        objl.put("$lt",params.get("endTime"));
        obj.put(TableEnum.TABLE_SORT_ACTIONTIME_FORMAT.getName(), objl);
        if(!StringUtils.isEmpty(params.get(TableEnum.TABLE_FIELD_REGIONCODE.getName())))
            obj.put(TableEnum.TABLE_FIELD_REGIONCODE.getName(), params.get(TableEnum.TABLE_FIELD_REGIONCODE.getName()));
        List<MedicalAction> list = baseQuery.selectByParams(obj, MedicalAction.class, TableEnum.MEDICALACTION_TABLE.getName());


//        BasicDBObject query = new BasicDBObject();
//        query.put(TableEnum.TABLE_FIELD_ACTION_TYPE.getName(),params.get(TableEnum.TABLE_FIELD_ACTION_TYPE.getName()));
//        Document q1 = new Document();
//        q1.put("$gt",params.get("beginTime"));
//        q1.put("$lt",params.get("endTime"));
//        query.put(TableEnum.TABLE_SORT_ACTIONTIME_FORMAT.getName(),q1);
//        List<MedicalAction> list = baseQuery.selectByParams(query, MedicalAction.class,TableEnum.MEDICALACTION_TABLE.getName());


//        Query query1 = new Query();
//        query1.addCriteria(Criteria.where(TableEnum.TABLE_FIELD_ACTION_TYPE.getName()).is(params.get(TableEnum.TABLE_FIELD_ACTION_TYPE.getName())));
//        query1.addCriteria(Criteria.where(TableEnum.TABLE_SORT_ACTIONTIME_FORMAT.getName()).gt(dateToISODate(DateUtil.formatDateTime((Date)params.get("beginTime")))));
//        List<MedicalAction> list = baseQuery.selectByParams(query1,MedicalAction.class);
        return list;
    }


    /**
     * 根据条件查询全量list
     *
     * @param params
     * @return
     */
    public List<MedicalAction> findByOrParams(Map<String, Object> params, String listName) {
        Document or = new Document();
        BasicDBList values = baseQuery.createOrValues(params,listName);
        if(values==null) return Lists.newArrayList();
        or.put("$or", values);
//        Document ne = new Document();
//        ne.put("$ne",null);
//        or.put("dataBase",ne);
        // 排序
        Document sort = new Document();
        sort.put(TableEnum.TABLE_SORT_LASTUPDATETIME.getName(), -1);
        return baseQuery.selectSortByParams(or, MedicalAction.class, TableEnum.MEDICALACTION_TABLE.getName(), sort);
    }


    /**
     * 详情查看
     *
     * @param params
     * @return
     */
    public MedicalAction findInfo(Map<String, Object> params) {
        Query query = SetQueryForParams.setQueryForMap(params);

        return baseQuery.selectOneByParams(query, MedicalAction.class);
    }

    /**
     * 获取总数
     *
     * @param params
     * @return
     */
    public Long countByParams(Map<String, Object> params) {
        Query query = SetQueryForParams.setQueryForMap(params);
        return baseQuery.countByParams(query, TableEnum.MEDICALACTION_TABLE.getName());
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
        return baseQuery.countByParams(query, TableEnum.MEDICALACTION_TABLE.getName());
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
        return baseQuery.countByParams(query, TableEnum.MEDICALACTION_TABLE.getName());
    }

}
