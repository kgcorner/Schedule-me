import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { JobService } from '../services/job.service';
import { GenericJob } from '../services/models/generic-job';
import { jsonValidator } from '../utils/json-validator';

@Component({
  selector: 'app-generic-jobs',
  templateUrl: './generic-jobs.component.html',
  styleUrls: ['./generic-jobs.component.scss']
})
export class GenericJobsComponent implements OnInit {

  jobParam : string = "";
  jobDataSource : any[] = [];
  displayedColumns: string[] = ['jobName', 'startTime', 'status', 'jobKind','endTime', 'operation', 'logs', 'history'];
  jobCreatorForm : FormGroup;
  availableJobs : GenericJob[] = [];
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
    this.getGenericJob();
    
  }

  getGenericJob() {
    this.availableJobs = [];
    this.jobService.getGenericJobs().subscribe(response=>{
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
    this.jobService.deleteGenericJob(jobId).subscribe(response=> {
      if(response && response.status == 200) {
        this.getGenericJob();
      }
    })
  }

  createJob(job : any, form : FormGroup) {
    job.jobParams = JSON.parse(job.jobParams)
    this.jobService.createGenericJob(job).subscribe(response => {
      if(response && response.body) {        
        this.availableJobs.push(response.body);
      }
    });
  }

  refresh() {
    this.getGenericJob();
  }

}
