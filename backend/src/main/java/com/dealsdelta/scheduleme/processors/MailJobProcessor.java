package com.dealsdelta.scheduleme.processors;


import com.dealsdelta.scheduleme.data.dao.RunningJobDao;
import com.dealsdelta.scheduleme.data.models.*;
import com.dealsdelta.scheduleme.dtos.*;
import com.dealsdelta.scheduleme.services.LogService;

import java.util.Date;
import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 27/06/22
 */

public class MailJobProcessor implements JobProcessor {

    private LogService logService;
    private RunningJobDao runningJobDao;

    @Override
    public void processJob(JobWrapper wrapper) {

    }

    private void updateProcessedRecordCount(Job job, int count) {

    }

    private void updateRecordCount(Job job, int count) {

    }

    @Override
    public boolean supports(JOB_KIND jobKind) {
        return jobKind == JOB_KIND.MAIL_SENDER;
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