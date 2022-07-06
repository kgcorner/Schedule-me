package com.dealsdelta.scheduleme.services;


import com.dealsdelta.scheduleme.data.dao.LogDao;
import com.dealsdelta.scheduleme.data.models.LogModel;
import com.dealsdelta.scheduleme.dtos.IJob;
import com.dealsdelta.scheduleme.dtos.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 27/06/22
 */

@Service
public class LogService {
    @Autowired
    private LogDao logDao;

    public void write(IJob job, String level, String message, String module, String runId) {
        LogModel log = new LogModel();
        log.setDate(new Date());
        log.setJobId(job.getJobId());
        log.setMessage(message);
        log.setModule(module);
        log.setLevel(level);
        log.setRunId(runId);
        logDao.create(log);
    }
}