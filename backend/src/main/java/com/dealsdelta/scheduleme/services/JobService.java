package com.dealsdelta.scheduleme.services;


import com.dealsdelta.scheduleme.data.dao.JobDao;
import com.dealsdelta.scheduleme.data.models.JobModel;
import com.dealsdelta.scheduleme.data.repo.Operation;
import com.dealsdelta.scheduleme.dtos.Job;
import com.dealsdelta.scheduleme.dtos.RUN_FREQUENCY;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 23/08/22
 */

@Service
public class JobService {

    @Autowired
    private JobDao jobDao;

    /**
     * Creates given job
     * @param job
     * @return
     */
    public Job createJob(Job job) {
        validateJob(job);
        JobModel model = new JobModel();
        BeanUtils.copyProperties(job, model);
        return jobDao.create(model);
    }

    /**
     * Updates the given job
     * @param job
     */
    public Job updateJob(Job job) {
        validateJob(job);
        JobModel model = jobDao.get(job.getJobId(), JobModel.class);
        if(model == null)
            throw new IllegalArgumentException("No such job exists");
        BeanUtils.copyProperties(job, model);
        job = jobDao.update(model);
        return job;
    }

    /**
     * Deletes the given job
     * @param jobId
     */
    public void removeJob(String jobId) {
        JobModel model = jobDao.get(jobId, JobModel.class);
        if(model == null)
            throw new IllegalArgumentException("No such job exists");
        jobDao.delete(model);
    }

    /**
     * Returns a job of given Id
     * @param jobId
     * @return
     */
    public Job getJob(String jobId) {
        return jobDao.get(jobId, JobModel.class);
    }

    /**
     * returns count of all existing jobs
     * @return
     */
    public long getAllJobCount() {
        return jobDao.getCount(JobModel.class);
    }

    /**
     * returns list of all jobs runs only once or manually whenever required
     * @return
     */
    public List<Job> getRunOnceJobs() {
        return getJobsByFrequency(RUN_FREQUENCY.ONCE);
    }

    /**
     * returns list of all daily jobs
     * @return
     */
    public List<Job> getDailyJobs() {
        return getJobsByFrequency(RUN_FREQUENCY.DAILY);
    }

    /**
     * returns list of all Weekly jobs
     * @return
     */
    public List<Job> getWeeklyJobs() {
        return getJobsByFrequency(RUN_FREQUENCY.WEEKLY);
    }

    /**
     * returns list of all Weekly jobs that runs multiple times in a week
     * @return
     */
    public List<Job> getNWeeklyJobs() {
        return getJobsByFrequency(RUN_FREQUENCY.N_WEEKLY);
    }

    /**
     * returns list of all Monthly jobs
     * @return
     */
    public List<Job> getMonthlyJobs() {
        return getJobsByFrequency(RUN_FREQUENCY.MONTHLY);
    }

    /**
     * returns list of all Monthly jobs multiple times in a month
     * @return
     */
    public List<Job> getNMonthlyJobs() {
        return getJobsByFrequency(RUN_FREQUENCY.N_MONTHLY);
    }

    /**
     * Returns all jobs that runs on last day of the month
     * @return
     */
    public List<Job> getLastDayOfMonthJobs() {
        return getJobsByFrequency(RUN_FREQUENCY.LAST_DAY_OF_MONTH);
    }

    private List<Job> getJobsByFrequency(RUN_FREQUENCY frequency) {
        Operation operation = new Operation(frequency, RUN_FREQUENCY.class,
            "frequency", Operation.OPERATORS.EQ);
        List<Operation> operations = new ArrayList<>();
        operations.add(operation);
        List<JobModel> models = jobDao.getAllByKey(operations, JobModel.class);
        List<Job> jobs = new ArrayList<>();
        for(JobModel model : models) {
            jobs.add(model);
        }
        return jobs;
    }

