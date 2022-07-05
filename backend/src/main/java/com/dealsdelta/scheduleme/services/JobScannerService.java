package com.dealsdelta.scheduleme.services;


import com.dealsdelta.scheduleme.data.dao.*;
import com.dealsdelta.scheduleme.data.models.*;
import com.dealsdelta.scheduleme.data.repo.Operation;
import com.dealsdelta.scheduleme.dtos.DailyJob;
import com.dealsdelta.scheduleme.dtos.GenericJob;
import com.dealsdelta.scheduleme.dtos.IJob;
import com.dealsdelta.scheduleme.dtos.RecordProcessorDailyJob;
import com.dealsdelta.scheduleme.executors.JobProcessorExecutorService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 26/06/22
 */

@Component
public class JobScannerService {

    private static final Logger LOGGER = Logger.getLogger(JobScannerService.class);

    @Autowired
    private DailyJobDao dailyJobDao;

    @Autowired
    private MonthlyJobDao monthlyJobDao;

    @Autowired
    private HourlyJobDao hourlyJobDao;

    @Autowired
    private LogDao logDao;

    @Autowired
    private GenericJobDao genericJobDao;

    @Autowired
    private RecordProcessorDailyJobDao recordProcessorDailyJobDao;

    @Autowired
    private RecordProcessorMonthlyJobDao recordProcessorMonthlyJobDao;

    @Autowired
    private RecordProcessorHourlyJobDao recordProcessorHourlyJobDao;

    @Autowired
    private RecordProcessorJobDao recordProcessorJobDao;

    @Autowired
    private RunningJobDao runningJobDao;

    @Autowired
    private JobProcessorExecutorService jobProcessorExecutorService;

    public static final Object RUNNING_JOB_NOTIFIER = new Object();

    @Async
    @Scheduled(fixedRate = 1000)
    public void scanGenericJobs() {
        LOGGER.debug("Scanning generic jobs");
        Operation operation = new Operation(IJob.JOB_STATUS.DUE.toString(), Operation.TYPES.STRING, "status",
            Operation.OPERATORS.EQ);
        List<Operation> operations = new ArrayList<>();
        operations.add(operation);
        List<GenericJobModel> allIdleGenericJobs = genericJobDao.getAllBy(operations);
        for(GenericJobModel job : allIdleGenericJobs) {
            if(job.isUrgent()) {
                RunningJobModel model = new RunningJobModel();
                model.setJobId(job.getJobId());
                model.setJob(job);
                runningJobDao.create(model);
                LOGGER.debug("Created runnable generic job id : " + job.getJobId());
                job.setStatus(IJob.JOB_STATUS.WAITING.toString());
                job.setUrgent(false);
                genericJobDao.update(job);
                runExecutor();
            } else {

                LocalTime now = LocalTime.now();
                if (job.getStartTime().isBefore(now)) {
                    RunningJobModel model = new RunningJobModel();
                    model.setJobId(job.getJobId());
                    model.setJob(job);
                    runningJobDao.create(model);
                    LOGGER.debug("Created runnable generic job id : " + job.getJobId());
                    job.setStatus(IJob.JOB_STATUS.WAITING.toString());
                    genericJobDao.update(job);
                    runExecutor();

                }
            }
        }
    }

    @Async
    @Scheduled(fixedRate = 1000)
    public void scanRecordProcessorJobs() {
        LOGGER.debug("Scanning Record processor jobs");
        Operation operation = new Operation(IJob.JOB_STATUS.IDLE.toString(), Operation.TYPES.STRING, "status",
            Operation.OPERATORS.EQ);
        List<Operation> operations = new ArrayList<>();
        operations.add(operation);
        List<RecordProcessorJobModel> allIdleGenericJobs = recordProcessorJobDao.getAllBy(operations);
        for(RecordProcessorJobModel job : allIdleGenericJobs) {
            if(job.isUrgent()) {
                RunningJobModel model = new RunningJobModel();
                model.setJobId(job.getJobId());
                model.setJob(job);
                runningJobDao.create(model);
                LOGGER.debug("Created runnable generic job id : " + job.getJobId());
                job.setStatus(IJob.JOB_STATUS.WAITING.toString());
                job.setUrgent(false);
                recordProcessorJobDao.update(job);
                runExecutor();

            } else {

                LocalTime now = LocalTime.now();
                if (job.getStartTime().isBefore(now)) {
                    RunningJobModel model = new RunningJobModel();
                    model.setJobId(job.getJobId());
                    model.setJob(job);
                    runningJobDao.create(model);
                    LOGGER.debug("Created runnable generic job id : " + job.getJobId());
                    job.setStatus(IJob.JOB_STATUS.WAITING.toString());
                    recordProcessorJobDao.update(job);
                    runExecutor();

                }
            }
        }
    }

