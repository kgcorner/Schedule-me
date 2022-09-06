package com.dealsdelta.scheduleme.resources;

import com.dealsdelta.scheduleme.dtos.Job;
import com.dealsdelta.scheduleme.dtos.RUN_FREQUENCY;
import com.dealsdelta.scheduleme.services.JobService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.when;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 04/09/22
 */

public class JobResourceTest {
    private JobResource resource;
    private JobService service;
    @Before
    public void setUp() {
        resource = new JobResource();
        service = PowerMockito.mock(JobService.class);
        Whitebox.setInternalState(resource, "service", service);
    }

    @Test
    public void getDailyJobs() {
        List<Job> jobs = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Job job = new Job();
            job.setFrequency(RUN_FREQUENCY.DAILY);
            jobs.add(job);
        }
        when(service.getDailyJobs()).thenReturn(jobs);
        List<Job> results = resource.getDailyJobs();
        for(Job j : results) {
            assertEquals(RUN_FREQUENCY.DAILY, j.getFrequency());
        }
    }

    @Test
    public void getMonthlyJobs() {
        List<Job> jobs = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Job job = new Job();
            job.setFrequency(RUN_FREQUENCY.MONTHLY);
            jobs.add(job);
        }
        when(service.getMonthlyJobs()).thenReturn(jobs);
        List<Job> results = resource.getMonthlyJobs();
        for(Job j : results) {
            assertEquals(RUN_FREQUENCY.MONTHLY, j.getFrequency());
        }
    }

    @Test
    public void getNMonthlyJobs() {
        List<Job> jobs = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Job job = new Job();
            job.setFrequency(RUN_FREQUENCY.N_MONTHLY);
            jobs.add(job);
        }
        when(service.getNMonthlyJobs()).thenReturn(jobs);
        List<Job> results = resource.getNMonthlyJobs();
        for(Job j : results) {
            assertEquals(RUN_FREQUENCY.N_MONTHLY, j.getFrequency());
        }
    }

    @Test
    public void getWeeklyJobs() {
        List<Job> jobs = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Job job = new Job();
            job.setFrequency(RUN_FREQUENCY.WEEKLY);
            jobs.add(job);
        }
        when(service.getWeeklyJobs()).thenReturn(jobs);
        List<Job> results = resource.getWeeklyJobs();
        for(Job j : results) {
            assertEquals(RUN_FREQUENCY.WEEKLY, j.getFrequency());
        }
    }

    @Test
    public void getNWeeklyJobs() {
        List<Job> jobs = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Job job = new Job();
            job.setFrequency(RUN_FREQUENCY.N_WEEKLY);
            jobs.add(job);
        }
        when(service.getNWeeklyJobs()).thenReturn(jobs);
        List<Job> results = resource.getNWeeklyJobs();
        for(Job j : results) {
            assertEquals(RUN_FREQUENCY.N_WEEKLY, j.getFrequency());
        }
    }

    @Test
    public void getRunOnceJobs() {
        List<Job> jobs = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Job job = new Job();
            job.setFrequency(RUN_FREQUENCY.ONCE);
            jobs.add(job);
        }
        when(service.getRunOnceJobs()).thenReturn(jobs);
        List<Job> results = resource.getRunOnceJobs();
        for(Job j : results) {
            assertEquals(RUN_FREQUENCY.ONCE, j.getFrequency());
        }
    }

    @Test
    public void getLastDayOfMonthJobs() {
        List<Job> jobs = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Job job = new Job();
            job.setFrequency(RUN_FREQUENCY.LAST_DAY_OF_MONTH);
            jobs.add(job);
        }
        when(service.getLastDayOfMonthJobs()).thenReturn(jobs);
        List<Job> results = resource.getLastDayOfMonthJobs();
        for(Job j : results) {
            assertEquals(RUN_FREQUENCY.LAST_DAY_OF_MONTH, j.getFrequency());
        }
    }

    @Test
    public void getJobCount() {
        long count = 100;
        when(service.getAllJobCount()).thenReturn(count);
        long result = resource.getJobCount();
        assertEquals(count, result);
    }

    @Test
    public void removeJob() {
        String jobId = "jobId";
        doNothing().when(service).removeJob(jobId);
        resource.removeJob(jobId);
    }

    @Test
    public void getJob() {
        Job job = new Job();
        String jobId = "jobId";
        job.setJobId(jobId);
        when(service.getJob(jobId)).thenReturn(job);
        Job result = resource.getJob(jobId);
        assertEquals(jobId, result.getJobId());
    }

    @Test
    public void updateJob() {
        Job job = new Job();
        String jobId = "jobId";
        job.setJobId(jobId);
        String updatedName = "UpdatedName";
        when(service.updateJob(job)).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object argument = invocationOnMock.getArgument(0);
                Job j = (Job) argument;
                j.setJobName(updatedName);
                return j;
            }
        });
        Job result = resource.updateJob(job);
        assertEquals(updatedName, result.getJobName());
    }


}