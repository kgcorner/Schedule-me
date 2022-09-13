package com.dealsdelta.scheduleme.services;

import com.dealsdelta.scheduleme.data.dao.JobDao;
import com.dealsdelta.scheduleme.data.dao.RunningJobDao;
import com.dealsdelta.scheduleme.data.models.JobModel;
import com.dealsdelta.scheduleme.data.models.RunningJobModel;
import com.dealsdelta.scheduleme.data.repo.Operation;
import com.dealsdelta.scheduleme.dtos.JOB_STATUS;
import com.dealsdelta.scheduleme.dtos.RUN_FREQUENCY;
import com.dealsdelta.scheduleme.executors.JobProcessorExecutorService;
import com.dealsdelta.scheduleme.util.DateTimeUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 08/09/22
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(DateTimeUtil.class)
public class JobScannerServiceTest {

    private JobScannerService service;
    private JobDao jobDao;
    private RunningJobDao runningJobDao;
    private final static String MOCKED_JOB_ID = "jobId";
    private JobProcessorExecutorService jobProcessorExecutorService;

    @Before
    public void setUp() throws Exception {
        service = new JobScannerService();
        jobDao = mock(JobDao.class);
        runningJobDao = mock(RunningJobDao.class);
        jobProcessorExecutorService = mock(JobProcessorExecutorService.class);
        Whitebox.setInternalState(service, "jobDao", jobDao);
        Whitebox.setInternalState(service, "runningJobDao", runningJobDao);
        Whitebox.setInternalState(service, "jobProcessorExecutorService", jobProcessorExecutorService);

    }

    @Test
    public void scanJobs() {
        LocalTime localTime = LocalTime.now();
        Operation status = new Operation(JOB_STATUS.DUE, JOB_STATUS.class, "status",
            Operation.OPERATORS.EQ);

        Operation time = new Operation(DateTimeUtil.getTime(localTime), Operation.TYPES.STRING, "startTimeStr",
            Operation.OPERATORS.EQ);
        List<Operation> operations = new ArrayList<>();
        operations.add(status);
        operations.add(time);
        int runOnceJobCount = 8;
        int dailyJobsCount = 10;
        int weeklyJobsCount = 5;
        int nWeeklyJobsCount = 8;
        int monthlyJobsCount = 6;
        int nMonthlyJobsCount = 8;
        int lastDayOfMonthJobsCount = 8;
        int totalJobsThatShouldRun = runOnceJobCount +
            dailyJobsCount + weeklyJobsCount +
            nWeeklyJobsCount + monthlyJobsCount + nMonthlyJobsCount + lastDayOfMonthJobsCount;

        //Prepare JobsList
        List<JobModel> jobs = new ArrayList<>();

        //Add Run Once jobs
        createMockedJobs(localTime, jobs, runOnceJobCount, RUN_FREQUENCY.ONCE);

        //Add daily jobs
        createMockedJobs(localTime, jobs, dailyJobsCount, RUN_FREQUENCY.DAILY);

        int dayOfWeek = 3;
        //add weeklyJobs
        createMockedJobs(localTime, jobs, weeklyJobsCount, RUN_FREQUENCY.WEEKLY, dayOfWeek);

        int[] weekDays = {3,4,5};
        //add nWeekly jobs
        createMockedJobs(localTime, jobs, nWeeklyJobsCount, RUN_FREQUENCY.N_WEEKLY, weekDays);

        int dayOfMonth = 31;
        //add Monthly jobs
        createMockedJobs(localTime, jobs, monthlyJobsCount, RUN_FREQUENCY.MONTHLY, dayOfMonth);

        int[] monthDays = {10,14,31};
        //add nMonthly jobs
        createMockedJobs(localTime, jobs, nMonthlyJobsCount, RUN_FREQUENCY.N_MONTHLY, monthDays);

        //add LAST_DAY_OF_MONTH jobs
        createMockedJobs(localTime, jobs, lastDayOfMonthJobsCount, RUN_FREQUENCY.LAST_DAY_OF_MONTH);

        JobModel mockedDueStatusJob = new JobModel();
        mockedDueStatusJob.setStatus(JOB_STATUS.DUE);
        when(jobDao.getAllByKey(operations, JobModel.class)).thenReturn(jobs);
        List<Operation> runningJobQuery = new ArrayList<>();
        Operation jobQuery = new Operation(MOCKED_JOB_ID, Operation.TYPES.STRING, "runningJobId", Operation.OPERATORS.EQ);
        runningJobQuery.add(jobQuery);
        when(jobDao.get(MOCKED_JOB_ID, JobModel.class)).thenReturn(mockedDueStatusJob);
        when(runningJobDao.getAllBy(runningJobQuery)).thenReturn(new ArrayList<>());
        Calendar calendar = mock(Calendar.class);
        mockStatic(DateTimeUtil.class);
        when(DateTimeUtil.getCalenderInstance()).thenReturn(calendar);
        when(calendar.get(Calendar.DAY_OF_WEEK)).thenReturn(dayOfWeek);
        when(calendar.get(Calendar.DAY_OF_MONTH)).thenReturn(dayOfMonth);
        when(calendar.getActualMaximum(Calendar.DAY_OF_MONTH)).thenReturn(dayOfMonth);
        when(DateTimeUtil.getTime(ArgumentMatchers.any(LocalTime.class))).thenCallRealMethod();
        service.scanJobs();
        Mockito.verify(jobDao, Mockito.times(totalJobsThatShouldRun)).update(ArgumentMatchers.any(JobModel.class));
        Mockito.verify(runningJobDao, Mockito.times(totalJobsThatShouldRun)).create(ArgumentMatchers.any(RunningJobModel.class));
        Mockito.verify(jobProcessorExecutorService, Mockito.times(1)).start();

        when(calendar.get(Calendar.DAY_OF_WEEK)).thenReturn(0);
        when(calendar.get(Calendar.DAY_OF_MONTH)).thenReturn(0);
        when(calendar.getActualMaximum(Calendar.DAY_OF_MONTH)).thenReturn(31);
        service.scanJobs();
        Mockito.verify(jobDao, Mockito.times(totalJobsThatShouldRun + dailyJobsCount + runOnceJobCount)).update(ArgumentMatchers.any(JobModel.class));
        Mockito.verify(runningJobDao, Mockito.times(totalJobsThatShouldRun  + dailyJobsCount + runOnceJobCount)).create(ArgumentMatchers.any(RunningJobModel.class));
        Mockito.verify(jobProcessorExecutorService, Mockito.times(2)).start();
    }