    @Async
    @Scheduled(fixedRate = 1000)
    public void scanDailyJobs() {
        LOGGER.debug("Scanning Daily jobs");
        Operation operation = new Operation(IJob.JOB_STATUS.DUE.toString(), Operation.TYPES.STRING, "status",
            Operation.OPERATORS.EQ);
        List<Operation> operations = new ArrayList<>();
        operations.add(operation);
        List<DailyJobModel> allIdleGenericJobs = dailyJobDao.getAllBy(operations);
        for (DailyJobModel job : allIdleGenericJobs) {

            if (job.isUrgent()) {
                RunningJobModel model = new RunningJobModel();
                model.setJobId(job.getJobId());
                model.setJob(job);
                runningJobDao.create(model);
                LOGGER.debug("Created runnable generic job id : " + job.getJobId());
                job.setStatus(IJob.JOB_STATUS.WAITING.toString());
                job.setUrgent(false);
                dailyJobDao.update(job);
                runExecutor();

            } else {
                LocalTime now = LocalTime.now();
                if (job.getStartTime().isBefore(now)) {
                    RunningJobModel model = new RunningJobModel();
                    model.setJobId(job.getJobId());
                    model.setJob(job);
                    runningJobDao.create(model);
                    LOGGER.debug("Created runnable generic job id : " + job.getJobId());
                    job.setStatus(IJob.JOB_STATUS.WAITING.toString());
                    dailyJobDao.update(job);
                    runExecutor();


                }
            }
        }
    }

    @Async
    @Scheduled(fixedRate = 1000 * 60)
    public void scanHourlyJobs() {
        LOGGER.debug("Scanning Hourly jobs");
        Operation operation = new Operation(IJob.JOB_STATUS.DUE.toString(), Operation.TYPES.STRING, "status",
            Operation.OPERATORS.EQ);
        List<Operation> operations = new ArrayList<>();
        operations.add(operation);
        List<HourlyJobModel> allIdleGenericJobs = hourlyJobDao.getAllBy(operations);
        for(HourlyJobModel job : allIdleGenericJobs) {
            if(job.isUrgent()) {
                RunningJobModel model = new RunningJobModel();
                model.setJobId(job.getJobId());
                model.setJob(job);
                runningJobDao.create(model);
                LOGGER.debug("Created runnable generic job id : " + job.getJobId());
                job.setStatus(IJob.JOB_STATUS.WAITING.toString());
                job.setUrgent(false);
                hourlyJobDao.update(job);
                runExecutor();
            } else {
                LocalTime now = LocalTime.now();
                if (job.getStartTime().isBefore(now)) {
                    RunningJobModel model = new RunningJobModel();
                    model.setJobId(job.getJobId());
                    model.setJob(job);
                    runningJobDao.create(model);
                    LOGGER.debug("Created runnable generic job id : " + job.getJobId());
                    job.setStatus(IJob.JOB_STATUS.WAITING.toString());
                    hourlyJobDao.update(job);
                    runExecutor();
                }
            }
        }
    }

    @Async
    @Scheduled(fixedRate = 1000 * 60)
    public void scanMonthlyJobs() {
        LOGGER.debug("Scanning Monthly jobs");
        Operation operation = new Operation(IJob.JOB_STATUS.DUE.toString(), Operation.TYPES.STRING, "status",
            Operation.OPERATORS.EQ);
        List<Operation> operations = new ArrayList<>();
        operations.add(operation);
        List<MonthlyJobModel> allIdleGenericJobs = monthlyJobDao.getAllBy(operations);
        for(MonthlyJobModel job : allIdleGenericJobs) {
            if(job.isUrgent()) {
                RunningJobModel model = new RunningJobModel();
                model.setJobId(job.getJobId());
                model.setJob(job);
                runningJobDao.create(model);
                LOGGER.debug("Created runnable generic job id : " + job.getJobId());
                job.setUrgent(false);
                job.setStatus(IJob.JOB_STATUS.WAITING.toString());
                monthlyJobDao.update(job);
                runExecutor();
            } else {
                LocalTime now = LocalTime.now();
                if (job.getStartTime().isBefore(now)) {
                    RunningJobModel model = new RunningJobModel();
                    model.setJobId(job.getJobId());
                    model.setJob(job);
                    runningJobDao.create(model);
                    LOGGER.debug("Created runnable generic job id : " + job.getJobId());
                    job.setStatus(IJob.JOB_STATUS.WAITING.toString());
                    monthlyJobDao.update(job);
                    runExecutor();
                }
            }
        }
    }

