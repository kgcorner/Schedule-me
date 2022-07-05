package com.dealsdelta.scheduleme.data.repo;


import com.dealsdelta.scheduleme.data.models.DailyJobModel;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 25/06/22
 */
@Component
public class JobRepo<T extends Serializable> extends MongoRepository<T> {
    public JobRepo(MongoTemplate template) {
        this.template = template;
    }
}