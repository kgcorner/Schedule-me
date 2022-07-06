import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { JobService } from '../services/job.service';
import { MonthlyJob } from '../services/models/monthly-job';
import { jsonValidator } from '../utils/json-validator';

@Component({
  selector: 'app-monthly-jobs',
  templateUrl: './monthly-jobs.component.html',
  styleUrls: ['./monthly-jobs.component.scss']
})
export class MonthlyJobsComponent implements OnInit {

  jobParam : string = "";
  jobDataSource : any[] = [];
  displayedColumns: string[] = ['jobName', 'startTime', 'status', 'jobKind','dayOfMonth', 'operation', 'logs', 'history'];
  jobCreatorForm : FormGroup;
  availableJobs : MonthlyJob[] = [];
  constructor(private fb: FormBuilder, private jobService : JobService) { 
    this.jobCreatorForm = this.fb.group({
      name : new FormControl("", [Validators.required]),
      urgent : new FormControl(""),
      jobParams : new FormControl("", [Validators.required, jsonValidator]),
      startTime :  new FormControl("", [Validators.required]),
      jobKind :  new FormControl("", [Validators.required]),
      dayOfMonth : new FormControl("", [Validators.required, Validators.max(31)])
    })
  }

  ngOnInit(): void {
    this.getMonthlyJob();
    
  }

  getMonthlyJob() {
    this.availableJobs = [];
    this.jobService.getMonthlyJobs().subscribe(response=>{
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
    this.jobService.deleteMonthlyJob(jobId).subscribe(response=> {
      if(response && response.status == 200) {
        this.getMonthlyJob();
      }
    })
  }

  createJob(job : any, form : FormGroup) {
    job.jobParams = JSON.parse(job.jobParams)
    this.jobService.createMonthlyJob(job).subscribe(response => {
      if(response && response.body) {        
        this.getMonthlyJob();
      }
    });
  }

  refresh() {
    this.getMonthlyJob();
  }

}
