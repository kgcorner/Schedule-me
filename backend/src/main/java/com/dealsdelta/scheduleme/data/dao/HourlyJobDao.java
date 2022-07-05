package com.dealsdelta.scheduleme.data.dao;


import com.dealsdelta.scheduleme.data.models.HourlyJobModel;
import com.dealsdelta.scheduleme.data.models.MonthlyJobModel;
import com.dealsdelta.scheduleme.data.repo.JobRepo;
import com.dealsdelta.scheduleme.data.repo.Operation;
import com.dealsdelta.scheduleme.dtos.HourlyJob;
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
public class HourlyJobDao {

    @Autowired
    private JobRepo<HourlyJobModel> jobRepo;

    public HourlyJob create(HourlyJobModel HourlyJobModel) {
        return jobRepo.create(HourlyJobModel);
    }

    public HourlyJob update(HourlyJobModel HourlyJobModel) {
        return jobRepo.update(HourlyJobModel);
    }

    public void delete(HourlyJobModel model) {
        jobRepo.remove(model);
    }

    public List<HourlyJobModel> getHourlyJobs() {
        return jobRepo.getAll(HourlyJobModel.class);
    }

    public HourlyJob getHourlyJob(String jobId) {
        return jobRepo.getById(jobId, HourlyJobModel.class);
    }

    public HourlyJob getHourlyJobByKey(String key, String value) {
        return jobRepo.getByKey(key, value, HourlyJobModel.class);
    }

    public List<HourlyJobModel> getAllBy(List<Operation> operations) {
        return jobRepo.getAll(operations, 0, Integer.MAX_VALUE, HourlyJobModel.class);
    }
}