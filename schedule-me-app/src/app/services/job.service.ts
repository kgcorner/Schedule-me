import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpService } from './http.service';
import { JobAudit } from './models/audit';
import { DailyJob } from './models/daily-job';
import { GenericJob } from './models/generic-job';
import { HourlyJob } from './models/hourly-job';
import { Log } from './models/log';
import { MonthlyJob } from './models/monthly-job';
import { RecordProcessorDailyJob } from './models/record-processor-daily-job';
import { RecordProcessorHourlyJob } from './models/record-processor-hourly-job';
import { RecordProcessorJob } from './models/record-processor-job';
import { RecordProcessorMonthlyJob } from './models/record-processor-monthly-job';

@Injectable({
  providedIn: 'root'
})
export class JobService {
  

  private DAILY_JOBS =  "/daily-jobs";
  private MONTHLY_JOBS =  "/monthly-jobs";
  private HOURLY_JOBS =  "/hourly-jobs";
  private RECORD_PROCESSOR_DAILY_JOBS =  "/record-processor-daily-jobs";
  private RECORD_PROCESSOR_MONTHLY_JOBS =  "/record-processor-monthly-jobs";
  private RECORD_PROCESSOR_HOURLY_JOBS =  "/record-processor-hourly-jobs";
  private RECORD_PROCESSOR_JOBS =  "/record-processor-jobs";
  private GENERIC_JOBS =  "/generic-jobs";
  private JOB_LOGS =  "/jobs/{1}/logs";
  private JOB_AUDIT =  "/jobs/{1}/audit";
  private ALL_JOB_COUNT =  "/jobs-count";
  private RUNNING_JOB_COUNT =  "/running-jobs-count";
  private JOB_RUN_PER_DAY = "/job-run-per-day"
  private JOB_FAILED_PER_DAY = "/job-failed-per-day"

  constructor(private http : HttpService) { }

  
  createDailyJob(job : DailyJob) {
    job.status = "DUE";
    return this.http.doPost<DailyJob>(environment.host + this.DAILY_JOBS, null, job);
  }

  getDailyJobs() {
    return this.http.doGet<DailyJob[]>(environment.host + this.DAILY_JOBS, null);
  }

  deleteDailyJob(jobId : string) {
    return this.http.doDelete(environment.host + this.DAILY_JOBS+"/"+jobId, null);
  }

  createHourlyJob(job : HourlyJob) {
    job.status = "DUE";
    return this.http.doPost<HourlyJob>(environment.host + this.HOURLY_JOBS, null, job);
  }

  getHourlyJobs() {
    return this.http.doGet<HourlyJob[]>(environment.host + this.HOURLY_JOBS, null);
  }

  deleteHourlyJob(jobId : string) {
    return this.http.doDelete(environment.host + this.HOURLY_JOBS+"/"+jobId, null);
  }

  createMonthlyJob(job : MonthlyJob) {
    job.status = "DUE";
    return this.http.doPost<MonthlyJob>(environment.host + this.MONTHLY_JOBS, null, job);
  }

  getMonthlyJobs() {
    return this.http.doGet<MonthlyJob[]>(environment.host + this.MONTHLY_JOBS, null);
  }

  deleteMonthlyJob(jobId : string) {
    return this.http.doDelete(environment.host + this.MONTHLY_JOBS+"/"+jobId, null);
  }

  createRecordProcessorDailyJob(job : RecordProcessorDailyJob) {
    job.status = "DUE";
    return this.http.doPost<RecordProcessorDailyJob>(environment.host + this.RECORD_PROCESSOR_DAILY_JOBS, null, job);
  }

  getRecordProcessorDailyJobs() {
    return this.http.doGet<RecordProcessorDailyJob[]>(environment.host + this.RECORD_PROCESSOR_DAILY_JOBS, null);
  }

  deleteRecordProcessorDailyJob(jobId : string) {
    return this.http.doDelete(environment.host + this.RECORD_PROCESSOR_DAILY_JOBS+"/"+jobId, null);
  }

  createRecordProcessorHourlyJob(job : RecordProcessorHourlyJob) {
    job.status = "DUE";
    return this.http.doPost<RecordProcessorHourlyJob>(environment.host + this.RECORD_PROCESSOR_HOURLY_JOBS, null, job);
  }

  getRecordProcessorHourlyJobs() {
    return this.http.doGet<RecordProcessorHourlyJob[]>(environment.host + this.RECORD_PROCESSOR_HOURLY_JOBS, null);
  }

