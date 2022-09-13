package com.dealsdelta.scheduleme.services;


import com.dealsdelta.scheduleme.data.dao.*;
import com.dealsdelta.scheduleme.data.models.*;
import com.dealsdelta.scheduleme.data.repo.Operation;
import com.dealsdelta.scheduleme.dtos.*;
import com.dealsdelta.scheduleme.executors.JobProcessorExecutorService;
import com.dealsdelta.scheduleme.util.DateTimeUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.swing.text.DateFormatter;
import java.text.DateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 26/06/22
 */

@Component
public class JobScannerService {

    private static final Logger LOGGER = Logger.getLogger(JobScannerService.class);

    @Autowired
    private JobDao jobDao;

    @Autowired
    private RunningJobDao runningJobDao;

    @Autowired
    private JobProcessorExecutorService jobProcessorExecutorService;

    private static final Object LOCK = new Object();


    @Async
    @Scheduled(fixedRate = 1000)
    public void scanJobs() {
        LOGGER.debug("Scanning jobs");
        LocalTime localTime = LocalTime.now();
        Operation status = new Operation(JOB_STATUS.DUE, JOB_STATUS.class, "status",
            Operation.OPERATORS.EQ);

        Operation time = new Operation(DateTimeUtil.getTime(localTime), Operation.TYPES.STRING, "startTimeStr",
            Operation.OPERATORS.EQ);
        List<Operation> operations = new ArrayList<>();
        operations.add(status);
        operations.add(time);
        List<JobModel> allJobs = jobDao.getAllByKey(operations, JobModel.class);
        List<JobModel> jobsToRun = new ArrayList<>();
        for (JobModel model : allJobs) {
            switch (model.getFrequency()) {
                case ONCE:
                    if(shouldRunOnce(model, localTime)) {
                        jobsToRun.add(model);
                    }
                    break;
                case DAILY:
                    if(shouldRunDailyJob(model, localTime)) {
                        jobsToRun.add(model);
                    }
                    break;
                case WEEKLY:
                    if(shouldRunWeeklyJob(model, localTime)) {
                        jobsToRun.add(model);
                    }
                    break;
                case N_WEEKLY:
                    if(shouldRunNWeeklyJob(model, localTime)) {
                        jobsToRun.add(model);
                    }
                    break;
                case MONTHLY:
                    if(shouldRunMonthlyJob(model, localTime)) {
                        jobsToRun.add(model);
                    }
                    break;
                case N_MONTHLY:
                    if(shouldRunNMonthlyJob(model, localTime)) {
                        jobsToRun.add(model);
                    }
                    break;
                case LAST_DAY_OF_MONTH:
                    if(shouldRunLastDayOfMonthJob(model, localTime)) {
                        jobsToRun.add(model);
                    }
                    break;
            }
        }

        LOGGER.info("Found " + jobsToRun.size() + "jobs to run");
        synchronized (LOCK) {
            for(JobModel job : jobsToRun) {
                job.setStatus(JOB_STATUS.WAITING);
                RunningJobModel runningJob = new RunningJobModel();
                runningJob.setRunningJob(job);
                runningJob.setRunningJobId(job.getJobId());
                //Double check before running a job
                JobModel jobInDb = jobDao.get(job.getJobId(), JobModel.class);
                List<Operation> runningJobQuery = new ArrayList<>();
                Operation jobQuery = new Operation(job.getJobId(), Operation.TYPES.STRING, "runningJobId", Operation.OPERATORS.EQ);
                runningJobQuery.add(jobQuery);
                int runningInstancesCount = runningJobDao.getAllBy(runningJobQuery).size();
                if(jobInDb.getStatus() == JOB_STATUS.DUE && runningInstancesCount == 0) {
                    jobDao.update(job);
                    runningJobDao.create(runningJob);
                }
            }
        }
        runExecutor();
    }

    private boolean shouldRunNWeeklyJob(JobModel model, LocalTime localTime) {
        int dayOfWeek = DateTimeUtil.getCalenderInstance().get(Calendar.DAY_OF_WEEK);
        for(int day : model.getDaysOfWeek()) {
            if(day == dayOfWeek) {
                return shouldRunDailyJob(model, localTime);
            }
        }
        return false;
    }

    private boolean shouldRunNMonthlyJob(JobModel model, LocalTime localTime) {
        int dayOfMonth = DateTimeUtil.getCalenderInstance().get(Calendar.DAY_OF_MONTH);
        for(int day : model.getDaysInMonth()) {
            if(day == dayOfMonth) {
                return shouldRunDailyJob(model, localTime);
            }
        }
        return false;
    }