    private void createMockedJobs(LocalTime localTime, List<JobModel> jobs, int i2, RUN_FREQUENCY daily) {
        for (int i = 0; i < i2; i++) {
            JobModel jobModel = new JobModel();
            jobModel.setJobId(MOCKED_JOB_ID);
            jobModel.setStartTime(localTime);
            jobModel.setStatus(JOB_STATUS.DUE);
            jobModel.setFrequency(daily);
            jobs.add(jobModel);
        }
    }

    private void createMockedJobs(LocalTime localTime, List<JobModel> jobs, int i2, RUN_FREQUENCY frequency, int[] days) {
        for (int i = 0; i < i2; i++) {
            JobModel jobModel = new JobModel();
            jobModel.setJobId(MOCKED_JOB_ID);
            jobModel.setStartTime(localTime);
            jobModel.setStatus(JOB_STATUS.DUE);
            jobModel.setFrequency(frequency);
            jobs.add(jobModel);
            if(frequency == RUN_FREQUENCY.N_WEEKLY)
                jobModel.setDaysOfWeek(days);
            else
                jobModel.setDaysInMonth(days);
        }
    }

    private void createMockedJobs(LocalTime localTime, List<JobModel> jobs, int i2, RUN_FREQUENCY frequency, int day) {
        for (int i = 0; i < i2; i++) {
            JobModel jobModel = new JobModel();
            jobModel.setJobId(MOCKED_JOB_ID);
            jobModel.setStartTime(localTime);
            jobModel.setStatus(JOB_STATUS.DUE);
            jobModel.setFrequency(frequency);
            jobs.add(jobModel);
            if(frequency == RUN_FREQUENCY.WEEKLY)
                jobModel.setDayOfWeek(day);
            else
                jobModel.setDayOfMonth(day);
        }
    }

    @Test
    public void resetJobStatus() {
        PowerMockito.mockStatic(DateTimeUtil.class);
        Calendar calendar = PowerMockito.mock(Calendar.class);
        when(DateTimeUtil.getCalenderInstance()).thenReturn(calendar);

        //Days query
        List<Operation> dailyJobQueries = new ArrayList<>();
        Operation dailyJobQuery = new Operation(RUN_FREQUENCY.DAILY, RUN_FREQUENCY.class,
            "frequency", Operation.OPERATORS.EQ);
        dailyJobQueries.add(dailyJobQuery);

        //Weeks query
        List<Operation> weeklyJobQueries = new ArrayList<>();
        Operation weeklyJobQuery = new Operation(RUN_FREQUENCY.WEEKLY, RUN_FREQUENCY.class,
            "frequency", Operation.OPERATORS.EQ);
        Operation nWeeklyJobQuery = new Operation(RUN_FREQUENCY.N_WEEKLY, RUN_FREQUENCY.class,
            "frequency", Operation.OPERATORS.EQ);
        nWeeklyJobQuery.setOrWithPrevious(true);

        weeklyJobQueries.add(weeklyJobQuery);
        weeklyJobQueries.add(nWeeklyJobQuery);


        //Months query
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

        when(calendar.get(Calendar.DAY_OF_MONTH)).thenReturn(31);
        when(calendar.get(Calendar.DAY_OF_WEEK)).thenReturn(7);
        when(calendar.getActualMaximum(Calendar.DAY_OF_MONTH)).thenReturn(31);
        Calendar calendar1 = Calendar.getInstance();
        System.out.println(calendar1);
        service.resetJobStatus();

        Mockito.verify(jobDao, Mockito.times(1)).updateMany(dailyJobQueries, "status", JOB_STATUS.DUE, JobModel.class);
        Mockito.verify(jobDao, Mockito.times(1)).updateMany(weeklyJobQueries, "status", JOB_STATUS.DUE, JobModel.class);
        Mockito.verify(jobDao, Mockito.times(1)).updateMany(monthlyJobQueries, "status", JOB_STATUS.DUE, JobModel.class);
        when(calendar.get(Calendar.DAY_OF_WEEK)).thenReturn(0);
        when(calendar.getActualMaximum(Calendar.DAY_OF_MONTH)).thenReturn(0);
        service.resetJobStatus();
        Mockito.verify(jobDao, Mockito.times(2)).updateMany(dailyJobQueries, "status", JOB_STATUS.DUE, JobModel.class);
        Mockito.verify(jobDao, Mockito.times(1)).updateMany(weeklyJobQueries, "status", JOB_STATUS.DUE, JobModel.class);
        Mockito.verify(jobDao, Mockito.times(1)).updateMany(monthlyJobQueries, "status", JOB_STATUS.DUE, JobModel.class);
    }
}