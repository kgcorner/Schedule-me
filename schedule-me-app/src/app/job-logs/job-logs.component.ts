import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JobService } from '../services/job.service';
import { Log } from '../services/models/log';

@Component({
  selector: 'app-job-logs',
  templateUrl: './job-logs.component.html',
  styleUrls: ['./job-logs.component.scss']
})
export class JobLogsComponent implements OnInit {

  displayedColumns: string[] = ['date', 'module', 'level','message', 'filter'];
  logs : Log[] = [];
  filteredLogs : Log[] = [];
  jobId : string = "";
  name : string = "";
  constructor(private jobService : JobService, private route : ActivatedRoute) { 
    this.route.params.subscribe(param=>{
      this.name = param["name"]
      this.jobId = param["jobId"]
    })
  }

  ngOnInit(): void {
    this.getLogs(this.jobId)
  }

  getLogs(jobId : string) {
    this.logs = [];
    this.jobService.getJobLogs(jobId).subscribe(response=>{      
      if(response && response.body) {
        
        this.logs = response.body;
        this.filteredLogs = response.body;
      }
    })
  }

  filter(level : string) {
    if(level != 'ALL')  {
      this.filteredLogs = this.logs.filter(l=> l.level == level);
    } else {
      this.filteredLogs = this.logs;
    }
      
  }

}