    private boolean shouldRunLastDayOfMonthJob(JobModel model, LocalTime localTime) {
        Calendar c = DateTimeUtil.getCalenderInstance();
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        int lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        if(dayOfMonth == lastDay) {
            return shouldRunDailyJob(model, localTime);
        }
        return false;
    }

    public static void main(String[] args) {
        Calendar c = DateTimeUtil.getCalenderInstance();
        c.add(Calendar.MONTH, -7);
        System.out.println(DateTimeUtil.getCalenderInstance().getActualMaximum(Calendar.DAY_OF_MONTH));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        System.out.println(LocalTime.now().format(formatter));
    }


    private boolean shouldRunMonthlyJob(JobModel model, LocalTime localTime) {
        int dayOfMonth = DateTimeUtil.getCalenderInstance().get(Calendar.DAY_OF_MONTH);
        if(dayOfMonth == model.getDayOfMonth()) {
            return shouldRunDailyJob(model, localTime);
        }
        return false;
    }

    private boolean shouldRunWeeklyJob(JobModel model, LocalTime localTime) {
        int dayOfWeek = DateTimeUtil.getCalenderInstance().get(Calendar.DAY_OF_WEEK);
        if(dayOfWeek == model.getDayOfWeek()) {
            return shouldRunDailyJob(model, localTime);
        }
        return false;
    }

    private boolean shouldRunOnce(JobModel model, LocalTime localTime) {
        return shouldRunDailyJob(model, localTime);
    }


    private boolean shouldRunDailyJob(JobModel model, LocalTime localTime) {
        return localTime.isBefore(LocalTime.now());
    }


    @Async
    @Scheduled(cron = "0 59 23 * * ?")
    public void resetJobStatus() {
        Calendar calendar = DateTimeUtil.getCalenderInstance();
        //Reset All daily jobs
        List<Operation> dailyJobQueries = new ArrayList<>();
        Operation dailyJobQuery = new Operation(RUN_FREQUENCY.DAILY, RUN_FREQUENCY.class,
            "frequency", Operation.OPERATORS.EQ);
        dailyJobQueries.add(dailyJobQuery);
        jobDao.updateMany(dailyJobQueries, "status", JOB_STATUS.DUE, JobModel.class);

        //Reset all Weekly Jobs
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if(dayOfWeek == 7) {
            List<Operation> weeklyJobQueries = new ArrayList<>();
            Operation weeklyJobQuery = new Operation(RUN_FREQUENCY.WEEKLY, RUN_FREQUENCY.class,
                "frequency", Operation.OPERATORS.EQ);
            Operation nWeeklyJobQuery = new Operation(RUN_FREQUENCY.N_WEEKLY, RUN_FREQUENCY.class,
                "frequency", Operation.OPERATORS.EQ);
            nWeeklyJobQuery.setOrWithPrevious(true);

            weeklyJobQueries.add(weeklyJobQuery);
            weeklyJobQueries.add(nWeeklyJobQuery);
            jobDao.updateMany(weeklyJobQueries, "status", JOB_STATUS.DUE, JobModel.class);
        }

        //Reset all Monthly jobs
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        if(dayOfMonth == lastDayOfMonth) {
            List<Operation> monthlyJobQueries = new ArrayList<>();
            Operation monthlyJobQuery = new Operation(RUN_FREQUENCY.WEEKLY, RUN_FREQUENCY.class,
                "frequency", Operation.OPERATORS.EQ);
            Operation nMonthlyJobQuery = new Operation(RUN_FREQUENCY.N_WEEKLY, RUN_FREQUENCY.class,
                "frequency", Operation.OPERATORS.EQ);
            nMonthlyJobQuery.setOrWithPrevious(true);
            Operation lastDayMonthlyJobQuery = new Operation(RUN_FREQUENCY.LAST_DAY_OF_MONTH, RUN_FREQUENCY.class,
                "frequency", Operation.OPERATORS.EQ);
            lastDayMonthlyJobQuery.setOrWithPrevious(true);

            monthlyJobQueries.add(monthlyJobQuery);
            monthlyJobQueries.add(nMonthlyJobQuery);
            monthlyJobQueries.add(lastDayMonthlyJobQuery);
            jobDao.updateMany(monthlyJobQueries, "status", JOB_STATUS.DUE, JobModel.class);
        }
    }
    
    private void runExecutor() {
        new Thread(() -> jobProcessorExecutorService.start()).start();
    }
}