    @Async
    @Scheduled(fixedRate = 1000)
    public void scanRecordProcessorDailyJobs() {
        LOGGER.debug("Scanning Record processor daily jobs");
        Operation operation = new Operation(IJob.JOB_STATUS.DUE.toString(), Operation.TYPES.STRING, "status",
            Operation.OPERATORS.EQ);
        List<Operation> operations = new ArrayList<>();
        operations.add(operation);
        List<RecordProcessorDailyJobModel> allIdleGenericJobs = recordProcessorDailyJobDao.getAllBy(operations);
        for(RecordProcessorDailyJobModel job : allIdleGenericJobs) {
            if(job.isUrgent()) {
                RunningJobModel model = new RunningJobModel();
                model.setJobId(job.getJobId());
                model.setJob(job);
                runningJobDao.create(model);
                LOGGER.debug("Created runnable generic job id : " + job.getJobId());
                job.setStatus(IJob.JOB_STATUS.WAITING.toString());
                job.setUrgent(false);
                recordProcessorDailyJobDao.update(job);
                runExecutor();

            }
            else{
                LocalTime now = LocalTime.now();
                if (job.getStartTime().isBefore(now)) {
                    RunningJobModel model = new RunningJobModel();
                    model.setJobId(job.getJobId());
                    model.setJob(job);
                    runningJobDao.create(model);
                    LOGGER.debug("Created runnable generic job id : " + job.getJobId());
                    job.setStatus(IJob.JOB_STATUS.WAITING.toString());
                    recordProcessorDailyJobDao.update(job);
                    runExecutor();
                }
            }
        }
    }

    @Async
    @Scheduled(fixedRate = 1000 * 60)
    public void scanRecordProcessorHourlyJobs() {
        LOGGER.debug("Scanning Record processor Hourly jobs");
        Operation operation = new Operation(IJob.JOB_STATUS.DUE.toString(), Operation.TYPES.STRING, "status",
            Operation.OPERATORS.EQ);
        List<Operation> operations = new ArrayList<>();
        operations.add(operation);
        List<RecordProcessorHourlyJobModel> allIdleGenericJobs = recordProcessorHourlyJobDao.getAllBy(operations);
        for(RecordProcessorHourlyJobModel job : allIdleGenericJobs) {
            if(job.isUrgent()) {
                RunningJobModel model = new RunningJobModel();
                model.setJobId(job.getJobId());
                model.setJob(job);
                runningJobDao.create(model);
                LOGGER.debug("Created runnable generic job id : " + job.getJobId());
                job.setStatus(IJob.JOB_STATUS.WAITING.toString());
                job.setUrgent(false);
                recordProcessorHourlyJobDao.update(job);
                runExecutor();
            } else {
                LocalTime now = LocalTime.now();
                if (job.getStartTime().isBefore(now)) {
                    RunningJobModel model = new RunningJobModel();
                    model.setJobId(job.getJobId());
                    model.setJob(job);
                    runningJobDao.create(model);
                    LOGGER.debug("Created runnable generic job id : " + job.getJobId());
                    job.setStatus(IJob.JOB_STATUS.WAITING.toString());
                    recordProcessorHourlyJobDao.update(job);
                    runExecutor();
                }
            }

        }
    }

    @Async
    @Scheduled(fixedRate = 1000 * 60)
    public void scanRecordProcessorMonthlyJobs() {
        LOGGER.debug("Scanning Record processor Monthly jobs");
        Operation operation = new Operation(IJob.JOB_STATUS.DUE.toString(), Operation.TYPES.STRING, "status",
            Operation.OPERATORS.EQ);
        List<Operation> operations = new ArrayList<>();
        operations.add(operation);
        List<RecordProcessorMonthlyJobModel> allIdleGenericJobs = recordProcessorMonthlyJobDao.getAllBy(operations);
        for(RecordProcessorMonthlyJobModel job : allIdleGenericJobs) {
            if(job.isUrgent()) {
                RunningJobModel model = new RunningJobModel();
                model.setJobId(job.getJobId());
                model.setJob(job);
                runningJobDao.create(model);
                LOGGER.debug("Created runnable generic job id : " + job.getJobId());
                job.setStatus(IJob.JOB_STATUS.WAITING.toString());
                job.setUrgent(false);
                recordProcessorMonthlyJobDao.update(job);
                runExecutor();
            } else {
                LocalTime now = LocalTime.now();
                if (job.getStartTime().isBefore(now)) {
                    RunningJobModel model = new RunningJobModel();
                    model.setJobId(job.getJobId());
                    model.setJob(job);
                    runningJobDao.create(model);
                    LOGGER.debug("Created runnable generic job id : " + job.getJobId());
                    job.setStatus(IJob.JOB_STATUS.WAITING.toString());
                    recordProcessorMonthlyJobDao.update(job);
                    runExecutor();
                }
            }

        }
    }

    @Async
    @Scheduled(cron = "0 59 23 * * ?")
    public void setDailyJobStatus() {
        dailyJobDao.updateMany(Collections.emptyList(), "status", IJob.JOB_STATUS.DUE);
    }

    @Async
    @Scheduled(cron = "0 59 23 28-31 * ?")
    public void setMonthlyJobStatus() {
        final Calendar c = Calendar.getInstance();
        if (c.get(Calendar.DATE) == c.getActualMaximum(Calendar.DATE)) {
            monthlyJobDao.updateMany(Collections.emptyList(), "status", IJob.JOB_STATUS.DUE);
        }

    }

    private void runExecutor() {
        new Thread(() -> jobProcessorExecutorService.start()).start();
    }
}