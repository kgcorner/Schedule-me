package com.dealsdelta.scheduleme.services;


import com.dealsdelta.scheduleme.data.dao.*;
import com.dealsdelta.scheduleme.data.models.*;
import com.dealsdelta.scheduleme.dtos.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 24/06/22
 */

@Service
public class TaskService {

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
    private LogService logService;

    @Autowired
    private RunningJobDao runningJobDao;


    public List<IJob> getJobs(String status) {
        return null;
    }

    public IJob createJob(IJob job) {
        return null;
    }

    public IJob getJob(String jobId) {
        return null;
    }

    public RecordProcessorHourlyJob createRecordProcessingHourlyJob(RecordProcessorHourlyJob job) {
        RecordProcessorHourlyJobModel model = new RecordProcessorHourlyJobModel();
        BeanUtils.copyProperties(job, model);
        return recordProcessorHourlyJobDao.create(model);
    }

    public RecordProcessorDailyJob createRecordProcessingHourlyJob(RecordProcessorDailyJob job) {
        RecordProcessorDailyJobModel model = new RecordProcessorDailyJobModel();
        BeanUtils.copyProperties(job, model);
        return recordProcessorDailyJobDao.create(model);
    }

    public RecordProcessorMonthlyJob createRecordProcessingHourlyJob(RecordProcessorMonthlyJob job) {
        RecordProcessorMonthlyJobModel model = new RecordProcessorMonthlyJobModel();
        BeanUtils.copyProperties(job, model);
        return recordProcessorMonthlyJobDao.create(model);
    }

    public DailyJob createDailyJob(DailyJob job) {
        DailyJobModel model = new DailyJobModel();
        BeanUtils.copyProperties(job, model);
        return dailyJobDao.create(model);
    }

    public MonthlyJob createMonthlyJob(MonthlyJob job) {
        MonthlyJobModel model = new MonthlyJobModel();
        BeanUtils.copyProperties(job, model);
        return monthlyJobDao.create(model);
    }

    public HourlyJob createHourlyJob(HourlyJob job) {
        HourlyJobModel model = new HourlyJobModel();
        BeanUtils.copyProperties(job, model);
        return hourlyJobDao.create(model);
    }

    public RecordProcessorJob createRecordProcessorJob(RecordProcessorJob job) {
        RecordProcessorJobModel model = new RecordProcessorJobModel();
        BeanUtils.copyProperties(job, model);
        return recordProcessorJobDao.create(model);
    }

    public GenericJob createGenericJob(GenericJob job) {
        GenericJobModel model = new GenericJobModel();
        BeanUtils.copyProperties(job, model);
        return genericJobDao.create(model);
    }













    public DailyJobDao getDailyJobDao() {
        return dailyJobDao;
    }

    public MonthlyJobDao getMonthlyJobDao() {
        return monthlyJobDao;
    }

    public HourlyJobDao getHourlyJobDao() {
        return hourlyJobDao;
    }

    public LogDao getLogDao() {
        return logDao;
    }

    public GenericJobDao getGenericJobDao() {
        return genericJobDao;
    }

    public RecordProcessorDailyJobDao getRecordProcessorDailyJobDao() {
        return recordProcessorDailyJobDao;
    }

    public RecordProcessorMonthlyJobDao getRecordProcessorMonthlyJobDao() {
        return recordProcessorMonthlyJobDao;
    }

    public RecordProcessorHourlyJobDao getRecordProcessorHourlyJobDao() {
        return recordProcessorHourlyJobDao;
    }

    public RecordProcessorJobDao getRecordProcessorJobDao() {
        return recordProcessorJobDao;
    }

    public LogService getLogService() {
        return logService;
    }

    public RunningJobDao getRunningJobDao() {
        return runningJobDao;
    }


    public List<DailyJob> getDailyJobs() {
        List<DailyJobModel> models = dailyJobDao.getDailyJobs();
        List<DailyJob> jobs = new ArrayList<>();
        for(DailyJobModel model : models) {
            jobs.add(model);
        }
        return jobs;
    }

    public void deleteDailyJob(String jobId) {
        dailyJobDao.delete((DailyJobModel) dailyJobDao.getDailyJob(jobId));
    }