  deleteRecordProcessorHourlyJob(jobId : string) {
    return this.http.doDelete(environment.host + this.RECORD_PROCESSOR_HOURLY_JOBS+"/"+jobId, null);
  }

  createRecordProcessorMonthlyJob(job : RecordProcessorMonthlyJob) {
    job.status = "DUE";
    return this.http.doPost<RecordProcessorMonthlyJob>(environment.host + this.RECORD_PROCESSOR_MONTHLY_JOBS, null, job);
  }

  getRecordProcessorMonthlyJobs() {
    return this.http.doGet<RecordProcessorMonthlyJob[]>(environment.host + this.RECORD_PROCESSOR_MONTHLY_JOBS, null);
  }

  deleteRecordProcessorMonthlyJob(jobId : string) {
    return this.http.doDelete(environment.host + this.RECORD_PROCESSOR_MONTHLY_JOBS+"/"+jobId, null);
  }


  createRecordProcessorJob(job : RecordProcessorJob) {
    job.status = "DUE";
    return this.http.doPost<RecordProcessorJob>(environment.host + this.RECORD_PROCESSOR_JOBS, null, job);
  }

  getRecordProcessorJobs() {
    return this.http.doGet<RecordProcessorJob[]>(environment.host + this.RECORD_PROCESSOR_JOBS, null);
  }

  deleteRecordProcessorJob(jobId : string) {
    return this.http.doDelete(environment.host + this.RECORD_PROCESSOR_JOBS+"/"+jobId, null);
  }

  createGenericJob(job : GenericJob) {
    job.status = "DUE";
    return this.http.doPost<GenericJob>(environment.host + this.GENERIC_JOBS, null, job);
  }

  getGenericJobs() {
    return this.http.doGet<GenericJob[]>(environment.host + this.GENERIC_JOBS, null);
  }

  deleteGenericJob(jobId : string) {
    return this.http.doDelete(environment.host + this.GENERIC_JOBS+"/"+jobId, null);
  }

  getJobLogs(jobId: string) {
    return this.http.doGet<Log[]>(environment.host + this.JOB_LOGS.replace("{1}", jobId), null);
  }

  getJobAudit(jobId: string) {
    return this.http.doGet<JobAudit[]>(environment.host + this.JOB_AUDIT.replace("{1}", jobId), null);
  }

  getAllJobsCount() {
    return this.http.doGet<any>(environment.host + this.ALL_JOB_COUNT, null);
  }

  getRunningJobsCount() {
    return this.http.doGet<number>(environment.host + this.RUNNING_JOB_COUNT, null);
  }

  getJobRunPerDay() {
    return this.http.doGet<any[]>(environment.host + this.JOB_RUN_PER_DAY, null);
  }

  getJobFailedPerDay() {
    return this.http.doGet<any[]>(environment.host + this.JOB_FAILED_PER_DAY, null);
  }

  runDailyJob(jobId : string) {
    return this.http.doPut<DailyJob>(environment.host + this.DAILY_JOBS+"/"+jobId, null, null);
  }

  runMonthlyJob(jobId : string) {
    return this.http.doPut<MonthlyJob>(environment.host + this.MONTHLY_JOBS+"/"+jobId, null, null);
  }

  runHourlyJob(jobId : string) {
    return this.http.doPut<HourlyJob>(environment.host + this.HOURLY_JOBS+"/"+jobId, null, null);
  }


  runGenericJob(jobId : string) {
    return this.http.doPut<GenericJob>(environment.host + this.GENERIC_JOBS+"/"+jobId, null, null);
  }

  runRecordProcessorJob(jobId : string) {
    return this.http.doPut<RecordProcessorJob>(environment.host + this.RECORD_PROCESSOR_JOBS+"/"+jobId, null, null);
  }

  runRecordProcessorDailyJob(jobId : string) {
    return this.http.doPut<RecordProcessorDailyJob>(environment.host + this.RECORD_PROCESSOR_DAILY_JOBS+"/"+jobId, null, null);
  }

  runRecordProcessorHourlyJob(jobId : string) {
    return this.http.doPut<RecordProcessorHourlyJob>(environment.host + this.RECORD_PROCESSOR_HOURLY_JOBS+"/"+jobId, null, null);
  }

  runRecordProcessorMonthlyJob(jobId : string) {
    return this.http.doPut<RecordProcessorMonthlyJob>(environment.host + this.RECORD_PROCESSOR_MONTHLY_JOBS+"/"+jobId, null, null);
  }
}
