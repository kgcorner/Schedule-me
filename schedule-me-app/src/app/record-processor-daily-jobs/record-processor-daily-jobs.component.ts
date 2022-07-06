import { animate, state, style, transition, trigger } from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ConfirmJobDeleteComponent } from '../confirm-job-delete/confirm-job-delete.component';
import { ConfirmJobRunComponent } from '../confirm-job-run/confirm-job-run.component';
import { JobService } from '../services/job.service';
import { RecordProcessorDailyJob } from '../services/models/record-processor-daily-job';
import { jsonValidator } from '../utils/json-validator';

@Component({
  selector: 'app-record-processor-daily-jobs',
  templateUrl: './record-processor-daily-jobs.component.html',
  styleUrls: ['./record-processor-daily-jobs.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)'))
    ])
  ]
})
export class RecordProcessorDailyJobsComponent implements OnInit {

  jobParam : string = "";
  jobDataSource : any[] = [];
  displayedColumns: string[] = ['name', 'startTime', 'status', 'jobKind','operation', 'log', 'audit', 'refresh', "runNow"];
  jobCreatorForm : FormGroup;
  availableJobs : RecordProcessorDailyJob[] = [];
  expandedJob : RecordProcessorDailyJob | null = null;
  displayedColumnsWithExpand = [...this.displayedColumns, 'expand']
  constructor(private fb: FormBuilder, private jobService : JobService, private snackBar : MatSnackBar, private dialog: MatDialog) { 
    this.jobCreatorForm = this.fb.group({
      name : new FormControl("", [Validators.required]),
      urgent : new FormControl(""),
      jobParams : new FormControl("", [Validators.required, jsonValidator]),
      startTime :  new FormControl("", [Validators.required]),
      jobKind :  new FormControl("", [Validators.required])
    })
  }

  ngOnInit(): void {
    this.getRecordProcessorDailyJob();
    
  }

  getRecordProcessorDailyJob() {
    this.availableJobs = [];
    this.jobService.getRecordProcessorDailyJobs().subscribe(response=>{
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
    let diaRef = this.dialog.open(ConfirmJobDeleteComponent);
    diaRef.afterClosed().subscribe(result=> {
      if(result) {
        this.jobService.deleteRecordProcessorDailyJob(jobId).subscribe(response=> {
          if(response && response.status == 200) {
            this.getRecordProcessorDailyJob();
          }
        })
      }
    });
    
  }

  createJob(job : any, form : FormGroup) {
    job.jobParams = JSON.parse(job.jobParams)
    this.jobService.createRecordProcessorDailyJob(job).subscribe(response => {
      if(response && response.body) {        
        this.getRecordProcessorDailyJob();
        this.snackBar.open("Job created", "close")
      }
    }, e=> {
      this.snackBar.open("Job creation failed", "close")
    });
  }

  refresh() {
    this.getRecordProcessorDailyJob();
  }

  runJob(jobId : string) {
    let diaRef = this.dialog.open(ConfirmJobRunComponent);
    diaRef.afterClosed().subscribe(result=> {
      if(result) {
        this.jobService.runRecordProcessorDailyJob(jobId).subscribe(r=> {
          if(r && r.body) {
            this.getRecordProcessorDailyJob();
            this.snackBar.open("Job started", "close");
          }
        }, e=>{
          this.snackBar.open("Failed to start job", "close");
        })
      }
    })
  }

}
