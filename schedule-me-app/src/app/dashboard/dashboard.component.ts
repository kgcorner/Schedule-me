import { Component, OnInit } from '@angular/core';
import { JobService } from '../services/job.service';
import { ChartType } from 'angular-google-charts';
@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent  implements OnInit {
  jobRanConfig : any = {
    title : 'Number of Jobs ran in last 30 days',
    type : ChartType.LineChart,
    data : [],
    columnNames : ["Date", "No. of jobs Ran"],
    options : {   
      hAxis: {
        title: 'Date'
      },
      vAxis:{
        title: 'No. of jobs Ran'
      },
      pointSize:5,
      responsive: true
    }
  }

  jobFailedConfig : any = {
    title : 'Number of Jobs failed in last 30 days',
    type : ChartType.LineChart,
    data : [],
    columnNames : ["Date", "No. of jobs failed"],
    options : {   
      hAxis: {
        title: 'Date'
      },
      vAxis:{
        title: 'No. of jobs failed'
      },
      pointSize:5,
      responsive: true,
      colors: ["red"]
    }
  }

  allJobCountConfig : any = {
    options : {
      pieHole:0.4,
      is3D : true
    },
    title : 'Available jobs type wise',
   type : ChartType.PieChart,
   data : [
   ],
   columnNames : ['Job Type', 'count']
  }
  totalRunningJobs : number = 0;
  constructor(private jobService : JobService) {}

  ngOnInit(): void {
   this.populateCharts()
  }

  ngOnChaneg() {
    this.populateCharts();
  }

  populateCharts() {
    this.jobService.getJobRunPerDay().subscribe(response=>{
      if(response && response.body) {
        let jobRunData : any[] = response.body;
        jobRunData.forEach(d=> {
          let dayData :any[] = []
          let date : Date = new Date( d["date"]);
 
          dayData.push(date.getDate()+"/" + date.getMonth())
          dayData.push(d["count"])
          this.jobRanConfig.data.push(dayData);
        })
      }
    })
 
    this.jobService.getJobFailedPerDay().subscribe(response=>{
       if(response && response.body) {
         let jobRunData : any[] = response.body;
         jobRunData.forEach(d=> {
           let dayData :any[] = []
           let date : Date = new Date( d["date"]);
 
           dayData.push(date.getDate()+"/" + date.getMonth())
           dayData.push(d["count"])
           this.jobFailedConfig.data.push(dayData);
         })
       }
     })
 
     this.jobService.getAllJobsCount().subscribe(response=>{
       if(response && response.body) {
         Object.keys(response.body).forEach(key=> {
           let data :any[] = [];
           data.push(key)
           data.push(response.body[key])
           this.allJobCountConfig.data.push(data);
         })
       }
     })   
     
     this.jobService.getRunningJobsCount().subscribe(response=>{
       if(response && response.body) {        
         this.totalRunningJobs = response.body;
       }
     })
  }

  

}
