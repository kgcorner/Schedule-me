package com.dealsdelta.scheduleme.data.dao;


import com.dealsdelta.scheduleme.data.models.DailyJobModel;
import com.dealsdelta.scheduleme.data.models.RecordProcessorHourlyJobModel;
import com.dealsdelta.scheduleme.data.models.RecordProcessorMonthlyJobModel;
import com.dealsdelta.scheduleme.data.repo.JobRepo;
import com.dealsdelta.scheduleme.data.repo.Operation;
import com.dealsdelta.scheduleme.dtos.RecordProcessorMonthlyJob;
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
public class RecordProcessorMonthlyJobDao {

    @Autowired
    private JobRepo<RecordProcessorMonthlyJobModel> jobRepo;

    public RecordProcessorMonthlyJob create(RecordProcessorMonthlyJobModel RecordProcessorMonthlyJobModel) {
        return jobRepo.create(RecordProcessorMonthlyJobModel);
    }

    public RecordProcessorMonthlyJob update(RecordProcessorMonthlyJobModel RecordProcessorMonthlyJobModel) {
        return jobRepo.update(RecordProcessorMonthlyJobModel);
    }

    public void delete(RecordProcessorMonthlyJobModel model) {
        jobRepo.remove(model);
    }

    public List<RecordProcessorMonthlyJobModel> getRecordProcessorMonthlyJobs() {
        return jobRepo.getAll(RecordProcessorMonthlyJobModel.class);
    }

    public RecordProcessorMonthlyJob getRecordProcessorMonthlyJob(String jobId) {
        return jobRepo.getById(jobId, RecordProcessorMonthlyJobModel.class);
    }

    public RecordProcessorMonthlyJob getRecordProcessorMonthlyJobByKey(String key, String value) {
        return jobRepo.getByKey(key, value, RecordProcessorMonthlyJobModel.class);
    }

    public List<RecordProcessorMonthlyJobModel> getAllBy(List<Operation> operations) {
        return jobRepo.getAll(operations, 0, Integer.MAX_VALUE, RecordProcessorMonthlyJobModel.class);
    }

    public long getJobCount() {
        return jobRepo.getCount(RecordProcessorMonthlyJobModel.class);
    }
}