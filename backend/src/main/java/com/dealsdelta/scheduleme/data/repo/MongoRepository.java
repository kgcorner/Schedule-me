package com.dealsdelta.scheduleme.data.repo;


import org.bson.Document;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 24/06/22
 */

public class MongoRepository<T extends Serializable> {

    class Order {
        private String name;
        private boolean isAscending;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isAscending() {
            return isAscending;
        }

        public void setAscending(boolean ascending) {
            isAscending = ascending;
        }
    }

    protected MongoTemplate template;

    public List<T> getAll(int maxCount, Class<T> type) {
        return getAll(0, maxCount, type);
    }

    
    public List<T> getAll(int page, int itemsCount, Class<T> type) {
        Pageable pageable = PageRequest.of(page, itemsCount);
        Query query = new Query().with(pageable);
        return template.find(query, type);
    }


    
    public List<T> getAll(List<Operation> conditions, int page, int itemPerPage, Class<T> model) {
        return getAll(conditions, page, itemPerPage, model, Collections.emptyList());
    }

    
    public List<T> getAll(List<Operation> conditions, int page, int itemPerPage, Class<T> model, List<Order> orderList) {
        Query query = new Query();
        Pageable pageable = PageRequest.of(page, itemPerPage);
        if(itemPerPage > 0)
            query.with(pageable);
        Criteria criteria = new Criteria();
        for(Operation operation : conditions) {
            switch (operation.getOperator()) {

                case GE:
                    if(operation.isOrWithPrevious()) {
                        criteria.orOperator(Criteria.where(operation.getName()).gte(operation.getValue()));
                    } else {
                        criteria.andOperator(Criteria.where(operation.getName()).gte(operation.getValue()));
                    }
                    break;
                case EQ:
                    if(operation.isOrWithPrevious()) {
                        criteria.orOperator(Criteria.where(operation.getName()).is(operation.getValue()));
                    } else {
                        criteria.andOperator(Criteria.where(operation.getName()).is(operation.getValue()));
                    }
                    break;
                case LE:
                    if(operation.isOrWithPrevious()) {
                        criteria.orOperator(Criteria.where(operation.getName()).lte(operation.getValue()));
                    } else {
                        criteria.andOperator(Criteria.where(operation.getName()).lte(operation.getValue()));
                    }
                    break;
                case GT:
                    if(operation.isOrWithPrevious()) {
                        criteria.orOperator(Criteria.where(operation.getName()).gt(operation.getValue()));
                    } else {
                        criteria.andOperator(Criteria.where(operation.getName()).gt(operation.getValue()));
                    }
                    break;
                case LT:
                    if(operation.isOrWithPrevious()) {
                        criteria.orOperator(Criteria.where(operation.getName()).lt(operation.getValue()));
                    } else {
                        criteria.andOperator(Criteria.where(operation.getName()).lt(operation.getValue()));
                    }
                    break;
                case LIKE:
                case IS_NOT_NULL:
                    throw new UnsupportedOperationException("Like operator is not supported for mongo yet");
                case IS_NULL:
                    criteria.andOperator(Criteria.where(operation.getName()).exists(false));
                    break;
            }
        }
        query.addCriteria(criteria);
        if(orderList != null && orderList.size() > 1) {
            List<Sort.Order> sortOrder = new ArrayList<>();
            for(Order order : orderList) {
                if(order.isAscending()) {
                    sortOrder.add(new Sort.Order(Sort.Direction.ASC, order.getName()));
                } else {
                    sortOrder.add(new Sort.Order(Sort.Direction.DESC, order.getName()));
                }
            }
            query.with(Sort.by(sortOrder));
        }
        return template.find(query, model);
    }

    public T getById(String id, Class<T> type) {
        return getByKey("id", id, type);
    }

    public List<T> getAll(Class<T> type) {
        return template.findAll(type);
    }
    public T getByKey(String key, String value, Class<T> type) {
        Query query = new Query();
        query.addCriteria(Criteria.where(key).is(value));
        return template.findOne(query, type);
    }

    
    public T create(T document) {
        document = template.insert(document);
        return  document;
    }

    
    public T update(T document) {
        document = template.save(document);
        return document;
    }

    public void updateMany(List<Operation> operations, String key, Object value, Class<T> type) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        for(Operation operation : operations) {
            switch (operation.getOperator()) {

                case GE:
                    if(operation.isOrWithPrevious()) {
                        criteria.orOperator(Criteria.where(operation.getName()).gte(operation.getValue()));
                    } else {
                        criteria.andOperator(Criteria.where(operation.getName()).gte(operation.getValue()));
                    }
                    break;
                case EQ:
                    if(operation.isOrWithPrevious()) {
                        criteria.orOperator(Criteria.where(operation.getName()).is(operation.getValue()));
                    } else {
                        criteria.andOperator(Criteria.where(operation.getName()).is(operation.getValue()));
                    }
                    break;
                case LE:
                    if(operation.isOrWithPrevious()) {
                        criteria.orOperator(Criteria.where(operation.getName()).lte(operation.getValue()));
                    } else {
                        criteria.andOperator(Criteria.where(operation.getName()).lte(operation.getValue()));
                    }
                    break;
                case GT:
                    if(operation.isOrWithPrevious()) {
                        criteria.orOperator(Criteria.where(operation.getName()).gt(operation.getValue()));
                    } else {
                        criteria.andOperator(Criteria.where(operation.getName()).gt(operation.getValue()));
                    }
                    break;
                case LT:
                    if(operation.isOrWithPrevious()) {
                        criteria.orOperator(Criteria.where(operation.getName()).lt(operation.getValue()));
                    } else {
                        criteria.andOperator(Criteria.where(operation.getName()).lt(operation.getValue()));
                    }
                    break;
                case LIKE:
                case IS_NOT_NULL:
                    throw new UnsupportedOperationException("Like operator is not supported for mongo yet");
                case IS_NULL:
                    criteria.andOperator(Criteria.where(operation.getName()).exists(false));
                    break;
            }
        }
        query.addCriteria(criteria);
        Update updateDefinition = new Update();
        updateDefinition.set(key, value);
        template.updateMulti(query, updateDefinition, type);
    }

    
    public void remove(T document) {
        template.remove(document);
    }


    public Object runSelectNativeQuery(String queryStr, String collectionName, int limit, int offset) {
        String command = "{\"find\":\"%s\",\"filter\":%s,\"limit\" : %s,\"skip\" : %s}";
        command = String.format(command, collectionName, queryStr, limit, offset);

        return template.executeCommand(command);
    }

    public Object runSelectNativeQuery(String queryStr) {
        Document bsonCmd = Document.parse(queryStr);
        Document document = template.executeCommand(bsonCmd);
        Document cursor = (Document) document.get("cursor");
        return cursor.get("firstBatch");
    }

    public long getCount(Class<T> type) {
        return template.count(new Query(), type);
    }
}