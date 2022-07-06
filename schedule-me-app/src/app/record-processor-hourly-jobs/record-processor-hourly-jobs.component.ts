import { animate, state, style, transition, trigger } from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { JobService } from '../services/job.service';
import { RecordProcessorHourlyJob } from '../services/models/record-processor-hourly-job';
import { jsonValidator } from '../utils/json-validator';

@Component({
  selector: 'app-record-processor-hourly-jobs',
  templateUrl: './record-processor-hourly-jobs.component.html',
  styleUrls: ['./record-processor-hourly-jobs.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)'))
    ])
  ]
})
export class RecordProcessorHourlyJobsComponent implements OnInit {

  jobParam : string = "";
  jobDataSource : any[] = [];
  displayedColumns: string[] = ['name', 'startTime', 'status', 'jobKind','operation', 'refresh'];
  jobCreatorForm : FormGroup;
  availableJobs : RecordProcessorHourlyJob[] = [];
  expandedJob : RecordProcessorHourlyJob | null = null;
  displayedColumnsWithExpand = [...this.displayedColumns, 'expand']
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
    this.getRecordProcessorHourlyJob();
    
  }

  getRecordProcessorHourlyJob() {
    this.availableJobs = [];
    this.jobService.getRecordProcessorHourlyJobs().subscribe(response=>{
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
    this.jobService.deleteRecordProcessorHourlyJob(jobId).subscribe(response=> {
      if(response && response.status == 200) {
        this.getRecordProcessorHourlyJob();
      }
    })
  }

  createJob(job : any, form : FormGroup) {
    job.jobParams = JSON.parse(job.jobParams)
    this.jobService.createRecordProcessorHourlyJob(job).subscribe(response => {
      if(response && response.body) {        
        this.getRecordProcessorHourlyJob();
      }
    });
  }

  refresh() {
    this.getRecordProcessorHourlyJob();
  }

}
