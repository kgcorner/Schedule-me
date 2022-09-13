package com.dealsdelta.scheduleme.resources;


import com.dealsdelta.scheduleme.dtos.*;
import com.dealsdelta.scheduleme.services.JobService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 24/06/22
 */

@RestController
public class JobResource {

    @Autowired
    private JobService service;

    @GetMapping("/daily-jobs")
    public List<Job> getDailyJobs() {
       return service.getDailyJobs();
    }

    @GetMapping("/monthly-jobs")
    public List<Job> getMonthlyJobs() {
        return service.getMonthlyJobs();
    }

    @GetMapping("/nmonthly-jobs")
    public List<Job> getNMonthlyJobs() {
        return service.getNMonthlyJobs();
    }

    @GetMapping("/weekly-jobs")
    public List<Job> getWeeklyJobs() {
        return service.getWeeklyJobs();
    }

    @GetMapping("/nweekly-jobs")
    public List<Job> getNWeeklyJobs() {
        return service.getNWeeklyJobs();
    }

    @GetMapping("/run-once-jobs")
    public List<Job> getRunOnceJobs() {
        return service.getRunOnceJobs();
    }

    @GetMapping("/last-day-of-month-jobs")
    public List<Job> getLastDayOfMonthJobs() {
        return service.getLastDayOfMonthJobs();
    }

    @GetMapping("/jobs-count")
    public long getJobCount() {
        return service.getAllJobCount();
    }

    @DeleteMapping("/jobs/{jobId}")
    public void removeJob(@PathVariable("jobId") String jobId) {
        service.removeJob(jobId);
    }

    @GetMapping("/jobs/{jobId}")
    public Job getJob(@PathVariable("jobId") String jobId) {
       return service.getJob(jobId);
    }

    @PutMapping("/jobs")
    public Job updateJob(@RequestBody Job job) {
        return service.updateJob(job);
    }




}