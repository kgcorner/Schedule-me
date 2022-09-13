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
        Job job = wrapper.getRunningJob().getRunningJob();

        if(job.getJobParams().containsKey("email") || job.getJobParams().containsKey("emails")) {
            if(job.getJobParams().containsKey("email")) {
                logService.write(job, Log.VERBOSE, "Running Mail processing job for " +
                        job.getJobParams().get("email"),
                    "MailJobProcessor", wrapper.getRunningJob().getRunningJobId());
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {

                }
                logService.write(job, Log.INFO, "Mail sent at " + new Date().toString(),
                    "MailJobProcessor", wrapper.getRunningJob().getRunningJobId());
            } else {
                int count = 0;
                updateRecordCount(job, (((List<String>)job.getJobParams().get("emails")).size()));
                for(String email : ((List<String>) job.getJobParams().get("emails"))) {
                    logService.write(job, Log.VERBOSE, "Running Mail processing job for " +
                            email,
                        "MailJobProcessor", wrapper.getRunningJob().getRunningJobId());
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {

                    }
                    count++;
                    updateProcessedRecordCount(job, count);
                    runningJobDao.update((RunningJobModel) wrapper.getRunningJob());
                }
                logService.write(job, Log.INFO, "All Mails sent at " + new Date().toString(),
                    "MailJobProcessor", wrapper.getRunningJob().getRunningJobId());
            }
        } else {
            logService.write(job, Log.ERROR, "Invalid Job param found. No email or emails attached",
                "MailJobProcessor", wrapper.getRunningJob().getRunningJobId());
            throw new IllegalArgumentException("Invalid Job param found. No email or emails attached");
        }

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
        this.logService = logService;
    }

    @Override
    public void setRunningJobDao(RunningJobDao runningJobDao) {
        this.runningJobDao = runningJobDao;
    }

    @Override
    public void recover(JobWrapper jobWrapper) {

    }
}