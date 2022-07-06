package com.dealsdelta.scheduleme.services;


import com.dealsdelta.scheduleme.data.dao.*;
import com.dealsdelta.scheduleme.data.models.*;
import com.dealsdelta.scheduleme.data.repo.Operation;
import com.dealsdelta.scheduleme.dtos.*;
import com.dealsdelta.scheduleme.executors.JobProcessorExecutorService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Autowired
    private JobAuditDao jobAuditDao;



    public List<IJob> getJobs(String status) {
        return null;
    }

    public IJob createJob(IJob job) {
        return null;
    }

    public IJob getJob(String jobId) {
        return null;
    }

    public RecordProcessorHourlyJob createRecordProcessorHourlyJob(RecordProcessorHourlyJob job) {
        RecordProcessorHourlyJobModel model = new RecordProcessorHourlyJobModel();
        BeanUtils.copyProperties(job, model);
        return recordProcessorHourlyJobDao.create(model);
    }

    public RecordProcessorDailyJob createRecordProcessorHourlyJob(RecordProcessorDailyJob job) {
        RecordProcessorDailyJobModel model = new RecordProcessorDailyJobModel();
        BeanUtils.copyProperties(job, model);
        return recordProcessorDailyJobDao.create(model);
    }

    public RecordProcessorMonthlyJob createRecordProcessorHourlyJob(RecordProcessorMonthlyJob job) {
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

    public JobAuditDao getJobAuditDao() {
        return jobAuditDao;
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

    public List<Log> getLogs(String jobId, int page, int count) {
        List<Operation> operations = new ArrayList<>();
        Operation operation = new Operation(jobId, Operation.TYPES.STRING, "jobId", Operation.OPERATORS.EQ);
        operations.add(operation);
        List<Log> logs = new ArrayList<>();
        List<LogModel> models = logDao.getAllBy(operations, page, count);
        for(LogModel model : models) {
            logs.add(model);
        }
        return logs;
    }


    public List<JobAudit> getJobAudits(String jobId, int page, int count) {
        List<Operation> operations = new ArrayList<>();
        Operation operation = new Operation(jobId, Operation.TYPES.STRING, "jobId", Operation.OPERATORS.EQ);
        operations.add(operation);
        List<JobAudit> audits = new ArrayList<>();
        List<JobAuditModel> models = jobAuditDao.getAllBy(operations, page, count);
        for(JobAuditModel model : models) {
            audits.add(model);
        }
        return audits;

    }

    public Map<String, Long> getAllJobsCount() {
        Map<String, Long> jobsCount = new HashMap<>();
        jobsCount.put("Generic Jobs", genericJobDao.getJobCount());
        jobsCount.put("Daily Jobs", dailyJobDao.getJobCount());
        jobsCount.put("Hourly Jobs", hourlyJobDao.getJobCount());
        jobsCount.put("Monthly Jobs", monthlyJobDao.getJobCount());
        jobsCount.put("Record Processor Daily Jobs", recordProcessorDailyJobDao.getJobCount());
        jobsCount.put("Record Processor Hourly Jobs", recordProcessorHourlyJobDao.getJobCount());
        jobsCount.put("Record Processor Monthly Jobs", recordProcessorMonthlyJobDao.getJobCount());
        jobsCount.put("Record Processor Jobs", recordProcessorJobDao.getJobCount());
        return jobsCount;
    }

    public long getRunningJobCount() {
        return runningJobDao.getJobCount();
    }

    public List<JobRunStat> getJobsRanByDay() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -30);

        Calendar c1 = Calendar.getInstance();
        c1.add(Calendar.DATE, 1);
        return jobAuditDao.getJobRunByDay(c.getTime(), c1.getTime());
    }

    public List<JobAudit> createBogusAudit() {
        List<DailyJobModel> dailyJobs = dailyJobDao.getDailyJobs();
        List<HourlyJobModel> hourlyJobs = hourlyJobDao.getHourlyJobs();
        List<MonthlyJobModel> monthlyJobs = monthlyJobDao.getMonthlyJobs();
        List<GenericJobModel> genericJobs = genericJobDao.getGenericJobs();
        List<RecordProcessorDailyJobModel> recordProcessorDailyJobs = recordProcessorDailyJobDao.getRecordProcessorDailyJobs();
        List<RecordProcessorHourlyJobModel> recordProcessorHourlyJobs = recordProcessorHourlyJobDao.getRecordProcessorHourlyJobs();
        List<RecordProcessorMonthlyJobModel> recordProcessorMonthlyJobs = recordProcessorMonthlyJobDao.getRecordProcessorMonthlyJobs();
        List<IJob> jobs = new ArrayList<>();
        for(IJob job : dailyJobs) {
            jobs.add(job);
        }

        for(IJob job : hourlyJobs) {
            jobs.add(job);
        }

        for(IJob job : monthlyJobs) {
            jobs.add(job);
        }

        for(IJob job : recordProcessorDailyJobs) {
            jobs.add(job);
        }

        for(IJob job : recordProcessorHourlyJobs) {
            jobs.add(job);
        }

        for(IJob job : recordProcessorMonthlyJobs) {
            jobs.add(job);
        }
        List<JobAudit> audits = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            for(IJob job : jobs) {
                JobAuditModel model = new JobAuditModel();
                int randomDate = new Random().nextInt(25);
                Calendar c = Calendar.getInstance();
                c.add(Calendar.DATE, -(randomDate));
                model.setStartTime(c.getTime());
                c.add(Calendar.MINUTE, 1);
                model.setEndTime(c.getTime());
                model.setJobId(job.getJobId());
                model.setJob(job);
                model.setLogs(Collections.emptyList());
                if(randomDate %3 == 0)
                    model.setStatus("FAILED");
                else
                    model.setStatus("COMPLETE");
                jobAuditDao.create(model);
                audits.add(model);
            }
        }

        return audits;
    }

    public List<JobRunStat> getJobFailedByDay() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -30);
        Calendar c1 = Calendar.getInstance();
        c1.add(Calendar.DATE, 1);
        return jobAuditDao.getJobFailedByDay(c.getTime(), c1.getTime());
    }

    public DailyJob runDailyJob(String jobId) {
        DailyJob model = dailyJobDao.getDailyJob(jobId);
        model.setStatus(IJob.JOB_STATUS.DUE.toString());
        RunningJobModel runningJobModel = new RunningJobModel();
        runningJobModel.setJob(model);
        runningJobModel.setJobId(jobId);
        runningJobDao.create(runningJobModel);
        model = dailyJobDao.update((DailyJobModel) model);
        runExecutor();
        return model;
    }

    public MonthlyJob runMonthlyJob(String jobId) {
        MonthlyJob model = monthlyJobDao.getMonthlyJob(jobId);
        model.setStatus(IJob.JOB_STATUS.DUE.toString());
        RunningJobModel runningJobModel = new RunningJobModel();
        runningJobModel.setJob(model);
        runningJobModel.setJobId(jobId);
        runningJobDao.create(runningJobModel);
        model = monthlyJobDao.update((MonthlyJobModel) model);
        runExecutor();
        return model;
    }

    public HourlyJob runHourlyJob(String jobId) {
        HourlyJob model = hourlyJobDao.getHourlyJob(jobId);
        model.setStatus(IJob.JOB_STATUS.DUE.toString());
        RunningJobModel runningJobModel = new RunningJobModel();
        runningJobModel.setJob(model);
        runningJobModel.setJobId(jobId);
        runningJobDao.create(runningJobModel);
        model = hourlyJobDao.update((HourlyJobModel) model);
        runExecutor();
        return model;
    }

    public GenericJob runGenericJob(String jobId) {
        GenericJob model = genericJobDao.getGenericJob(jobId);
        model.setStatus(IJob.JOB_STATUS.DUE.toString());
        RunningJobModel runningJobModel = new RunningJobModel();
        runningJobModel.setJob(model);
        runningJobModel.setJobId(jobId);
        runningJobDao.create(runningJobModel);
        model = genericJobDao.update((GenericJobModel) model);
        runExecutor();
        return model;
    }

    public RecordProcessorJob runRecordProcessorJob(String jobId) {
        RecordProcessorJob model = recordProcessorJobDao.getRecordProcessorJob(jobId);
        model.setStatus(IJob.JOB_STATUS.DUE.toString());
        RunningJobModel runningJobModel = new RunningJobModel();
        runningJobModel.setJob(model);
        runningJobModel.setJobId(jobId);
        runningJobDao.create(runningJobModel);
        model = recordProcessorJobDao.update((RecordProcessorJobModel) model);
        runExecutor();
        return model;
    }

    public RecordProcessorDailyJob runRecordProcessorDailyJob(String jobId) {
        RecordProcessorDailyJob model = recordProcessorDailyJobDao.getRecordProcessorDailyJob(jobId);
        model.setStatus(IJob.JOB_STATUS.DUE.toString());
        RunningJobModel runningJobModel = new RunningJobModel();
        runningJobModel.setJob(model);
        runningJobModel.setJobId(jobId);
        runningJobDao.create(runningJobModel);
        model = recordProcessorDailyJobDao.update((RecordProcessorDailyJobModel) model);
        runExecutor();
        return model;
    }

    public RecordProcessorHourlyJob runRecordProcessorHourlyJob(String jobId) {
        RecordProcessorHourlyJob model = recordProcessorHourlyJobDao.getRecordProcessorHourlyJob(jobId);
        model.setStatus(IJob.JOB_STATUS.DUE.toString());
        RunningJobModel runningJobModel = new RunningJobModel();
        runningJobModel.setJob(model);
        runningJobModel.setJobId(jobId);
        runningJobDao.create(runningJobModel);
        model = recordProcessorHourlyJobDao.update((RecordProcessorHourlyJobModel) model);
        runExecutor();
        return model;
    }

    public RecordProcessorMonthlyJob runRecordProcessorMonthlyJob(String jobId) {
        RecordProcessorMonthlyJob model = recordProcessorMonthlyJobDao.getRecordProcessorMonthlyJob(jobId);
        model.setStatus(IJob.JOB_STATUS.DUE.toString());
        RunningJobModel runningJobModel = new RunningJobModel();
        runningJobModel.setJob(model);
        runningJobModel.setJobId(jobId);
        runningJobDao.create(runningJobModel);
        model = recordProcessorMonthlyJobDao.update((RecordProcessorMonthlyJobModel) model);
        runExecutor();
        return model;
    }

    @Autowired
    ConfigurableApplicationContext configurableApplicationContext;
    private void runExecutor() {
        JobProcessorExecutorService jobProcessorExecutorService = configurableApplicationContext.getBean(JobProcessorExecutorService.class);
        new Thread(() -> jobProcessorExecutorService.start()).start();
    }
}