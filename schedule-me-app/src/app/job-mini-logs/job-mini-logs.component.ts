import { Component, Input, OnInit } from '@angular/core';
import { Log } from '../services/models/log';

@Component({
  selector: 'app-job-mini-logs',
  templateUrl: './job-mini-logs.component.html',
  styleUrls: ['./job-mini-logs.component.scss']
})
export class JobMiniLogsComponent implements OnInit {

  @Input()
  logs : Log[] = []

  
  filteredLogs : Log[] = [];
  displayedColumns: string[] = ['date', 'module', 'level','message', 'filter'];
  constructor() { }

  ngOnInit(): void {
  }

  ngOnChanges() {
    if(this.logs.length > 0) {
      this.filteredLogs = this.logs;
    }
  }

  filter(level : string) {
    if(level == 'ALL') {
      this.filteredLogs = this.logs;
    } else {
      this.filteredLogs = this.logs.filter(l=>l.level == level);
    }
    
  }


}
