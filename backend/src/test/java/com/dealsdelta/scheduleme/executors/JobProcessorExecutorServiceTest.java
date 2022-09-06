package com.dealsdelta.scheduleme.executors;

import com.dealsdelta.scheduleme.data.dao.JobAuditDao;
import com.dealsdelta.scheduleme.data.dao.LogDao;
import com.dealsdelta.scheduleme.data.dao.RunningJobDao;
import com.dealsdelta.scheduleme.data.models.LogModel;
import com.dealsdelta.scheduleme.data.models.RunningJobModel;
import com.dealsdelta.scheduleme.data.repo.Operation;
import com.dealsdelta.scheduleme.dtos.IJob;
import com.dealsdelta.scheduleme.dtos.Job;
import com.dealsdelta.scheduleme.dtos.Log;
import com.dealsdelta.scheduleme.dtos.RunningJob;
import com.dealsdelta.scheduleme.processors.JobProcessor;
import com.dealsdelta.scheduleme.processors.JobProcessorFactory;
import com.dealsdelta.scheduleme.services.JobService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import static org.powermock.api.mockito.PowerMockito.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 04/09/22
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(JobProcessorFactory.class)
public class JobProcessorExecutorServiceTest {

    @Test
    public void start() {
        Mockito.mock(JobProcessorExecutorService.class);
        JobProcessorExecutorService executorService = new JobProcessorExecutorService();
        RunningJobDao runningJobDao = mock(RunningJobDao.class);
        JobService jobService = mock(JobService.class);
        JobProcessor jobProcessor = mock(JobProcessor.class);

        RunningJob runningJob = new RunningJobModel();
        runningJob.setRunningJobId("RunningJobId");
        LogDao logDao = mock(LogDao.class);
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
        when(logDao.getAllBy(operations, 0, Integer.MAX_VALUE)).thenReturn(logs);
        doNothing().when(runningJobDao).delete((RunningJobModel) runningJob);
        Operation jobOoperation = new Operation(IJob.JOB_STATUS.DUE.toString(), Operation.TYPES.STRING, "job.status",
            Operation.OPERATORS.EQ);
        List<Operation> jobOperations = new ArrayList<>();
        jobOperations.add(jobOoperation);

        List<RunningJobModel> runningJobs = new ArrayList<>();
        runningJobs.add((RunningJobModel) runningJob);
        when(runningJobDao.getAllBy(jobOperations)).thenReturn(runningJobs);
        Whitebox.setInternalState(executorService, "poolSize", 10);
        Whitebox.setInternalState(executorService, "runningJobDao", runningJobDao);
        Whitebox.setInternalState(executorService, "jobService", jobService);
        mockStatic(JobProcessorFactory.class);
        when(JobProcessorFactory.getJobProcessor(job)).thenReturn(jobProcessor);
        executorService.start();
        Mockito.verify(runningJobDao, Mockito.times(runningJobs.size())).update((RunningJobModel) runningJob);
    }
}