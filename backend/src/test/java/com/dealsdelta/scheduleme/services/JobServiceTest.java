package com.dealsdelta.scheduleme.services;

import com.dealsdelta.scheduleme.data.dao.JobAuditDao;
import com.dealsdelta.scheduleme.data.dao.JobDao;
import com.dealsdelta.scheduleme.data.dao.LogDao;
import com.dealsdelta.scheduleme.data.dao.RunningJobDao;
import com.dealsdelta.scheduleme.data.models.JobModel;
import com.dealsdelta.scheduleme.data.repo.Operation;
import com.dealsdelta.scheduleme.dtos.Job;
import com.dealsdelta.scheduleme.dtos.RUN_FREQUENCY;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.powermock.api.mockito.PowerMockito.when;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 23/08/22
 */


public class JobServiceTest {

    private Job job;
    private JobService jobService;
    private JobDao jobDao;

    @Before
    public void setUp() {
        job = new Job();
        job.setFrequency(RUN_FREQUENCY.ONCE);
        jobService = new JobService();
        jobDao = PowerMockito.mock(JobDao.class);
        Whitebox.setInternalState(jobService,  "jobDao", jobDao);
    }
    
    @Test
    public void createJob() {
        Job job = new Job();
        job.setStartTime(LocalTime.now());
        job.setFrequency(RUN_FREQUENCY.ONCE);
        JobModel model = new JobModel();
        when(jobDao.create(any(JobModel.class))).thenReturn(model);
        Job result = jobService.createJob(job);
        assertEquals(model, result);
    }

    @Test
    public void testNoStartTimeJob() {
        try {
            jobService.validateJob(job);
            fail("Job with no start time is processed");
        } catch (IllegalArgumentException x) {
            assertEquals( "job must have a start Time", x.getMessage());
        }
    }

    @Test
    public void testNoJobFrequency() {
        try {
            Job job = new Job();
            job.setStartTime(LocalTime.now());
            jobService.validateJob(job);
            fail("Job with no frequency is processed");
        } catch (IllegalArgumentException x) {
            assertEquals( "job must have a job frequency", x.getMessage());
        }
    }

