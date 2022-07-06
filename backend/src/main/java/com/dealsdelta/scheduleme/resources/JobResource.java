package com.dealsdelta.scheduleme.resources;


import com.dealsdelta.scheduleme.dtos.*;
import com.dealsdelta.scheduleme.services.TaskService;
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
    private TaskService service;

    @GetMapping
    public List<IJob> getJobs(@RequestParam("status") String status) {
        return service.getJobs(status);
    }

    @GetMapping("/jobs/{jobId}")
    public IJob getJob(@PathVariable("jobId") String jobId) {
        return service.getJob(jobId);
    }

    @PostMapping("/daily-jobs")
    public DailyJob createDailyJob(@RequestBody DailyJob job) {
        return service.createDailyJob(job);
    }

    @GetMapping("/daily-jobs")
    public List<DailyJob> getDailyJobs() {
        return service.getDailyJobs();
    }

    @PutMapping("/daily-jobs/{jobId}")
    public DailyJob runDailyJob(@PathVariable("jobId") String jobId) {
        return service.runDailyJob(jobId);
    }

    @DeleteMapping("/daily-jobs/{jobId}")
    public void deleteDailyJobs(@PathVariable("jobId") String jobId) {
        service.deleteDailyJob(jobId);
    }

    @PostMapping("/hourly-jobs")
    public HourlyJob createHourly(@RequestBody HourlyJob job) {
        return service.createHourlyJob(job);
    }

    @GetMapping("/hourly-jobs")
    public List<HourlyJob> getHourlyJobs() {
        return service.getHourlyJobs();
    }

    @DeleteMapping("/hourly-jobs/{jobId}")
    public void deleteHourlyJobs(@PathVariable("jobId") String jobId) {
        service.deleteHourlyJob(jobId);
    }

    @PutMapping("/hourly-jobs/{jobId}")
    public HourlyJob runHourlyJob(@PathVariable("jobId") String jobId) {
        return service.runHourlyJob(jobId);
    }

    @PostMapping("/monthly-jobs")
    public MonthlyJob createMonthly(@RequestBody MonthlyJob job) {
        return service.createMonthlyJob(job);
    }

    @GetMapping("/monthly-jobs")
    public List<MonthlyJob> getMonthlyJobs() {
        return service.getMonthlyJobs();
    }

    @DeleteMapping("/monthly-jobs/{jobId}")
    public void deleteMonthlyJobs(@PathVariable("jobId") String jobId) {
        service.deleteMonthlyJob(jobId);
    }

    @PutMapping("/monthly-jobs/{jobId}")
    public MonthlyJob runMonthlyJob(@PathVariable("jobId") String jobId) {
        return service.runMonthlyJob(jobId);
    }

    @PostMapping("/record-processor-jobs")
    public RecordProcessorJob createRecordProcessorJob(@RequestBody RecordProcessorJob job) {
        return service.createRecordProcessorJob(job);
    }

    @GetMapping("/record-processor-jobs")
    public List<RecordProcessorJob> getRecordProcessorJobs() {
        return service.getRecordProcessorJobs();
    }

    @DeleteMapping("/record-processor-jobs/{jobId}")
    public void deleteRecordProcessorJobs(@PathVariable("jobId") String jobId) {
        service.deleteRecordProcessorJob(jobId);
    }

    @PutMapping("/record-processor-jobs/{jobId}")
    public RecordProcessorJob runRecordProcessorJob(@PathVariable("jobId") String jobId) {
        return service.runRecordProcessorJob(jobId);
    }

    @PostMapping("/generic-jobs")
    public GenericJob createDailyJob(@RequestBody GenericJob job) {
        return service.createGenericJob(job);
    }

    @GetMapping("/generic-jobs")
    public List<GenericJob> getGenericJobs() {
        return service.getGenericJobs();
    }

    @DeleteMapping("/generic-jobs/{jobId}")
    public void deleteGenericJobs(@PathVariable("jobId") String jobId) {
        service.deleteGenericJob(jobId);
    }

    @PutMapping("/generic-jobs/{jobId}")
    public GenericJob runGenericJob(@PathVariable("jobId") String jobId) {
        return service.runGenericJob(jobId);
    }


    @PostMapping("/record-processor-daily-jobs")
    public RecordProcessorDailyJob createRecordProcessorDailyJobs(@RequestBody RecordProcessorDailyJob job) {
        return service.createRecordProcessorHourlyJob(job);
    }

    @GetMapping("/record-processor-daily-jobs")
    public List<RecordProcessorDailyJob> getRecordProcessorDailyJobs() {
        return service.getRecordProcessorDailyJobs();
    }

    @DeleteMapping("/record-processor-daily-jobs/{jobId}")
    public void deleteRecordProcessorDailyJobs(@PathVariable("jobId") String jobId) {
        service.deleteRecordProcessorDailyJob(jobId);
    }

    @PutMapping("/record-processor-daily-jobs/{jobId}")
    public RecordProcessorDailyJob runRecordProcessorDailyJob(@PathVariable("jobId") String jobId) {
        return service.runRecordProcessorDailyJob(jobId);
    }

    @PostMapping("/record-processor-monthly-jobs")
    public RecordProcessorMonthlyJob createRecordProcessorMonthlyJob(@RequestBody RecordProcessorMonthlyJob job) {
        return service.createRecordProcessorHourlyJob(job);
    }

    @GetMapping("/record-processor-monthly-jobs")
    public List<RecordProcessorMonthlyJob> getRecordProcessorMonthlyJobs() {
        return service.getRecordProcessorMonthlyJobs();
    }

    @DeleteMapping("/record-processor-monthly-jobs/{jobId}")
    public void deleteRecordProcessorMonthlyJobs(@PathVariable("jobId") String jobId) {
        service.deleteRecordProcessorMonthlyJob(jobId);
    }

    @PutMapping("/record-processor-monthly-jobs/{jobId}")
    public RecordProcessorMonthlyJob runRecordProcessorMonthlyJob(@PathVariable("jobId") String jobId) {
        return service.runRecordProcessorMonthlyJob(jobId);
    }

    @PostMapping("/record-processor-hourly-jobs")
    public RecordProcessorHourlyJob createRecordProcessorHourlyJob(@RequestBody RecordProcessorHourlyJob job) {
        return service.createRecordProcessorHourlyJob(job);
    }

    @GetMapping("/record-processor-hourly-jobs")
    public List<RecordProcessorHourlyJob> getRecordProcessorHourlyJobs() {
        return service.getRecordProcessorHourlyJobs();
    }

    @DeleteMapping("/record-processor-hourly-jobs/{jobId}")
    public void deleteRecordProcessorHourlyJobs(@PathVariable("jobId") String jobId) {
        service.deleteRecordProcessorHourlyJob(jobId);
    }

    @PutMapping("/record-processor-hourly-jobs/{jobId}")
    public RecordProcessorHourlyJob runRecordProcessorHourlyJob(@PathVariable("jobId") String jobId) {
        return service.runRecordProcessorHourlyJob(jobId);
    }

    @GetMapping("/jobs/{jobId}/logs")
    public List<Log> getJobLogs(@PathVariable String jobId, @RequestParam(name = "page", defaultValue = "0") int page,
                                     @RequestParam(name = "count", defaultValue = "1000") int count) {
        return service.getLogs(jobId, page, count);
    }

    @GetMapping("/jobs/{jobId}/audit")
    public List<JobAudit> getJopAudits(@PathVariable String jobId, @RequestParam(name = "page", defaultValue = "0") int page,
                                @RequestParam(name = "count", defaultValue = "1000") int count) {
        return service.getJobAudits(jobId, page, count);
    }

    @GetMapping("/jobs-count")
    public Map<String, Long> getAllJobsCount() {
        return service.getAllJobsCount();
    }

    @GetMapping("/running-jobs-count")
    public long getRunningJobCount() {
        return service.getRunningJobCount();
    }

    @GetMapping("/job-run-per-day")
    public List<JobRunStat> getJobsRanByDay() {
        return service.getJobsRanByDay();
    }

    @GetMapping("/job-failed-per-day")
    public List<JobRunStat> getJobFailedByDay() {
        return service.getJobFailedByDay();
    }

    @GetMapping("/create-bogus-data")
    public List<JobAudit> createBogusAudit() {
        return service.createBogusAudit();
    }
}