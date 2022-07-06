package com.dealsdelta.scheduleme.data.dao;


import com.dealsdelta.scheduleme.data.models.DailyJobModel;
import com.dealsdelta.scheduleme.data.repo.JobRepo;
import com.dealsdelta.scheduleme.data.repo.Operation;
import com.dealsdelta.scheduleme.dtos.DailyJob;
import com.dealsdelta.scheduleme.dtos.IJob;
import com.dealsdelta.scheduleme.util.JsonUtil;
import org.bson.Document;
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
public class DailyJobDao {

    @Autowired
    private JobRepo<DailyJobModel> jobRepo;

    public DailyJob create(DailyJobModel dailyJobModel) {
        return jobRepo.create(dailyJobModel);
    }

    public DailyJob update(DailyJobModel dailyJobModel) {
        return jobRepo.update(dailyJobModel);
    }

    public void delete(DailyJobModel model) {
        jobRepo.remove(model);
    }

    public List<DailyJobModel> getDailyJobs() {
        return jobRepo.getAll(DailyJobModel.class);
    }

    public DailyJob getDailyJob(String jobId) {
        return jobRepo.getById(jobId, DailyJobModel.class);
    }

    public DailyJob getDailyJobByKey(String key, String value) {
        return jobRepo.getByKey(key, value, DailyJobModel.class);
    }

    public List<DailyJobModel> getAllBy(List<Operation> operations) {
        return jobRepo.getAll(operations, 0, Integer.MAX_VALUE, DailyJobModel.class);
    }

    public void updateMany(List<Operation> conditions, String key, Object value) {
        jobRepo.updateMany(conditions, key, value, DailyJobModel.class);
    }

    public long getJobCount() {
        return jobRepo.getCount(DailyJobModel.class);
    }
}