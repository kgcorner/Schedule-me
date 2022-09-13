package com.dealsdelta.scheduleme.executors;


import com.dealsdelta.scheduleme.data.models.*;
import com.dealsdelta.scheduleme.data.repo.Operation;
import com.dealsdelta.scheduleme.dtos.*;
import com.dealsdelta.scheduleme.processors.JobProcessor;
import com.dealsdelta.scheduleme.services.JobService;

import org.apache.log4j.Logger;

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
    private JobService jobService;

    public JobRunner(RunningJob runningJob, JobProcessor jobProcessor, JobService jobService) {
        this.runningJob = runningJob;
        this.jobProcessor = jobProcessor;
        this.jobService = jobService;
    }

    @Override
    public void run() {
        JobAuditModel jobAuditModel = new JobAuditModel();
        jobAuditModel.setStartTime(new Date());
        LOGGER.info("Starting job : " + runningJob.getRunningJob().getJobName()+ "(" + runningJob.getRunningJob().getJobId()+")");
        updateJobStatus(runningJob.getRunningJob(), JOB_STATUS.RUNNING.toString());
        jobProcessor.setLogService(jobService.getLogService());
        jobProcessor.setRunningJobDao(jobService.getRunningJobDao());
        JobWrapper wrapper = new JobWrapper();
        wrapper.setRunningJob(runningJob);
        try {
            updateJobStatus(runningJob.getRunningJob(), JOB_STATUS.RUNNING.toString());
            jobProcessor.processJob(wrapper);
            updateJobStatus(runningJob.getRunningJob(), JOB_STATUS.COMPLETED.toString());
            jobAuditModel.setStatus( JOB_STATUS.COMPLETED.toString());
        } catch (Exception x) {
            updateJobStatus(runningJob.getRunningJob(), JOB_STATUS.FAILED.toString());
            jobProcessor.recover(wrapper);
            jobAuditModel.setStatus( JOB_STATUS.FAILED.toString());
        }
        List<Operation> operations = new ArrayList<>();
        Operation operation = new Operation(wrapper.getRunningJob().getRunningJobId(), Operation.TYPES.STRING, "runId", Operation.OPERATORS.EQ);
        operations.add(operation);
        List<LogModel> allLogs = jobService.getLogDao().getAllBy(operations, 0, Integer.MAX_VALUE);
        jobAuditModel.setJobId(wrapper.getRunningJob().getJobId());
        jobAuditModel.setLogs(allLogs);
        jobAuditModel.setJob(wrapper.getRunningJob().getRunningJob());
        jobAuditModel.setEndTime(new Date());
        jobService.getRunningJobDao().delete((RunningJobModel) runningJob);
        jobService.getJobAuditDao().create(jobAuditModel);
    }

    private void updateJobStatus(Job job, String status) {
        if(status.equalsIgnoreCase("COMPLETED")) {
            job.setLastRunEndTime(new Date());
        }
        jobService.updateJob(job);
    }
}