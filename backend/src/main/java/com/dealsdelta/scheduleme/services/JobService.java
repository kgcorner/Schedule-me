package com.dealsdelta.scheduleme.services;


import com.dealsdelta.scheduleme.data.dao.JobDao;
import com.dealsdelta.scheduleme.dtos.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 23/08/22
 */

@Service
public class JobService {

    @Autowired
    private JobDao jobDao;

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