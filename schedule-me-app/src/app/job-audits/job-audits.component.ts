import { animate, state, style, transition, trigger } from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JobService } from '../services/job.service';
import { JobAudit } from '../services/models/audit';

@Component({
  selector: 'app-job-audits',
  templateUrl: './job-audits.component.html',
  styleUrls: ['./job-audits.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)'))
    ])
  ]
})
export class JobAuditsComponent implements OnInit {

  displayedColumns: string[] = ['startTime', 'endTime', 'status'];
  jobAudits : JobAudit[] = [];
  jobId : string = "";
  name : string = "";
  expandedAudit : JobAudit | null = null;
  displayedColumnsWithExpand = [...this.displayedColumns, 'expand']
  constructor(private jobService : JobService, private route : ActivatedRoute) { 
    this.route.params.subscribe(param=>{
      this.name = param["name"]
      this.jobId = param["jobId"]
    })
  }

  ngOnInit(): void {
    this.getJobAudits(this.jobId)
  }

  getJobAudits(jobId : string) {
    this.jobAudits = [];
    this.jobService.getJobAudit(jobId).subscribe(response=>{      
      if(response && response.body) {
        
        this.jobAudits = response.body;
      }
    })
  }

}