    public void validateJob(Job job) {
        if(job.getStartTime() == null) {
            throw new IllegalArgumentException("job must have a start Time");
        }
        if(job.getFrequency() == null)
            throw new IllegalArgumentException("job must have a job frequency");
        switch (job.getFrequency()) {
            case ONCE:
            case LAST_DAY_OF_MONTH:
            case DAILY:
                validateNoDateJob(job);
                break;
            case WEEKLY:
                validateWeeklyJob(job);
                break;
            case N_WEEKLY:
                validateN_WeeklyJob(job);
                break;
            case MONTHLY:
                validateMonthlyJob(job);
                break;
            case N_MONTHLY:
                validateNMonthlyJob(job);
                break;
        }        
    }
    
    private void validateNoDateJob(Job job) {

        boolean valid = job.getDayOfMonth() == 0 && job.getDayOfWeek() == 0 && (job.getDaysInMonth()== null || job.getDaysInMonth().length == 0)
            && (job.getDaysOfWeek() == null || job.getDaysOfWeek().length == 0);
        if(!valid) {
            throw new IllegalArgumentException("Non Repeatable jobs or daily job or job to run on last day of month" +
                " can't have Day of month, " +
                "days of week, days of month or days of week");
        }
        
    }
    
    private void validateWeeklyJob(Job job) {
        boolean valid = job.getDayOfMonth() == 0 && (job.getDaysInMonth()== null || job.getDaysInMonth().length == 0)
            && (job.getDaysOfWeek() == null || job.getDaysOfWeek().length == 0);
        if(!valid) {
            throw new IllegalArgumentException("Weekly jobs can't have Day of month " +
                ", days of month or days of week");
        }
        if(job.getDayOfWeek()> 7 || job.getDayOfWeek() < 1) {
            throw new IllegalArgumentException("Weekly jobs must have day of week between 1-7");
        }
    }

    private void validateN_WeeklyJob(Job job) {
        boolean valid = job.getDayOfMonth() == 0 && job.getDayOfWeek() == 0 &&
            (job.getDaysInMonth()== null || job.getDaysInMonth().length == 0);
        if(!valid) {
            throw new IllegalArgumentException("N_Weekly jobs can't have Day of month " +
                ",Day of week or days of month");
        }
        if(job.getDaysOfWeek().length == 0) {
            throw new IllegalArgumentException("N_Weekly jobs must have days of week");
        }

        for(int dayNum : job.getDaysOfWeek()) {
            if(dayNum > 7 || dayNum < 1) {
                throw new IllegalArgumentException("N_Weekly jobs must have days of week between 1-7");
            }
        }
    }

    private void validateMonthlyJob(Job job) {
        boolean valid = job.getDayOfWeek() == 0 && (job.getDaysInMonth()== null || job.getDaysInMonth().length == 0)
            && (job.getDaysOfWeek() == null || job.getDaysOfWeek().length == 0);
        if(!valid) {
            throw new IllegalArgumentException("Monthly jobs can't have Day of Week " +
                ",Day of week or days of month");
        }
        if(job.getDayOfMonth() >31 || job.getDayOfMonth() <1 ) {
            throw new IllegalArgumentException("Monthly jobs must have day of Month between 1-31");
        }
    }

    private void validateNMonthlyJob(Job job) {
        boolean valid = job.getDayOfMonth() == 0 && job.getDayOfWeek() == 0 &&
            (job.getDaysOfWeek() == null || job.getDaysOfWeek().length == 0);
        if(!valid) {
            throw new IllegalArgumentException("N_Monthly jobs Running more than once can't have Day of Week " +
                ",Day of week or day of month");
        }
        if(job.getDaysInMonth().length == 0 ) {
            throw new IllegalArgumentException("N_Monthly jobs must have days in Month");
        }

        for(int dayNum : job.getDaysInMonth()) {
            if(dayNum > 31 || dayNum < 1) {
                throw new IllegalArgumentException("N_Monthly jobs must have days of week between 1-31");
            }
        }
    }
}