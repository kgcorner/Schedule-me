import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { JobsComponent } from './jobs/jobs.component';
import { NavigationComponent } from './navigation/navigation.component';

const routes: Routes = [
  {path:"dashboard", component: DashboardComponent},
  {path:"jobs", component: JobsComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
