package com.dealsdelta.scheduleme.executors;


import com.dealsdelta.scheduleme.data.dao.*;
import com.dealsdelta.scheduleme.data.models.RunningJobModel;
import com.dealsdelta.scheduleme.data.repo.Operation;
import com.dealsdelta.scheduleme.dtos.IJob;
import com.dealsdelta.scheduleme.dtos.JOB_STATUS;
import com.dealsdelta.scheduleme.dtos.RecordProcessorMonthlyJob;
import com.dealsdelta.scheduleme.dtos.RunningJob;
import com.dealsdelta.scheduleme.processors.JobProcessor;
import com.dealsdelta.scheduleme.processors.JobProcessorFactory;
import com.dealsdelta.scheduleme.services.JobScannerService;
import com.dealsdelta.scheduleme.services.JobService;
import com.dealsdelta.scheduleme.services.LogService;
import com.dealsdelta.scheduleme.services.TaskService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 27/06/22
 */
@Component
public class JobProcessorExecutorService {

    private static final Logger LOGGER = Logger.getLogger(JobProcessorExecutorService.class);
    private static ExecutorService executorService;
    private static final Object LOCK = new Object();

    @Autowired
    private RunningJobDao runningJobDao;

    @Autowired
    private JobService jobService;

    @Value("${job.executor.pool.size}")
    int poolSize;

    private boolean stopExecutor;
    private boolean started;

    public JobProcessorExecutorService() {
        LOGGER.info("Initialized Executors with pool size of " + poolSize);
        this.stopExecutor = false;
        this.started = false;
    }

    private void runJob(Runnable runnable) {
        executorService.submit(runnable);
    }

    public void start() {
        if(!started)
            executorService = Executors.newFixedThreadPool(poolSize);
        synchronized (LOCK) {
            Operation operation = new Operation(IJob.JOB_STATUS.DUE.toString(), Operation.TYPES.STRING, "job.status",
                Operation.OPERATORS.EQ);
            List<Operation> operations = new ArrayList<>();
            operations.add(operation);
            List<RunningJobModel> runningJobs = runningJobDao.getAllBy(operations);
            if(runningJobs.size() > 0) {
                for(RunningJob runningJob : runningJobs) {
                    JobProcessor jobProcessor = JobProcessorFactory.getJobProcessor(runningJob.getJob());
                    JobRunner runner = new JobRunner(runningJob, jobProcessor, jobService);
                    runningJob.getRunningJob().setStatus(JOB_STATUS.WAITING);
                    runningJobDao.update((RunningJobModel)runningJob);
                    runJob(runner);
                }
            }
        }

    }
}