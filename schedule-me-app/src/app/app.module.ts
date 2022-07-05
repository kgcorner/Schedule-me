import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NavigationComponent } from './navigation/navigation.component';
import { LayoutModule } from '@angular/cdk/layout';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatInputModule } from '@angular/material/input';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { DashboardComponent } from './dashboard/dashboard.component';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatCardModule } from '@angular/material/card';
import { MatMenuModule } from '@angular/material/menu';
import {MatTabsModule} from '@angular/material/tabs';
import { JobsComponent } from './jobs/jobs.component';
import { DailyJobsComponent } from './daily-jobs/daily-jobs.component';
import { HourlyJobsComponent } from './hourly-jobs/hourly-jobs.component';
import { MonthlyJobsComponent } from './monthly-jobs/monthly-jobs.component';
import { GenericJobsComponent } from './generic-jobs/generic-jobs.component';
import { RecordProcessorJobsComponent } from './record-processor-jobs/record-processor-jobs.component';
import { RecordProcessorDailyJobsComponent } from './record-processor-daily-jobs/record-processor-daily-jobs.component';
import { RecordProcessorHourlyJobsComponent } from './record-processor-hourly-jobs/record-processor-hourly-jobs.component';
import { RecordProcessorMonthlyJobsComponent } from './record-processor-monthly-jobs/record-processor-monthly-jobs.component';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatTableModule} from '@angular/material/table';
import {MatRadioModule} from '@angular/material/radio';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { TimePipe } from './pips/time.pipe';
@NgModule({
  declarations: [
    AppComponent,
    NavigationComponent,
    DashboardComponent,
    JobsComponent,
    DailyJobsComponent,
    HourlyJobsComponent,
    MonthlyJobsComponent,
    GenericJobsComponent,
    RecordProcessorJobsComponent,
    RecordProcessorDailyJobsComponent,
    RecordProcessorHourlyJobsComponent,
    RecordProcessorMonthlyJobsComponent,
    TimePipe
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    LayoutModule,
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    MatGridListModule,
    MatCardModule,
    MatMenuModule,
    MatTabsModule,
    MatFormFieldModule,
    MatCheckboxModule,
    MatInputModule,
    MatTableModule,
    MatRadioModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [NavigationComponent]
})
export class AppModule { }
