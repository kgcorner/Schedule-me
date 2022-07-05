package com.dealsdelta.scheduleme.data.dao;


import com.dealsdelta.scheduleme.data.models.DailyJobModel;
import com.dealsdelta.scheduleme.data.models.RunningJobModel;
import com.dealsdelta.scheduleme.data.repo.JobRepo;
import com.dealsdelta.scheduleme.data.repo.Operation;
import com.dealsdelta.scheduleme.dtos.RunningJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 26/06/22
 */
@Repository
@Transactional
public class RunningJobDao  {
    @Autowired
    private JobRepo<RunningJobModel> jobRepo;

    public RunningJob create(RunningJobModel RunningJobModel) {
        return jobRepo.create(RunningJobModel);
    }

    public RunningJob update(RunningJobModel RunningJobModel) {
        return jobRepo.update(RunningJobModel);
    }

    public void delete(RunningJobModel model) {
        jobRepo.remove(model);
    }

    public List<RunningJobModel> getRunningJobs() {
        return jobRepo.getAll(RunningJobModel.class);
    }

    public RunningJob getRunningJob(String jobId) {
        return jobRepo.getById(jobId, RunningJobModel.class);
    }

    public RunningJob getRunningJobByKey(String key, String value) {
        return jobRepo.getByKey(key, value, RunningJobModel.class);
    }

    public List<RunningJobModel> getAllBy(List<Operation> operations) {
        return jobRepo.getAll(operations, 0, Integer.MAX_VALUE, RunningJobModel.class);
    }
}