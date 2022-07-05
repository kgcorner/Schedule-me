import { Time } from "@angular/common";

export interface HourlyJob {
    jobId : string
    name : string
    urgent : boolean
    startTime : Time
    status : string
    jobParam : any
    jobKind : string
    repeatFrequencyInMinutes : number
    lastCompleteRunStartedAt : Date
    lastCompletedRunEndedAt : Date
}