import { animate, state, style, transition, trigger } from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { JobService } from '../services/job.service';
import { RecordProcessorJob } from '../services/models/record-processor-job';
import { jsonValidator } from '../utils/json-validator';

@Component({
  selector: 'app-record-processor-jobs',
  templateUrl: './record-processor-jobs.component.html',
  styleUrls: ['./record-processor-jobs.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)'))
    ])
  ]
})
export class RecordProcessorJobsComponent implements OnInit {

  jobParam : string = "";
  jobDataSource : any[] = [];
  displayedColumns: string[] = ['name', 'startTime', 'status', 'jobKind','endTime', 'operation'];
  jobCreatorForm : FormGroup;
  availableJobs : RecordProcessorJob[] = [];
  expandedJob : RecordProcessorJob | null = null;
  displayedColumnsWithExpand = [...this.displayedColumns, 'expand']
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
    this.getRecordProcessorJob();
    
  }

  getRecordProcessorJob() {
    this.availableJobs = [];
    this.jobService.getRecordProcessorJobs().subscribe(response=>{
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
    this.jobService.deleteRecordProcessorJob(jobId).subscribe(response=> {
      if(response && response.status == 200) {
        this.getRecordProcessorJob();
      }
    })
  }

  createJob(job : any, form : FormGroup) {
    job.jobParams = JSON.parse(job.jobParams)
    this.jobService.createRecordProcessorJob(job).subscribe(response => {
      if(response && response.body) {        
        this.availableJobs.push(response.body);
      }
    });
  }

  refresh() {
    this.getRecordProcessorJob();
  }

}
