package com.dealsdelta.scheduleme.data.dao;


import com.dealsdelta.scheduleme.data.models.MonthlyJobModel;
import com.dealsdelta.scheduleme.data.models.RecordProcessorDailyJobModel;
import com.dealsdelta.scheduleme.data.repo.JobRepo;
import com.dealsdelta.scheduleme.data.repo.Operation;
import com.dealsdelta.scheduleme.dtos.RecordProcessorDailyJob;
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
public class RecordProcessorDailyJobDao {

    @Autowired
    private JobRepo<RecordProcessorDailyJobModel> jobRepo;

    public RecordProcessorDailyJob create(RecordProcessorDailyJobModel RecordProcessorDailyJobModel) {
        return jobRepo.create(RecordProcessorDailyJobModel);
    }

    public RecordProcessorDailyJob update(RecordProcessorDailyJobModel RecordProcessorDailyJobModel) {
        return jobRepo.update(RecordProcessorDailyJobModel);
    }

    public void delete(RecordProcessorDailyJobModel model) {
        jobRepo.remove(model);
    }

    public List<RecordProcessorDailyJobModel> getRecordProcessorDailyJobs() {
        return jobRepo.getAll(RecordProcessorDailyJobModel.class);
    }

    public RecordProcessorDailyJob getRecordProcessorDailyJob(String jobId) {
        return jobRepo.getById(jobId, RecordProcessorDailyJobModel.class);
    }

    public RecordProcessorDailyJob getRecordProcessorDailyJobByKey(String key, String value) {
        return jobRepo.getByKey(key, value, RecordProcessorDailyJobModel.class);
    }

    public List<RecordProcessorDailyJobModel> getAllBy(List<Operation> operations) {
        return jobRepo.getAll(operations, 0, Integer.MAX_VALUE, RecordProcessorDailyJobModel.class);
    }
}