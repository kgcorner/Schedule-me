package com.dealsdelta.scheduleme.data.dao;


import com.dealsdelta.scheduleme.data.repo.JobRepo;
import com.dealsdelta.scheduleme.data.repo.Operation;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 23/08/22
 */

public class GenericDao<T extends Serializable> {
    @Autowired
    protected JobRepo<T> jobRepo;

    public T create(T dailyJobModel) {
        return jobRepo.create(dailyJobModel);
    }

    public T update(T dailyJobModel) {
        return jobRepo.update(dailyJobModel);
    }

    public void delete(T model) {
        jobRepo.remove(model);
    }

    public List<T> getAll(Class<T> model) {
        return jobRepo.getAll(model);
    }

    public T get(String jobId, Class<T> model) {
        return jobRepo.getById(jobId, model);
    }

    public T getByKey(String key, String value, Class<T> model) {
        return jobRepo.getByKey(key, value, model);
    }

    public List<T> getAllByKey(List<Operation> operations, Class<T> model) {
        return jobRepo.getAll(operations, 0, Integer.MAX_VALUE, model);
    }

    public void updateMany(List<Operation> conditions, String key, Object value, Class<T> model) {
        jobRepo.updateMany(conditions, key, value, model);
    }

    public long getCount(Class<T> model) {
        return jobRepo.getCount(model);
    }
}