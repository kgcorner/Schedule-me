package com.dealsdelta.scheduleme.data.dao;


import com.dealsdelta.scheduleme.data.models.RecordProcessorDailyJobModel;
import com.dealsdelta.scheduleme.data.models.RecordProcessorJobModel;
import com.dealsdelta.scheduleme.data.repo.JobRepo;
import com.dealsdelta.scheduleme.data.repo.Operation;
import com.dealsdelta.scheduleme.dtos.RecordProcessorJob;
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
public class RecordProcessorJobDao {

    @Autowired
    private JobRepo<RecordProcessorJobModel> jobRepo;

    public RecordProcessorJob create(RecordProcessorJobModel RecordProcessorJobModel) {
        return jobRepo.create(RecordProcessorJobModel);
    }

    public RecordProcessorJob update(RecordProcessorJobModel RecordProcessorJobModel) {
        return jobRepo.update(RecordProcessorJobModel);
    }

    public void delete(RecordProcessorJobModel model) {
        jobRepo.remove(model);
    }

    public List<RecordProcessorJobModel> getRecordProcessorJobs() {
        return jobRepo.getAll(RecordProcessorJobModel.class);
    }

    public RecordProcessorJob getRecordProcessorJob(String jobId) {
        return jobRepo.getById(jobId, RecordProcessorJobModel.class);
    }

    public RecordProcessorJob getRecordProcessorJobByKey(String key, String value) {
        return jobRepo.getByKey(key, value, RecordProcessorJobModel.class);
    }

    public List<RecordProcessorJobModel> getAllBy(List<Operation> operations) {
        return jobRepo.getAll(operations, 0, Integer.MAX_VALUE, RecordProcessorJobModel.class);
    }
}