package com.dealsdelta.scheduleme.data.dao;


import com.dealsdelta.scheduleme.data.models.DailyJobModel;
import com.dealsdelta.scheduleme.data.models.MonthlyJobModel;
import com.dealsdelta.scheduleme.data.repo.JobRepo;
import com.dealsdelta.scheduleme.data.repo.Operation;
import com.dealsdelta.scheduleme.dtos.MonthlyJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 25/06/22
 */

@Repository
@Transactional
public class MonthlyJobDao {

    @Autowired
    private JobRepo<MonthlyJobModel> jobRepo;

    public MonthlyJob create(MonthlyJobModel MonthlyJobModel) {
        return jobRepo.create(MonthlyJobModel);
    }

    public MonthlyJob update(MonthlyJobModel MonthlyJobModel) {
        return jobRepo.update(MonthlyJobModel);
    }

    public void delete(MonthlyJobModel model) {
        jobRepo.remove(model);
    }

    public List<MonthlyJobModel> getMonthlyJobs() {
        return jobRepo.getAll(MonthlyJobModel.class);
    }

    public MonthlyJob getMonthlyJob(String jobId) {
        return jobRepo.getById(jobId, MonthlyJobModel.class);
    }

    public MonthlyJob getMonthlyJobByKey(String key, String value) {
        return jobRepo.getByKey(key, value, MonthlyJobModel.class);
    }

    public List<MonthlyJobModel> getAllBy(List<Operation> operations) {
        return jobRepo.getAll(operations, 0, Integer.MAX_VALUE, MonthlyJobModel.class);
    }

    public void updateMany(List<Operation> conditions, String key, Object value) {
        jobRepo.updateMany(conditions, key, value, MonthlyJobModel.class);
    }
}