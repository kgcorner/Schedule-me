package com.dealsdelta.scheduleme.executors;


import com.dealsdelta.scheduleme.data.dao.DailyJobDao;
import com.dealsdelta.scheduleme.data.dao.HourlyJobDao;
import com.dealsdelta.scheduleme.data.dao.LogDao;
import com.dealsdelta.scheduleme.data.dao.MonthlyJobDao;
import com.dealsdelta.scheduleme.data.models.*;
import com.dealsdelta.scheduleme.dtos.HourlyJob;
import com.dealsdelta.scheduleme.dtos.IJob;
import com.dealsdelta.scheduleme.dtos.JobWrapper;
import com.dealsdelta.scheduleme.dtos.RunningJob;
import com.dealsdelta.scheduleme.processors.JobProcessor;
import com.dealsdelta.scheduleme.services.TaskService;
import org.apache.log4j.Logger;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 28/06/22
 */

public class JobRunner implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(JobRunner.class);
    private RunningJob runningJob;
    private JobProcessor jobProcessor;
    private TaskService taskService;
    public JobRunner(RunningJob runningJob, JobProcessor jobProcessor, TaskService taskService) {
        this.runningJob = runningJob;
        this.jobProcessor = jobProcessor;
        this.taskService = taskService;
    }

    @Override
    public void run() {        
        LOGGER.info("Starting job : " + runningJob.getJob().getName()+ "(" + runningJob.getJob().getJobId()+")");
        updateJobStatus(runningJob.getJob(), IJob.JOB_STATUS.RUNNING.toString());
        jobProcessor.setLogService(taskService.getLogService());        
        jobProcessor.setRunningJobDao(taskService.getRunningJobDao());
        JobWrapper wrapper = new JobWrapper();
        wrapper.setRunningJob(runningJob);
        try {
            jobProcessor.processJob(wrapper);
            updateJobStatus(runningJob.getJob(), IJob.JOB_STATUS.COMPLETED.toString());
        } catch (Exception x) {
            updateJobStatus(runningJob.getJob(), IJob.JOB_STATUS.FAILED.toString());
            jobProcessor.recover(wrapper);
        }
        taskService.getRunningJobDao().delete((RunningJobModel) runningJob);
    }

    private void updateJobStatus(IJob job, String status) {
        if(job instanceof DailyJobModel) {
            ((DailyJobModel) job).setStatus(status);
            taskService.getDailyJobDao().update((DailyJobModel) job);
        }

        if(job instanceof GenericJobModel) {
            ((GenericJobModel) job).setStatus(status);
            if(status.equalsIgnoreCase("COMPLETED")) {
                ((GenericJobModel) job).setEndTime(LocalTime.now());
            }
            taskService.getGenericJobDao().update((GenericJobModel) job);
        }

        if(job instanceof HourlyJobModel) {
            if(status.equalsIgnoreCase("COMPLETED")) {
                ((HourlyJobModel) job).setStatus("DUE");
                long frequency = ((HourlyJob) job).getRepeatFrequencyInMinutes();
                ((HourlyJobModel) job).setStartTime(job.getStartTime().plus(frequency, ChronoUnit.MINUTES));
            } else {
                job.setStatus(status);
            }
            taskService.getHourlyJobDao().update((HourlyJobModel) job);
        }

        if(job instanceof MonthlyJobModel) {
            ((MonthlyJobModel) job).setStatus(status);
            taskService.getMonthlyJobDao().update((MonthlyJobModel) job);
        }

        if(job instanceof RecordProcessorDailyJobModel) {
            ((RecordProcessorDailyJobModel) job).setStatus(status);
            taskService.getRecordProcessorDailyJobDao().update((RecordProcessorDailyJobModel) job);
        }

        if(job instanceof RecordProcessorJobModel) {
            ((RecordProcessorJobModel) job).setStatus(status);
            taskService.getRecordProcessorJobDao().update((RecordProcessorJobModel) job);
        }

        if(job instanceof RecordProcessorMonthlyJobModel) {
            ((RecordProcessorMonthlyJobModel) job).setStatus(status);
            taskService.getRecordProcessorMonthlyJobDao().update((RecordProcessorMonthlyJobModel) job);
        }

        if(job instanceof RecordProcessorHourlyJobModel) {
            if(status.equalsIgnoreCase("COMPLETED")) {
                ((HourlyJobModel) job).setStatus("DUE");
                long frequency = ((HourlyJob) job).getRepeatFrequencyInMinutes();
                ((HourlyJobModel) job).setStartTime(job.getStartTime().plus(frequency, ChronoUnit.MINUTES));
            } else {
                job.setStatus(status);
            }
            taskService.getRecordProcessorHourlyJobDao().update((RecordProcessorHourlyJobModel) job);
        }
    }
}