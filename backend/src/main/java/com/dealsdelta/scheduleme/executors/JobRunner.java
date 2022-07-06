package com.dealsdelta.scheduleme.executors;


import com.dealsdelta.scheduleme.data.dao.DailyJobDao;
import com.dealsdelta.scheduleme.data.dao.HourlyJobDao;
import com.dealsdelta.scheduleme.data.dao.LogDao;
import com.dealsdelta.scheduleme.data.dao.MonthlyJobDao;
import com.dealsdelta.scheduleme.data.models.*;
import com.dealsdelta.scheduleme.data.repo.Operation;
import com.dealsdelta.scheduleme.dtos.*;
import com.dealsdelta.scheduleme.processors.JobProcessor;
import com.dealsdelta.scheduleme.services.TaskService;
import org.apache.log4j.Logger;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        JobAuditModel jobAuditModel = new JobAuditModel();
        jobAuditModel.setStartTime(new Date());
        LOGGER.info("Starting job : " + runningJob.getJob().getName()+ "(" + runningJob.getJob().getJobId()+")");
        updateJobStatus(runningJob.getJob(), IJob.JOB_STATUS.RUNNING.toString());
        jobProcessor.setLogService(taskService.getLogService());        
        jobProcessor.setRunningJobDao(taskService.getRunningJobDao());
        JobWrapper wrapper = new JobWrapper();
        wrapper.setRunningJob(runningJob);
        try {
            jobProcessor.processJob(wrapper);
            updateJobStatus(runningJob.getJob(), IJob.JOB_STATUS.COMPLETED.toString());
            jobAuditModel.setStatus( IJob.JOB_STATUS.COMPLETED.toString());
        } catch (Exception x) {
            updateJobStatus(runningJob.getJob(), IJob.JOB_STATUS.FAILED.toString());
            jobProcessor.recover(wrapper);
            jobAuditModel.setStatus( IJob.JOB_STATUS.FAILED.toString());
        }
        List<Operation> operations = new ArrayList<>();
        Operation operation = new Operation(wrapper.getRunningJob().getRunningJobId(), Operation.TYPES.STRING, "runId", Operation.OPERATORS.EQ);
        operations.add(operation);
        List<LogModel> allLogs = taskService.getLogDao().getAllBy(operations, 0, Integer.MAX_VALUE);
        jobAuditModel.setJobId(wrapper.getRunningJob().getJobId());
        jobAuditModel.setLogs(allLogs);
        jobAuditModel.setJob(wrapper.getRunningJob().getJob());
        jobAuditModel.setEndTime(new Date());
        taskService.getRunningJobDao().delete((RunningJobModel) runningJob);
        taskService.getJobAuditDao().create(jobAuditModel);
    }

    private void saveJobAudit(JobWrapper wrapper) {

        JobAuditModel model = new JobAuditModel();

    }

    private void updateJobStatus(IJob job, String status) {
        if(job instanceof DailyJobModel) {
            job.setStatus(status);
            taskService.getDailyJobDao().update((DailyJobModel) job);
        }

        if(job instanceof GenericJobModel) {
            job.setStatus(status);
            if(status.equalsIgnoreCase("COMPLETED")) {
                ((GenericJobModel) job).setEndTime(LocalTime.now());
            }
            taskService.getGenericJobDao().update((GenericJobModel) job);
        }

        if(job instanceof HourlyJobModel) {
            if(status.equalsIgnoreCase("COMPLETED")) {
                job.setStatus("DUE");
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
            job.setStatus(status);
            taskService.getRecordProcessorDailyJobDao().update((RecordProcessorDailyJobModel) job);
        }

        if(job instanceof RecordProcessorJobModel) {
            job.setStatus(status);
            if(status.equalsIgnoreCase("COMPLETED")) {
                ((RecordProcessorJobModel) job).setEndTime(LocalTime.now());
            }
            taskService.getRecordProcessorJobDao().update((RecordProcessorJobModel) job);
        }

        if(job instanceof RecordProcessorMonthlyJobModel) {
            job.setStatus(status);
            taskService.getRecordProcessorMonthlyJobDao().update((RecordProcessorMonthlyJobModel) job);
        }

        if(job instanceof RecordProcessorHourlyJobModel) {
            if(status.equalsIgnoreCase("COMPLETED")) {
                job.setStatus("DUE");
                long frequency = ((HourlyJob) job).getRepeatFrequencyInMinutes();
                ((RecordProcessorHourlyJobModel) job).setStartTime(job.getStartTime().plus(frequency, ChronoUnit.MINUTES));
            } else {
                job.setStatus(status);
            }
            taskService.getRecordProcessorHourlyJobDao().update((RecordProcessorHourlyJobModel) job);
        }
    }
}