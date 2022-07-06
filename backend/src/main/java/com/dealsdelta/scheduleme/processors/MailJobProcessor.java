package com.dealsdelta.scheduleme.processors;


import com.dealsdelta.scheduleme.data.dao.RunningJobDao;
import com.dealsdelta.scheduleme.data.models.*;
import com.dealsdelta.scheduleme.dtos.HourlyJob;
import com.dealsdelta.scheduleme.dtos.IJob;
import com.dealsdelta.scheduleme.dtos.JobWrapper;
import com.dealsdelta.scheduleme.dtos.Log;
import com.dealsdelta.scheduleme.services.LogService;

import java.time.temporal.ChronoUnit;
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
        IJob job = wrapper.getRunningJob().getJob();

        if(job.getJobParams().containsKey("email") || job.getJobParams().containsKey("emails")) {
            if(job.getJobParams().containsKey("email")) {
                logService.write(job, Log.VERBOSE, "Running Mail processing job for " +
                        job.getJobParams().get("email"),
                    "MailJobProcessor");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {

                }
                logService.write(job, Log.INFO, "Mail sent at " + new Date().toString(),
                    "MailJobProcessor");
            } else {
                int count = 0;
                updateRecordCount(job, (((List<String>)job.getJobParams().get("emails")).size()));
                for(String email : ((List<String>) job.getJobParams().get("emails"))) {
                    logService.write(job, Log.VERBOSE, "Running Mail processing job for " +
                            email,
                        "MailJobProcessor");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {

                    }
                    count++;
                    updateProcessedRecordCount(job, count);
                    runningJobDao.update((RunningJobModel) wrapper.getRunningJob());
                }
                logService.write(job, Log.INFO, "All Mails sent at " + new Date().toString(),
                    "MailJobProcessor");
            }
        } else {
            logService.write(job, Log.ERROR, "Invalid Job param found. No email or emails attached",
                "MailJobProcessor");
        }

    }

    private void updateProcessedRecordCount(IJob job, int count) {
        if(job instanceof RecordProcessorDailyJobModel) {
            ((RecordProcessorDailyJobModel) job).setRecordProcessed(count);
        }

        if(job instanceof RecordProcessorJobModel) {
            ((RecordProcessorJobModel) job).setRecordProcessed(count);
        }

        if(job instanceof RecordProcessorMonthlyJobModel) {
            ((RecordProcessorMonthlyJobModel) job).setRecordProcessed(count);
        }

        if(job instanceof RecordProcessorHourlyJobModel) {
            ((RecordProcessorHourlyJobModel) job).setRecordProcessed(count);
        }
    }

    private void updateRecordCount(IJob job, int count) {
        if(job instanceof RecordProcessorDailyJobModel) {
            ((RecordProcessorDailyJobModel) job).setRecordCount(count);
        }

        if(job instanceof RecordProcessorJobModel) {
            ((RecordProcessorJobModel) job).setRecordCount(count);
        }

        if(job instanceof RecordProcessorMonthlyJobModel) {
            ((RecordProcessorMonthlyJobModel) job).setRecordCount(count);
        }

        if(job instanceof RecordProcessorHourlyJobModel) {
            ((RecordProcessorHourlyJobModel) job).setRecordCount(count);
        }
    }

    @Override
    public boolean supports(IJob.JOB_KIND jobKind) {
        return jobKind == IJob.JOB_KIND.MAIL_SENDER;
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