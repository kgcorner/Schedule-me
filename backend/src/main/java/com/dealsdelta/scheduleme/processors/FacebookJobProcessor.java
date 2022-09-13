package com.dealsdelta.scheduleme.processors;


import com.dealsdelta.scheduleme.data.dao.RunningJobDao;
import com.dealsdelta.scheduleme.dtos.JOB_KIND;
import com.dealsdelta.scheduleme.dtos.JobWrapper;
import com.dealsdelta.scheduleme.services.LogService;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 13/09/22
 */

public class FacebookJobProcessor implements JobProcessor {

    @Override
    public void processJob(JobWrapper jobWrapper) {

    }

    @Override
    public boolean supports(JOB_KIND jobKind) {
        return jobKind == JOB_KIND.FACEBOOK_POST_CREATOR;
    }

    @Override
    public void setLogService(LogService logService) {

    }

    @Override
    public void setRunningJobDao(RunningJobDao runningJobDao) {

    }

    @Override
    public void recover(JobWrapper jobWrapper) {

    }
}