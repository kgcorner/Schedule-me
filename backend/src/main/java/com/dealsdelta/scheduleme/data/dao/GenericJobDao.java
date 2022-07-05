package com.dealsdelta.scheduleme.data.dao;


import com.dealsdelta.scheduleme.data.models.DailyJobModel;
import com.dealsdelta.scheduleme.data.models.GenericJobModel;
import com.dealsdelta.scheduleme.data.repo.JobRepo;
import com.dealsdelta.scheduleme.data.repo.Operation;
import com.dealsdelta.scheduleme.dtos.GenericJob;
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
public class GenericJobDao {

    @Autowired
    private JobRepo<GenericJobModel> jobRepo;

    public GenericJob create(GenericJobModel GenericJobModel) {
        return jobRepo.create(GenericJobModel);
    }

    public GenericJob update(GenericJobModel GenericJobModel) {
        return jobRepo.update(GenericJobModel);
    }

    public void delete(GenericJobModel model) {
        jobRepo.remove(model);
    }

    public List<GenericJobModel> getGenericJobs() {
        return jobRepo.getAll(GenericJobModel.class);
    }

    public GenericJob getGenericJob(String jobId) {
        return jobRepo.getById(jobId, GenericJobModel.class);
    }

    public GenericJob getGenericJobByKey(String key, String value) {
        return jobRepo.getByKey(key, value, GenericJobModel.class);
    }

    public List<GenericJobModel> getAllBy(List<Operation> operations) {
        return jobRepo.getAll(operations, 0, Integer.MAX_VALUE, GenericJobModel.class);
    }
}