    public List<HourlyJob> getHourlyJobs() {
        List<HourlyJobModel> hourlyJobs = hourlyJobDao.getHourlyJobs();
        List<HourlyJob> jobs = new ArrayList<>();
        for(HourlyJobModel model : hourlyJobs ) {
            jobs.add(model);
        }
        return jobs;
    }

    public void deleteHourlyJob(String jobId) {
        hourlyJobDao.delete((HourlyJobModel) hourlyJobDao.getHourlyJob(jobId));
    }

    public List<MonthlyJob> getMonthlyJobs() {
        List<MonthlyJobModel> hourlyJobs = monthlyJobDao.getMonthlyJobs();
        List<MonthlyJob> jobs = new ArrayList<>();
        for(MonthlyJobModel model : hourlyJobs ) {
            jobs.add(model);
        }
        return jobs;
    }

    public void deleteMonthlyJob(String jobId) {
        monthlyJobDao.delete((MonthlyJobModel) monthlyJobDao.getMonthlyJob(jobId));
    }

    public List<GenericJob> getGenericJobs() {
        List<GenericJobModel> genericJobs = genericJobDao.getGenericJobs();
        List<GenericJob> jobs = new ArrayList<>();
        for(GenericJobModel model : genericJobs ) {
            jobs.add(model);
        }
        return jobs;
    }

    public void deleteGenericJob(String jobId) {
        genericJobDao.delete((GenericJobModel) genericJobDao.getGenericJob(jobId));
    }

    public List<RecordProcessorJob> getRecordProcessorJobs() {
        List<RecordProcessorJobModel> recordProcessorJobs = recordProcessorJobDao.getRecordProcessorJobs();
        List<RecordProcessorJob> jobs = new ArrayList<>();
        for(RecordProcessorJobModel model : recordProcessorJobs ) {
            jobs.add(model);
        }
        return jobs;
    }

    public void deleteRecordProcessorJob(String jobId) {
        recordProcessorJobDao.delete((RecordProcessorJobModel) recordProcessorJobDao.getRecordProcessorJob(jobId));
    }

    public List<RecordProcessorDailyJob> getRecordProcessorDailyJobs() {
        List<RecordProcessorDailyJobModel> recordProcessorDailyJobs = recordProcessorDailyJobDao.getRecordProcessorDailyJobs();
        List<RecordProcessorDailyJob> jobs = new ArrayList<>();
        for(RecordProcessorDailyJobModel model : recordProcessorDailyJobs ) {
            jobs.add(model);
        }
        return jobs;
    }

    public void deleteRecordProcessorDailyJob(String jobId) {
        recordProcessorDailyJobDao.delete((RecordProcessorDailyJobModel) recordProcessorDailyJobDao.getRecordProcessorDailyJob(jobId));
    }

    public List<RecordProcessorMonthlyJob> getRecordProcessorMonthlyJobs() {
        List<RecordProcessorMonthlyJobModel> recordProcessorMonthlyJobs = recordProcessorMonthlyJobDao.getRecordProcessorMonthlyJobs();
        List<RecordProcessorMonthlyJob> jobs = new ArrayList<>();
        for(RecordProcessorMonthlyJobModel model : recordProcessorMonthlyJobs ) {
            jobs.add(model);
        }
        return jobs;
    }

    public void deleteRecordProcessorMonthlyJob(String jobId) {
        recordProcessorMonthlyJobDao.delete((RecordProcessorMonthlyJobModel) recordProcessorMonthlyJobDao.getRecordProcessorMonthlyJob(jobId));
    }

    public List<RecordProcessorHourlyJob> getRecordProcessorHourlyJobs() {
        List<RecordProcessorHourlyJobModel> recordProcessorHourlyJobs = recordProcessorHourlyJobDao.getRecordProcessorHourlyJobs();
        List<RecordProcessorHourlyJob> jobs = new ArrayList<>();
        for(RecordProcessorHourlyJobModel model : recordProcessorHourlyJobs ) {
            jobs.add(model);
        }
        return jobs;
    }

    public void deleteRecordProcessorHourlyJob(String jobId) {
        recordProcessorHourlyJobDao.delete((RecordProcessorHourlyJobModel) recordProcessorHourlyJobDao.getRecordProcessorHourlyJob(jobId));
    }
}