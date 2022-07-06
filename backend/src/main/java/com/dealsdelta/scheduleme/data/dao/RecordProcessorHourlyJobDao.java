package com.dealsdelta.scheduleme.data.dao;


import com.dealsdelta.scheduleme.data.models.DailyJobModel;
import com.dealsdelta.scheduleme.data.models.RecordProcessorHourlyJobModel;
import com.dealsdelta.scheduleme.data.models.RecordProcessorJobModel;
import com.dealsdelta.scheduleme.data.repo.JobRepo;
import com.dealsdelta.scheduleme.data.repo.Operation;
import com.dealsdelta.scheduleme.dtos.RecordProcessorHourlyJob;
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
public class RecordProcessorHourlyJobDao {

    @Autowired
    private JobRepo<RecordProcessorHourlyJobModel> jobRepo;

    public RecordProcessorHourlyJob create(RecordProcessorHourlyJobModel RecordProcessorHourlyJobModel) {
        return jobRepo.create(RecordProcessorHourlyJobModel);
    }

    public RecordProcessorHourlyJob update(RecordProcessorHourlyJobModel RecordProcessorHourlyJobModel) {
        return jobRepo.update(RecordProcessorHourlyJobModel);
    }

    public void delete(RecordProcessorHourlyJobModel model) {
        jobRepo.remove(model);
    }

    public List<RecordProcessorHourlyJobModel> getRecordProcessorHourlyJobs() {
        return jobRepo.getAll(RecordProcessorHourlyJobModel.class);
    }

    public RecordProcessorHourlyJob getRecordProcessorHourlyJob(String jobId) {
        return jobRepo.getById(jobId, RecordProcessorHourlyJobModel.class);
    }

    public RecordProcessorHourlyJob getRecordProcessorHourlyJobByKey(String key, String value) {
        return jobRepo.getByKey(key, value, RecordProcessorHourlyJobModel.class);
    }

    public List<RecordProcessorHourlyJobModel> getAllBy(List<Operation> operations) {
        return jobRepo.getAll(operations, 0, Integer.MAX_VALUE, RecordProcessorHourlyJobModel.class);
    }

    public long getJobCount() {
        return jobRepo.getCount(RecordProcessorHourlyJobModel.class);
    }
}