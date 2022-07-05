import { Time } from "@angular/common";

export interface DailyJob {
    jobId : string
    name : string
    urgent : boolean
    startTime : Time
    status : string
    jobParams : any
    jobKind : string
    lastCompleteRunStartedAt : Date
    lastCompletedRunEndedAt : Date
}