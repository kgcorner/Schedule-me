import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { JobAuditsComponent } from './job-audits/job-audits.component';
import { JobLogsComponent } from './job-logs/job-logs.component';
import { JobsComponent } from './jobs/jobs.component';
import { NavigationComponent } from './navigation/navigation.component';

const routes: Routes = [
  {path:"dashboard", component: DashboardComponent},
  {path:"jobs", component: JobsComponent},
  {path:"logs/:jobId/:name", component: JobLogsComponent},
  {path:"audits/:jobId/:name", component: JobAuditsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
