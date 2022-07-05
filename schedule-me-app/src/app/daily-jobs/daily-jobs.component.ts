import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { JobService } from '../services/job.service';
import { DailyJob } from '../services/models/daily-job';
import { jsonValidator } from '../utils/json-validator';

@Component({
  selector: 'app-daily-jobs',
  templateUrl: './daily-jobs.component.html',
  styleUrls: ['./daily-jobs.component.scss']
})
export class DailyJobsComponent implements OnInit {

  jobParam : string = "";
  jobDataSource : any[] = [];
  displayedColumns: string[] = ['jobName', 'startTime', 'status', 'jobKind', 'operation'];
  jobCreatorForm : FormGroup;
  availableJobs : DailyJob[] = [];
  constructor(private fb: FormBuilder, private jobService : JobService) { 
    this.jobCreatorForm = this.fb.group({
      name : new FormControl("", [Validators.required]),
      urgent : new FormControl(""),
      jobParams : new FormControl("", [Validators.required, jsonValidator]),
      startTime :  new FormControl("", [Validators.required]),
      jobKind :  new FormControl("", [Validators.required])
    })
  }

  ngOnInit(): void {
    this.getDailyJobs();
    
  }

  getDailyJobs() {
    this.availableJobs = [];
    this.jobService.getDailyJobs().subscribe(response=>{
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
    this.jobService.deleteDailyJob(jobId).subscribe(response=> {
      if(response && response.status == 200) {
        this.getDailyJobs();
      }
    })
  }

  createJob(job : any, form : FormGroup) {
    job.jobParams = JSON.parse(job.jobParams)
    this.jobService.createDailyJob(job).subscribe(response => {
      if(response && response.body) {        
        this.getDailyJobs();
      }
    });
  }

  refresh() {
    this.getDailyJobs();
  }
}
