import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { JobService } from '../services/job.service';
import { HourlyJob } from '../services/models/hourly-job';
import { jsonValidator } from '../utils/json-validator';

@Component({
  selector: 'app-hourly-jobs',
  templateUrl: './hourly-jobs.component.html',
  styleUrls: ['./hourly-jobs.component.scss']
})
export class HourlyJobsComponent implements OnInit {

  jobParam : string = "";
  jobDataSource : any[] = [];
  displayedColumns: string[] = ['jobName', 'startTime', 'status', 'jobKind', 'operation', 'logs', 'history'];
  jobCreatorForm : FormGroup;
  availableJobs : HourlyJob[] = [];
  constructor(private fb: FormBuilder, private jobService : JobService) { 
    this.jobCreatorForm = this.fb.group({
      name : new FormControl("", [Validators.required]),
      urgent : new FormControl(""),
      jobParams : new FormControl("", [Validators.required, jsonValidator]),
      startTime :  new FormControl("", [Validators.required]),
      jobKind :  new FormControl("", [Validators.required]),
      repeatFrequencyInMinutes : new FormControl("", [Validators.required])
    })
  }

  ngOnInit(): void {
    this.getHourlyJobs();
    
  }

  getHourlyJobs() {
    this.availableJobs = [];
    this.jobService.getHourlyJobs().subscribe(response=>{
      if(response && response.body) {
        response.body.forEach(j=>{
          this.availableJobs.push(j);          
        })
        console.log(this.availableJobs.length);
      }
      
    })
  }

  format(event : any) {
    try {
      let value = event.target.value;
      let obj = JSON.parse(value);
      value = JSON.stringify(obj, null, 8);
      console.log(value);
      event.target.value = value;
    } catch(x){}
    
  }

  delete(jobId : string) {
    this.jobService.deleteHourlyJob(jobId).subscribe(response=> {
      if(response && response.status == 200) {
        this.getHourlyJobs();
      }
    })
  }

  createJob(job : any, form : FormGroup) {
    job.jobParams = JSON.parse(job.jobParams)
    this.jobService.createHourlyJob(job).subscribe(response => {
      if(response && response.body) {        
        this.getHourlyJobs();
      }
    });
  }

  refresh() {
    this.getHourlyJobs();
  }
}
