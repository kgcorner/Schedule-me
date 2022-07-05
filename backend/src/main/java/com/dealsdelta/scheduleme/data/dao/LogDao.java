package com.dealsdelta.scheduleme.data.dao;


import com.dealsdelta.scheduleme.data.models.LogModel;
import com.dealsdelta.scheduleme.data.repo.JobRepo;
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
public class LogDao {

    @Autowired
    private JobRepo<LogModel> jobRepo;

    public LogModel create(LogModel LogModel) {
        return jobRepo.create(LogModel);
    }

    public LogModel update(LogModel LogModel) {
        return jobRepo.update(LogModel);
    }

    public void delete(LogModel model) {
        jobRepo.remove(model);
    }

    public List<LogModel> getLogModels() {
        return jobRepo.getAll(LogModel.class);
    }

    public LogModel getLogModel(String jobId) {
        return jobRepo.getById(jobId, LogModel.class);
    }

    public LogModel getLogModelByKey(String key, String value) {
        return jobRepo.getByKey(key, value, LogModel.class);
    }
}