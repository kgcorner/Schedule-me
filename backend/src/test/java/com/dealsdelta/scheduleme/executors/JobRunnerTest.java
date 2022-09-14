package com.dealsdelta.scheduleme.executors;

import com.dealsdelta.scheduleme.data.dao.JobAuditDao;
import com.dealsdelta.scheduleme.data.dao.LogDao;
import com.dealsdelta.scheduleme.data.dao.RunningJobDao;
import com.dealsdelta.scheduleme.data.models.LogModel;
import com.dealsdelta.scheduleme.data.models.RunningJobModel;
import com.dealsdelta.scheduleme.data.repo.Operation;
import com.dealsdelta.scheduleme.dtos.Job;
import com.dealsdelta.scheduleme.dtos.JobWrapper;
import com.dealsdelta.scheduleme.dtos.Log;
import com.dealsdelta.scheduleme.dtos.RunningJob;
import com.dealsdelta.scheduleme.processors.JobProcessor;
import com.dealsdelta.scheduleme.services.JobService;
import org.junit.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.powermock.api.mockito.PowerMockito.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 04/09/22
 */

public class JobRunnerTest {

    //TODO: Add verification that job status set to COMPLETED on processing passed
    @Test
    public void run() {
        JobProcessor jobProcessor = mock(JobProcessor.class);
        JobService jobService = mock(JobService.class);
        RunningJob runningJob = new RunningJobModel();
        runningJob.setRunningJobId("RunningJobId");
        LogDao logDao = mock(LogDao.class);
        RunningJobDao runningJobDao = mock(RunningJobDao.class);
        JobAuditDao jobAuditDao = mock(JobAuditDao.class);
        Job job = new Job();
        job.setJobId("JobId");
        job.setJobName("New Job");
        job.setStartTime(LocalTime.now());
        runningJob.setRunningJob(job);
        //Prepare Logs
        List<LogModel> logs = new ArrayList<>();
        for(int i=0;i <100; i++) {
            LogModel log = new LogModel();
            log.setRunId("JobId");
            log.setLevel(Log.ERROR);
            logs.add(log);
        }
        List<Operation> operations = new ArrayList<>();
        Operation operation = new Operation("RunningJobId", Operation.TYPES.STRING,
            "runId", Operation.OPERATORS.EQ);
        operations.add(operation);
        when(jobService.getLogDao()).thenReturn(logDao);
        when(jobService.getRunningJobDao()).thenReturn(runningJobDao);
        when(jobService.getJobAuditDao()).thenReturn(jobAuditDao);
        when(logDao.getAllByKey(operations, LogModel.class)).thenReturn(logs);
        doNothing().when(runningJobDao).delete((RunningJobModel) runningJob);

        JobRunner runner = new JobRunner(runningJob, jobProcessor, jobService);
        runner.run();
    }

    //TODO: Add verification that job status set to FAILED on processing failed
    @Test
    public void runFailed() {
        JobProcessor jobProcessor = mock(JobProcessor.class);
        JobService jobService = mock(JobService.class);
        RunningJob runningJob = new RunningJobModel();
        runningJob.setRunningJobId("RunningJobId");
        LogDao logDao = mock(LogDao.class);
        RunningJobDao runningJobDao = mock(RunningJobDao.class);
        JobAuditDao jobAuditDao = mock(JobAuditDao.class);
        Job job = new Job();
        job.setJobId("JobId");
        job.setJobName("New Job");
        job.setStartTime(LocalTime.now());
        runningJob.setRunningJob(job);
        //Prepare Logs
        List<LogModel> logs = new ArrayList<>();
        for(int i=0;i <100; i++) {
            LogModel log = new LogModel();
            log.setRunId("JobId");
            log.setLevel(Log.ERROR);
            logs.add(log);
        }
        List<Operation> operations = new ArrayList<>();
        Operation operation = new Operation("RunningJobId", Operation.TYPES.STRING,
            "runId", Operation.OPERATORS.EQ);
        operations.add(operation);
        when(jobService.getLogDao()).thenReturn(logDao);
        when(jobService.getRunningJobDao()).thenReturn(runningJobDao);
        when(jobService.getJobAuditDao()).thenReturn(jobAuditDao);
        when(logDao.getAllByKey(operations, LogModel.class)).thenReturn(logs);
        doNothing().when(runningJobDao).delete((RunningJobModel) runningJob);
        JobWrapper wrapper = new JobWrapper();
        wrapper.setRunningJob(runningJob);
        doThrow(new RuntimeException()).when(jobProcessor).processJob(wrapper);
        JobRunner runner = new JobRunner(runningJob, jobProcessor, jobService);
        runner.run();
    }
}