    @Test
    public void validateNoDateJob() {
        job.setFrequency(RUN_FREQUENCY.ONCE);
        job.setStartTime(LocalTime.now());
        job.setDayOfMonth(10);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            assertEquals( "Non Repeatable jobs or daily job or job to run on last day of month" +
                " can't have Day of month, " +
                "days of week, days of month or days of week", x.getMessage());
        }
        job.setFrequency(RUN_FREQUENCY.LAST_DAY_OF_MONTH);
        job.setDayOfMonth(0);
        job.setDayOfWeek(2);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            assertEquals( "Non Repeatable jobs or daily job or job to run on last day of month" +
                " can't have Day of month, " +
                "days of week, days of month or days of week", x.getMessage());
        }
        job.setFrequency(RUN_FREQUENCY.DAILY);
        job.setDayOfMonth(0);
        job.setDayOfWeek(0);
        int[] daysArray = {4,5};
        job.setDaysInMonth(daysArray);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            assertEquals( "Non Repeatable jobs or daily job or job to run on last day of month" +
                " can't have Day of month, " +
                "days of week, days of month or days of week", x.getMessage());
        }

        job.setDayOfMonth(0);
        job.setDayOfWeek(0);
        int[] emptyArray = {};
        job.setDaysInMonth(emptyArray);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            assertEquals( "Non Repeatable jobs or daily job or job to run on last day of month" +
                " can't have Day of month, " +
                "days of week, days of month or days of week", x.getMessage());
        }

        job.setDayOfMonth(0);
        job.setDayOfWeek(0);
        job.setDaysInMonth(null);
        job.setDaysOfWeek(daysArray);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            assertEquals( "Non Repeatable jobs or daily job or job to run on last day of month" +
                " can't have Day of month, " +
                "days of week, days of month or days of week", x.getMessage());
        }

        job.setDayOfMonth(0);
        job.setDayOfWeek(0);
        job.setDaysInMonth(null);
        job.setDaysOfWeek(emptyArray);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            assertEquals( "Non Repeatable jobs or daily job or job to run on last day of month" +
                " can't have Day of month, " +
                "days of week, days of month or days of week", x.getMessage());
        }
    }

    @Test
    public void validateWeeklyJob() {
        job.setFrequency(RUN_FREQUENCY.WEEKLY);
        job.setStartTime(LocalTime.now());
        job.setDayOfMonth(10);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            assertEquals( "Weekly jobs can't have Day of month " +
                ", days of month or days of week", x.getMessage());
        }
        int[] array = {9,2};
        int[] emptyArray = {};
        job.setDayOfMonth(0);
        job.setDaysInMonth(array);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            assertEquals( "Weekly jobs can't have Day of month " +
                ", days of month or days of week", x.getMessage());
        }
        job.setDaysInMonth(emptyArray);
        job.setDaysOfWeek(array);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            assertEquals( "Weekly jobs can't have Day of month " +
                ", days of month or days of week", x.getMessage());
        }


        job.setDaysOfWeek(emptyArray);
        job.setDaysInMonth(emptyArray);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            assertEquals( "Weekly jobs must have day of week between 1-7", x.getMessage());
        }
        job.setDayOfWeek(0);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            assertEquals( "Weekly jobs must have day of week between 1-7", x.getMessage());
        }
        job.setDayOfWeek(10);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            assertEquals( "Weekly jobs must have day of week between 1-7", x.getMessage());
        }
        job.setDaysOfWeek(null);
        job.setDaysInMonth(null);
        job.setDayOfWeek(5);
        jobService.validateJob(job);
    }

    @Test
    public void validateN_WeeklyJob() {
        job.setFrequency(RUN_FREQUENCY.N_WEEKLY);
        job.setStartTime(LocalTime.now());
        job.setDayOfMonth(10);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            assertEquals("N_Weekly jobs can't have Day of month " +
                ",Day of week or days of month", x.getMessage());
        }
        int[] array = {5,2};
        int[] emptyArray = {};
        job.setDayOfMonth(0);
        job.setDaysInMonth(array);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            assertEquals( "N_Weekly jobs can't have Day of month " +
                ",Day of week or days of month", x.getMessage());
        }
        job.setDaysInMonth(emptyArray);
        job.setDayOfWeek(10);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            assertEquals( "N_Weekly jobs can't have Day of month " +
                ",Day of week or days of month", x.getMessage());
        }

        job.setDaysInMonth(emptyArray);
        job.setDayOfWeek(0);
        job.setDaysOfWeek(emptyArray);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            assertEquals( "N_Weekly jobs must have days of week",x.getMessage());
        }
        int[] invalidArray = {9, 5};
        job.setDayOfWeek(0);
        job.setDaysOfWeek(invalidArray);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            assertEquals( "N_Weekly jobs must have days of week between 1-7", x.getMessage());
        }

        int[] invalidArray2 = {0, 5};
        job.setDayOfWeek(0);
        job.setDaysOfWeek(invalidArray2);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            assertEquals( "N_Weekly jobs must have days of week between 1-7", x.getMessage());
        }
        job.setDaysInMonth(null);
        job.setDaysOfWeek(array);
        jobService.validateJob(job);
    }


    @Test
    public void validateN_MonthlyJob() {
        job.setFrequency(RUN_FREQUENCY.N_MONTHLY);
        job.setStartTime(LocalTime.now());
        int[] array = {9,2};
        int[] emptyArray = {};
        job.setDaysOfWeek(array);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            assertEquals( "N_Monthly jobs Running more than once can't have Day of Week " +
                ",Day of week or day of month", x.getMessage());
        }
        job.setDaysOfWeek(emptyArray);
        job.setDayOfMonth(10);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            assertEquals("N_Monthly jobs Running more than once can't have Day of Week " +
                ",Day of week or day of month", x.getMessage());
        }
        job.setDayOfMonth(0);
        job.setDaysOfWeek(emptyArray);
        job.setDayOfWeek(10);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            assertEquals( "N_Monthly jobs Running more than once can't have Day of Week " +
                ",Day of week or day of month", x.getMessage());
        }

        job.setDaysInMonth(emptyArray);
        job.setDayOfWeek(0);
        job.setDaysOfWeek(emptyArray);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            assertEquals( "N_Monthly jobs must have days in Month",x.getMessage());
        }
        int[] invalidArray = {69, 5};
        job.setDayOfWeek(0);
        job.setDaysInMonth(invalidArray);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            assertEquals( "N_Monthly jobs must have days of week between 1-31", x.getMessage());
        }

        int[] invalidArray2 = {0, 5};
        job.setDayOfWeek(0);
        job.setDaysInMonth(invalidArray2);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            assertEquals( "N_Monthly jobs must have days of week between 1-31", x.getMessage());
        }
        job.setDaysOfWeek(null);
        job.setDaysInMonth(array);
        jobService.validateJob(job);
    }

    @Test
    public void validateMonthlyJob() {
        job.setFrequency(RUN_FREQUENCY.MONTHLY);
        job.setStartTime(LocalTime.now());
        int[] array = {9,2};
        int[] emptyArray = {};
        job.setDaysOfWeek(array);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            assertEquals("Monthly jobs can't have Day of Week " +
                ",Day of week or days of month", x.getMessage());
        }

        job.setDaysOfWeek(emptyArray);
        job.setDaysInMonth(array);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            assertEquals( "Monthly jobs can't have Day of Week " +
                ",Day of week or days of month", x.getMessage());
        }
        job.setDaysInMonth(emptyArray);
        job.setDayOfWeek(5);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            assertEquals("Monthly jobs can't have Day of Week " +
                ",Day of week or days of month", x.getMessage());
        }
        job.setDaysInMonth(emptyArray);
        job.setDayOfWeek(0);
        job.setDaysOfWeek(emptyArray);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            assertEquals( "Monthly jobs must have day of Month between 1-31",x.getMessage());
        }
        job.setDayOfWeek(0);
        job.setDayOfMonth(69);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            assertEquals( "Monthly jobs must have day of Month between 1-31", x.getMessage());
        }

        job.setDayOfWeek(0);
        job.setDayOfMonth(0);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            assertEquals( "Monthly jobs must have day of Month between 1-31", x.getMessage());
        }
        job.setDaysInMonth(null);
        job.setDaysOfWeek(null);
        job.setDayOfMonth(10);
        jobService.validateJob(job);
    }

    @Test
    public void updateJob() {
        String jobId = "existingJobId";
        job.setJobId(jobId);
        JobModel model = new JobModel();
        model.setJobName("Updated value");
        job.setStartTime(LocalTime.now());
        when(jobDao.get(jobId, JobModel.class)).thenReturn(model);
        when(jobDao.update(any(JobModel.class))).thenReturn(model);
        Job result = jobService.updateJob(job);
        assertEquals(model.getJobName(), result.getJobName());
    }

    @Test
    public void updateInvalidJob() {
        String jobId = "nonExistingJobId";
        job.setJobId(jobId);
        JobModel model = new JobModel();
        model.setJobName("Updated value");
        job.setStartTime(LocalTime.now());
        when(jobDao.get(jobId, JobModel.class)).thenReturn(null);
        try {
            jobService.updateJob(job);
        } catch (IllegalArgumentException x) {
            String error = "No such job exists";
            assertEquals(error, x.getMessage());
        }
    }

    @Test
    public void removeJob() {
        String jobId = "existingJob";
        JobModel model = new JobModel();
        when(jobDao.get(jobId, JobModel.class)).thenReturn(model);
        jobService.removeJob(jobId);
    }

    @Test
    public void removeJobInvalidId() {
        String jobId = "nonExistingJob";
        when(jobDao.get(jobId, JobModel.class)).thenReturn(null);
        try {
            jobService.removeJob(jobId);
        } catch (IllegalArgumentException x) {
            String error = "No such job exists";
            assertEquals(error, x.getMessage());
        }
    }

    @Test
    public void getJob() {
        String jobId = "existingJob";
        JobModel model = new JobModel();
        model.setJobId(jobId);
        when(jobDao.get(jobId, JobModel.class)).thenReturn(model);
        Job result = jobService.getJob(jobId);
        assertEquals(jobId, result.getJobId());
    }

    @Test
    public void getAllJobCount() {
        long count = 10;
        when(jobDao.getCount(JobModel.class)).thenReturn(count);
        long result = jobService.getAllJobCount();
        assertEquals(count, result);
    }

    private List<Operation> getFrequencyOperation(RUN_FREQUENCY frequency) {
        Operation operation = new Operation(frequency, RUN_FREQUENCY.class,
            "frequency", Operation.OPERATORS.EQ);
        List<Operation> operations = new ArrayList<>();
        operations.add(operation);
        return operations;

    }

    @Test
    public void getRunOnceJobs() {
        List<JobModel> models = prepareJobModels();
        List<Operation> operations = getFrequencyOperation(RUN_FREQUENCY.ONCE);
        when(jobDao.getAllByKey(operations, JobModel.class)).thenReturn(models);
        List<Job> result = jobService.getRunOnceJobs();
        assertEquals(models.size(), result.size());
    }

    private List<JobModel> prepareJobModels() {
        List<JobModel> models = new ArrayList<>();
        for(int i = 0; i < 10; i++)
            models.add(new JobModel());
        return models;
    }

    @Test
    public void getDailyJobs() {
        List<JobModel> models = prepareJobModels();
        List<Operation> operations = getFrequencyOperation(RUN_FREQUENCY.DAILY);
        when(jobDao.getAllByKey(operations, JobModel.class)).thenReturn(models);
        List<Job> result = jobService.getDailyJobs();
        assertEquals(models.size(), result.size());
    }

    @Test
    public void getWeeklyJobs() {
        List<JobModel> models = prepareJobModels();
        List<Operation> operations = getFrequencyOperation(RUN_FREQUENCY.WEEKLY);
        when(jobDao.getAllByKey(operations, JobModel.class)).thenReturn(models);
        List<Job> result = jobService.getWeeklyJobs();
        assertEquals(models.size(), result.size());
    }

    @Test
    public void getNWeeklyJobs() {
        List<JobModel> models = prepareJobModels();
        List<Operation> operations = getFrequencyOperation(RUN_FREQUENCY.N_WEEKLY);
        when(jobDao.getAllByKey(operations, JobModel.class)).thenReturn(models);
        List<Job> result = jobService.getNWeeklyJobs();
        assertEquals(models.size(), result.size());
    }

    @Test
    public void getMonthlyJobs() {
        List<JobModel> models = prepareJobModels();
        List<Operation> operations = getFrequencyOperation(RUN_FREQUENCY.MONTHLY);
        when(jobDao.getAllByKey(operations, JobModel.class)).thenReturn(models);
        List<Job> result = jobService.getMonthlyJobs();
        assertEquals(models.size(), result.size());
    }

    @Test
    public void getNMonthlyJobs() {
        List<JobModel> models = prepareJobModels();
        List<Operation> operations = getFrequencyOperation(RUN_FREQUENCY.N_MONTHLY);
        when(jobDao.getAllByKey(operations, JobModel.class)).thenReturn(models);
        List<Job> result = jobService.getNMonthlyJobs();
        assertEquals(models.size(), result.size());
    }

    @Test
    public void getLastDayOfMonthJobs() {
        List<JobModel> models = prepareJobModels();
        List<Operation> operations = getFrequencyOperation(RUN_FREQUENCY.LAST_DAY_OF_MONTH);
        when(jobDao.getAllByKey(operations, JobModel.class)).thenReturn(models);
        List<Job> result = jobService.getLastDayOfMonthJobs();
        assertEquals(models.size(), result.size());
    }

    @Test
    public void testGetters() {
        JobDao jobDao = new JobDao();
        LogDao logDao = new LogDao();
        LogService logService = new LogService();
        RunningJobDao runningJobDao = new RunningJobDao();
        JobAuditDao jobAuditDao = new JobAuditDao();
        Whitebox.setInternalState(jobService, "jobDao", jobDao);
        Whitebox.setInternalState(jobService, "logDao", logDao);
        Whitebox.setInternalState(jobService, "logService", logService);
        Whitebox.setInternalState(jobService, "runningJobDao", runningJobDao);
        Whitebox.setInternalState(jobService, "jobAuditDao", jobAuditDao);
        assertEquals(jobDao, jobService.getJobDao());
        assertEquals(logDao, jobService.getLogDao());
        assertEquals(logService, jobService.getLogService());
        assertEquals(runningJobDao, jobService.getRunningJobDao());
        assertEquals(jobAuditDao, jobService.getJobAuditDao());
    }
}