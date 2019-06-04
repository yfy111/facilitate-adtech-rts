package com.adtech.rts.data.base;

import com.adtech.rts.model.enums.TableEnum;
import com.adtech.rts.model.util.EntityConvertUtil;
import com.google.common.collect.Lists;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class BaseQuery {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 根据条件查询分页信息
     *
     * @return
     */
    public <T> List<T> selectByPageParams(Query query, Integer pageNum, Integer pageSize, Class<T> clazz) {
        if (StringUtils.isEmpty(pageNum) || pageNum == 1) pageNum = 0;
        if (StringUtils.isEmpty(pageSize) || pageSize > 30) pageSize = 10;
        PageRequest pageable = PageRequest.of(pageNum, pageSize);
        query.with(pageable);
        setIsDeleted(query);
        List<T> list = mongoTemplate.find(query, clazz);
        return list;
    }


    /**
     * 获取全量列表
     *
     * @param query
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> List<T> selectByParams(Query query, Class<T> clazz) {
        setIsDeleted(query);
        log.info("查询条件：{}", query);
        return mongoTemplate.find(query, clazz);
    }

    /**
     * 获取全量列表
     *
     * @param query
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> List<T> selectByParams(Document query, Class<T> clazz, String collectionName) {
        setIsDeleted(query);
        log.info("查询条件：{}", query);
        //batchSize 设置每次取出数据量
        FindIterable<Document> dbCursor = mongoTemplate.getCollection(collectionName).find(query).batchSize(50000);
        MongoCursor<Document> cursor = dbCursor.iterator();
        return setList(cursor, clazz);
    }



    /**
     * 获取全量列表
     *
     * @param query
     * @return
     */
    public Long countByParams(BasicDBObject query, String collectionName) {
        setIsDeleted(query);
        log.info("查询条件：{}", query);
        return mongoTemplate.getCollection(collectionName).count(query);
    }


    /**
     * 设置list的值
     *
     * @param <T>
     * @return
     */
    public <T> List<T> setList(MongoCursor<Document> cursor, Class<T> tClass) {
        List<T> list = Lists.newArrayList();
        Document doc = null;
        while (cursor.hasNext()) {
            try {
                doc = cursor.next();
                T t = EntityConvertUtil.transMap2Bean(doc, tClass.newInstance());
                list.add(t);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * 获取全量列表
     *
     * @param query
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> List<T> selectByParams(BasicDBObject query, Class<T> clazz, String collectionName) {
        setIsDeleted(query);
        log.info("BasicDBObject查询表{},条件：{}", collectionName,query);
        //batchSize 设置每次取出数据量
        FindIterable<Document> dbCursor = mongoTemplate.getCollection(collectionName).find(query).batchSize(50000);
        MongoCursor<Document> cursor = dbCursor.iterator();
        return setList(cursor, clazz);
    }

    /**
     * 获取全量列表
     * 排序
     *
     * @param query
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> List<T> selectSortByParams(Document query, Class<T> clazz, String collectionName, Document sort) {
        List<T> list = Lists.newArrayList();
        setIsDeleted(query);
        //batchSize 设置每次取出数据量
        FindIterable<Document> dbCursor = mongoTemplate.getCollection(collectionName).find(query).sort(sort).batchSize(50000);
        setList(dbCursor.iterator(), list, clazz);
        return list;
    }


    /**
     * 获取全量列表
     *
     * @param query
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> List<T> selectDocByParams(Document query, Class<T> clazz, String collectionName) {
        List<T> list = Lists.newArrayList();
        setIsDeleted(query);
        //batchSize 设置每次取出数据量
        FindIterable<Document> dbCursor = mongoTemplate.getCollection(collectionName).find(query).batchSize(50000);
        setList(dbCursor.iterator(), list, clazz);
        return list;
    }


    /**
     * 获取单条数据
     *
     * @param query
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T selectOneByParams(Query query, Class<T> clazz) {
        setIsDeleted(query);
        return mongoTemplate.findOne(query, clazz);
    }

    /**
     * 获取总数
     *
     * @param query
     * @return
     */
    public Long countByParams(Query query, String tableName) {
        setIsDeleted(query);
        return mongoTemplate.count(query, tableName);
    }


    /**
     * list 反射设值
     * @param cursor
     * @param list
     * @param clazz
     * @param <T>
     */
    private <T> void setList(MongoCursor<Document> cursor, List<T> list, Class<T> clazz) {
        while (cursor.hasNext()) {
            try {
                T t = EntityConvertUtil.transMap2Bean(cursor.next(), clazz.newInstance());
                list.add(t);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建or条件
     *
     * @param params
     * @param listName
     * @return BasicDBList
     */
    public BasicDBList createOrValues(Map<String, Object> params, String listName) {
        BasicDBList values = new BasicDBList();
        List<String> list = (List) params.get(listName);
        if (list == null || list.isEmpty()) return null;
        for (String s : list) {
            Document query = new Document();
            query.put(listName, s);
            values.add(query);
        }
        return values;
    }

    /**
     * 创建OR条件
     *
     * @param params
     * @param name
     * @return query
     */
    public Query createOrQuery(Map<String, Object> params, String name) {
        Query query = new Query();
        List<String> list = (List) params.get(name);
        if (list == null || list.isEmpty()) return null;
        int size = list.size();
        Criteria[] c = new Criteria[size];
        for (int i = 0; i < size; i++) {
            c[i] = Criteria.where(name).is(list.get(i));
        }
        query.addCriteria(new Criteria().orOperator(c));
        return query;
    }

    /**
     * 排除设置
     * @param query
     */
    private void setIsDeleted(Document query){
        Document ne = new Document();
        ne.put("$ne",false);
        query.put(TableEnum.TABLE_FIELD_ISDELETED.getName(),ne);
    }

    /**
     * 排除设置
     * @param query
     */
    private void setIsDeleted(BasicDBObject query){
        Document ne = new Document();
        ne.put("$ne",false);
        query.put(TableEnum.TABLE_FIELD_ISDELETED.getName(),ne);
    }

    /**
     * 排除设置
     * @param query
     */
    private void setIsDeleted(Query query){
        query.addCriteria(Criteria.where(TableEnum.TABLE_FIELD_ISDELETED.getName()).ne(false));
    }

}
