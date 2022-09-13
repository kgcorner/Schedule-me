package com.dealsdelta.scheduleme.processors;


import com.dealsdelta.scheduleme.data.dao.RunningJobDao;
import com.dealsdelta.scheduleme.dtos.JOB_KIND;
import com.dealsdelta.scheduleme.dtos.JobWrapper;
import com.dealsdelta.scheduleme.services.LogService;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 26/06/22
 */

public interface JobProcessor {
    void processJob(JobWrapper jobWrapper);
    boolean supports(JOB_KIND jobKind);
    void setLogService(LogService logService);
    void setRunningJobDao(RunningJobDao runningJobDao);
    void recover(JobWrapper jobWrapper);
}