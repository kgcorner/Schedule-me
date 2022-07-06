import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ConfirmJobDeleteComponent } from '../confirm-job-delete/confirm-job-delete.component';
import { ConfirmJobRunComponent } from '../confirm-job-run/confirm-job-run.component';
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
  displayedColumns: string[] = ['jobName', 'startTime', 'status', 'jobKind', 'operation', 'logs', 'history', 'runNow'];
  jobCreatorForm : FormGroup;
  availableJobs : DailyJob[] = [];
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
    let diaRef = this.dialog.open(ConfirmJobDeleteComponent);
    diaRef.afterClosed().subscribe(result=> {
      if(result) {
        this.jobService.deleteDailyJob(jobId).subscribe(response=> {
          if(response && response.status == 200) {
            this.getDailyJobs();
          }
        })
      }
    })
    
  }

  createJob(job : any, form : FormGroup) {
    job.jobParams = JSON.parse(job.jobParams)
    this.jobService.createDailyJob(job).subscribe(response => {
      if(response && response.body) {        
        this.getDailyJobs();
        this.snackBar.open("Job Created", "close");
      }
    }, e=> {
      this.snackBar.open("Failed to create job", "close");
    });
  }

  refresh() {
    this.getDailyJobs();
  }

  runJob(jobId : string) {
    let diaRef = this.dialog.open(ConfirmJobRunComponent);
    diaRef.afterClosed().subscribe(result=> {
      if(result) {
        this.jobService.runDailyJob(jobId).subscribe(r=> {
          if(r && r.body) {
            this.getDailyJobs();
            this.snackBar.open("Job started", "close");
          }
        }, e=>{
          this.snackBar.open("Failed to start job", "close");
        })
      }
    })
  }
}
